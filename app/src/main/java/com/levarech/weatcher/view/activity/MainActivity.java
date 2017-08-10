package com.levarech.weatcher.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.levarech.weatcher.BuildConfig;
import com.levarech.weatcher.R;
import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.internal.di.components.DaggerActivityComponent;
import com.levarech.weatcher.internal.di.modules.ActivityModule;
import com.levarech.weatcher.presenter.CityListContract;
import com.levarech.weatcher.view.BaseActivity;
import com.levarech.weatcher.view.adapter.SavedCityAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity
        implements CityListContract.View, LocationListener {

    private static final String TAG = "MainActivity";
    private static final int TWO_MINUTES = 2 * 60 * 1000;
    private static final int MIN_LOCATION_TIME = 5000;
    private static final int MIN_LOCATION_DISTANCE = 100;
    private static final int LOCATION_REQUEST_CODE = 34;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvSavedCityList)
    RecyclerView rvSavedCityList;
    @BindView(R.id.loadingProgressBar)
    ContentLoadingProgressBar loadingProgressBar;

    @Inject CityListContract.Presenter mPresenter;
    private List<CityCondition> mCityConditionsList;
    private LocationManager mLocationManager;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // turn on the fullscreen flag to draw weather background over statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        ButterKnife.bind(this);
        initInjector();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mCityConditionsList = new ArrayList<>();
        mPresenter.setView(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .injek(this);
    }

    private void setUpRecyclerView() {
        SavedCityAdapter adapter = (SavedCityAdapter) rvSavedCityList.getAdapter();
        if (adapter == null) { // no adapter yet
            adapter = new SavedCityAdapter(mCityConditionsList, this);
            adapter.setOnItemClickListener((view, i, data) -> onItemClicked(data))
                    .setOnItemLongClickListener((view, i, data) -> {
                        if (!data.isCurrentCity()) // everything can be deleted, except the current city
                            onDeleteItem(data);
                    });
            rvSavedCityList.setLayoutManager(new LinearLayoutManager(this));
            rvSavedCityList.setAdapter(adapter);
        } else {
            adapter.update(mCityConditionsList);
        }
    }

    private void onItemClicked(CityCondition data) {
        // open city detail
    }

    private void onDeleteItem(CityCondition city) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence items[] = new CharSequence[] {getText(R.string.delete), getText(android.R.string.cancel)};
        builder.setItems(items, (dialogInterface, i) -> {
            switch (i) {
                case 0:
                    // do deletion
                    mPresenter.deleteSavedCity(city.getCityId());
                    mPresenter.getSavedCityCondition();
                    break;
                case 1:
                    break;
            }
            dialogInterface.dismiss();
        });
        builder.show();
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (mCurrentLocation == null) {
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                MIN_LOCATION_TIME, MIN_LOCATION_DISTANCE, this);
                    }
                } else {
                    showTurnOnGpsDialog();
                }
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        } else {
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isNetworkEnabled || isGpsEnabled) {
                if (isGpsEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_LOCATION_TIME, MIN_LOCATION_DISTANCE, this);
                    mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                if (isNetworkEnabled && mCurrentLocation == null) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_LOCATION_TIME, MIN_LOCATION_DISTANCE, this);
                    mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            } else {
                showTurnOnGpsDialog();
            }
        }

        if (mCurrentLocation != null) {
            mPresenter.getCurrentLocationWeather(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude());
        }
    }

    private boolean isNewLocationBetter(Location newLocation, Location oldLocation) {
        if (oldLocation == null) {
            return true;
        }

        int distanceDiff = (int) oldLocation.distanceTo(newLocation);
        if (distanceDiff < MIN_LOCATION_DISTANCE) {
            // new location is only few meters from old location
            return false;
        }
        int timeDiff = (int) (newLocation.getTime() - oldLocation.getTime());
        if (timeDiff > TWO_MINUTES) {
            // new location is newer
            return true;
        } else if (timeDiff < -TWO_MINUTES) {
            // new location is too old
            return false;
        }

        int accuracyDiff = (int) (newLocation.getAccuracy() - oldLocation.getAccuracy());
        if (accuracyDiff < 0) {
            // new location is more accurate
            return true;
        } else if (accuracyDiff > 200) {
            // new location is significantly less accurate than old location
            return false;
        }

        return timeDiff > 0;
    }

    private void showTurnOnGpsDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.turn_on_gps_message)
                .setNegativeButton(android.R.string.no,
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(android.R.string.yes,
                        (dialogInterface, i) -> {
                            Intent settingIntent =
                                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingIntent, LOCATION_REQUEST_CODE);
                            dialogInterface.dismiss();
                        })
                .show();
    }

    @OnClick(R.id.fab)
    public void onAddButtonClicked(View view) {
        startActivity(SearchCityActivity.prepareIntent(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();
        mPresenter.getSavedCityCondition();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(this);
        }
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                getCurrentLocation();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            getCurrentLocation();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onReceivedCityList(List<CityCondition> cityConditionList) {
        if (mCityConditionsList.size() > 0) {
            mCityConditionsList.clear();
            mCityConditionsList.addAll(cityConditionList);
        } else {
            mCityConditionsList.addAll(cityConditionList);
        }
        setUpRecyclerView();
    }

    @Override
    public void onReceivedCurrentLocationConditions(CityCondition cityCondition) {
        if (mCityConditionsList.size() > 0 &&
                mCityConditionsList.get(0).isCurrentCity()) {
            mCityConditionsList.remove(0);
        }
        mCityConditionsList.add(0, cityCondition);
        setUpRecyclerView();
    }

    @Override
    public void showLoading() {
        loadingProgressBar.show();
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.hide();
    }

    @Override
    public void showError(String message) {
        Log.e("MainActivity", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (isNewLocationBetter(location, mCurrentLocation)) {
                mCurrentLocation = location;
                if (BuildConfig.DEBUG) {
                    String message = "Better location received. Setting forceUpdate to true";
                    Snackbar.make(fab, message, Snackbar.LENGTH_LONG).show();
                    Log.d(TAG, message);
                }
                mPresenter.getCurrentLocationWeather(location.getLatitude(), location.getLongitude());
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
