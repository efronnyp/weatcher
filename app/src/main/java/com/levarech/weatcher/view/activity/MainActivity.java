package com.levarech.weatcher.view.activity;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.levarech.weatcher.Constants;
import com.levarech.weatcher.R;
import com.levarech.weatcher.model.local.CityConditions;
import com.levarech.weatcher.presenter.WeatherPresenter;
import com.levarech.weatcher.view.WeatherView;
import com.levarech.weatcher.view.adapter.SavedCityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements WeatherView, LocationListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvSavedCityList)
    RecyclerView rvSavedCityList;

    private WeatherPresenter mPresenter;
    private List<CityConditions> mCityConditionsList;
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
        /*fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());*/

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mCityConditionsList = new ArrayList<>();
        mPresenter = new WeatherPresenter(this, this);
    }

    private void setUpRecyclerView() {
        SavedCityAdapter adapter = (SavedCityAdapter) rvSavedCityList.getAdapter();
        if (adapter == null) { // no adapter yet
            adapter = new SavedCityAdapter(mCityConditionsList, this);
            rvSavedCityList.setLayoutManager(new LinearLayoutManager(this));
            rvSavedCityList.setAdapter(adapter);
        } else {
            adapter.update(mCityConditionsList);
        }
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
                                5000, 5, this);
                    }
                }
            }

            if (mCurrentLocation == null) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.LOCATION_REQUEST_CODE);
            }
        } else {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                mCurrentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (mCurrentLocation == null) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
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

    private void showTurnOnGpsDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.turn_on_gps_message)
                .setNegativeButton(android.R.string.no,
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(android.R.string.yes,
                        (dialogInterface, i) -> {
                            Intent settingIntent =
                                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingIntent, Constants.LOCATION_REQUEST_CODE);
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
        mPresenter.getSavedCitiesCondition();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.LOCATION_REQUEST_CODE:
                getCurrentLocation();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.LOCATION_REQUEST_CODE) {
            getCurrentLocation();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onReceivedCityList(List<CityConditions> citiesConditions) {
        if (mCityConditionsList.size() > 0) {
            if (mCityConditionsList.get(0).currentCity) {
                List<CityConditions> newList = new ArrayList<>();
                newList.add(mCityConditionsList.get(0));
                newList.addAll(citiesConditions);
                mCityConditionsList = newList;
            } else {
                mCityConditionsList.clear();
                mCityConditionsList.addAll(citiesConditions);
            }
        } else {
            mCityConditionsList.addAll(citiesConditions);
        }
        setUpRecyclerView();
    }

    @Override
    public void onReceivedCurrentLocationConditions(CityConditions conditions) {
        if (mCityConditionsList.size() > 0 &&
                mCityConditionsList.get(0).currentCity) {
            mCityConditionsList.remove(0);
        }
        mCityConditionsList.add(0, conditions);
        setUpRecyclerView();
    }

    @Override
    public void onNewCitySuccessfullySaved(CityConditions conditions) {
        // not-used
    }

    @Override
    public void showLoading() {
        // doSomething
    }

    @Override
    public void hideLoading() {
        // doSomething
    }

    @Override
    public void showError(String message) {
        Log.e("MainActivity", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mCurrentLocation = location;
            mPresenter.getCurrentLocationWeather(location.getLatitude(), location.getLongitude());
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
