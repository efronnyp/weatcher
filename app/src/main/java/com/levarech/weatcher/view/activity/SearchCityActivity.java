package com.levarech.weatcher.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.levarech.weatcher.R;
import com.levarech.weatcher.model.local.CityConditions;
import com.levarech.weatcher.presenter.WeatherPresenter;
import com.levarech.weatcher.view.WeatherAddView;
import com.levarech.weatcher.view.adapter.OnItemClickListener;
import com.levarech.weatcher.view.adapter.SearchSuggestionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EFR on 19/03/2017.
 * Search city activity.
 */

public class SearchCityActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        OnItemClickListener<AutocompletePrediction>, WeatherAddView {

    private static final String TAG = SearchCityActivity.class.getSimpleName();

    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.rvCitySuggestions) RecyclerView rvCitySuggestions;

    private WeatherPresenter mPresenter;
    private GoogleApiClient mGoogleApiClient;
    private AutocompleteFilter mPlaceFilter;
    private List<AutocompletePrediction> mPredictions;
    private Timer autoCompleteTimer;
    private ProgressDialog progressDialog;

    public static Intent prepareIntent(Context source) {
        return new Intent(source, SearchCityActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        ButterKnife.bind(this);

        mPresenter = new WeatherPresenter(this, this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mPlaceFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (autoCompleteTimer != null) {
                    autoCompleteTimer.cancel();
                }
                if (newText.length() >= 3) {
                    autoCompleteTimer = new Timer();
                    autoCompleteTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            onNewQuery(newText);
                        }
                    }, 1500);
                } else {
                    if (mPredictions != null) {
                        mPredictions.clear();
                        setupRecyclerView();
                    }
                }
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        SearchSuggestionAdapter adapter = (SearchSuggestionAdapter) rvCitySuggestions.getAdapter();
        if (adapter == null) {
            adapter = new SearchSuggestionAdapter(this, mPredictions);
            adapter.setOnItemClickListener(this);
            rvCitySuggestions.setLayoutManager(new LinearLayoutManager(this));
            rvCitySuggestions.setAdapter(adapter);
        } else {
            adapter.update(mPredictions);
        }
    }

    private void onNewQuery(final String query) {
        new Thread(() -> {
            mPredictions = getAutocomplete(query);
            runOnUiThread(this::setupRecyclerView);
        }).start();
    }

    /**
     * Submits an autocomplete query to the Places Geo Data Autocomplete API.
     * Results are returned as frozen AutocompletePrediction objects, ready to be cached.
     * objects to store the Place ID and description that the API returns.
     * Returns an empty list if no results were found.
     * Returns null if the API client is not available or the query did not complete
     * successfully.
     * This method MUST be called off the main UI thread, as it will block until data is returned
     * from the API, which may include a network request.
     *
     * @param constraint Autocomplete query string
     * @return Results from the autocomplete API or null if the query was not successful.
     * @see Places#GEO_DATA_API#getAutocomplete(CharSequence)
     * @see AutocompletePrediction#freeze()
     */
    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        if (mGoogleApiClient.isConnected()) {

            // Submit the query to the autocomplete API and retrieve a PendingResult that will
            // contain the results when the query completes.
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                                    null, mPlaceFilter);

            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);

            // Confirm that the query completed successfully, otherwise return null
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            // Freeze the results immutable representation that can be stored safely.
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }
        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showError(connectionResult.getErrorMessage());
    }

    @OnClick(R.id.cancelButton)
    public void onCancelButtonClicked(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View view, int i, AutocompletePrediction selectedPlace) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.saving);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        /*
         Issue a request to the Places Geo Data API to retrieve a Place object with additional
         details about the place.
        */
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, selectedPlace.getPlaceId());
        placeResult.setResultCallback(places -> {
            if (places.getStatus().isSuccess()) {
                Place place = places.get(0);
                double lat = place.getLatLng().latitude;
                double lon = place.getLatLng().longitude;
                //Geocoder geocoder = new Geocoder(this);

                try {
                    /*List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                    /*String countryCode = addresses.get(0).getCountryCode();
                    String cityName = addresses.get(0).getSubAdminArea();

                    /*if (countryCode != null && cityName != null) {
                        mPresenter.saveCityByName(countryCode, cityName);
                    } else {*/
                        mPresenter.saveNewCity(lat, lon);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            } else {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                progressDialog.dismiss();
            }
            places.release();
        });
    }

    @Override
    public void onNewCitySavedSuccessfully(CityConditions conditions) {
        if (progressDialog != null) progressDialog.dismiss();
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
