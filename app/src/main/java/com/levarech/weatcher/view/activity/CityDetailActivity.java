package com.levarech.weatcher.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.levarech.weatcher.R;
import com.levarech.weatcher.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by EFR on 11/04/2017.
 * Activity to display saved city weather detail.
 */

public class CityDetailActivity extends BaseActivity {

    @BindView(R.id.tvLocationName) TextView tvLocationName;
    @BindView(R.id.tvWeatherCondition) TextView tvWeatherCondition;
    @BindView(R.id.tvTemperature) TextView tvTemperature;
    @BindView(R.id.tvTodayDayName) TextView tvTodayDayName;
    @BindView(R.id.tvTodayHighTemp) TextView tvTodayHighTemp;
    @BindView(R.id.tvTodayLowTemp) TextView tvTodayLowTemp;
    @BindView(R.id.rvHourlyForecast) RecyclerView rvHourlyForecast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        // Bind this activity with it's view
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
