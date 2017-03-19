package com.levarech.weatcher.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.levarech.weatcher.R;
import com.levarech.weatcher.model.local.CityConditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EFR on 19/03/2017.
 * RecyclerView adapter for saved city data list.
 */

public class SavedCityAdapter extends RecyclerView.Adapter<SavedCityAdapter.CityViewHolder> {

    private List<CityConditions> mCities;
    private Context mContext;
    private Calendar mCalendar;

    public SavedCityAdapter(List<CityConditions> mCities, Context context) {
        this.mCities = new ArrayList<>();
        this.mCities.addAll(mCities);
        this.mContext = context;
        this.mCalendar = Calendar.getInstance();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_saved_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        CityConditions city = mCities.get(position);

        // Set calendar to this city's localtime
        String timezoneStr = city.currentObservation.local_tz_long;
        if (timezoneStr == null) {
            timezoneStr = city.currentObservation.local_tz_short;
        }
        mCalendar.setTimeZone(TimeZone.getTimeZone(timezoneStr));
        DateFormat formatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        String localTime = formatter.format(mCalendar.getTime());
        holder.tvLocalTime.setText(localTime);

        holder.ivCurrentLocationIcon.setVisibility(city.currentCity ? View.VISIBLE :View.GONE);
        holder.tvDisplayName.setText(city.currentObservation.display_location.city);
        holder.tvCurrentTemperature.setText(String.format("%s°", city.currentObservation.temp_c));

        // Set card background
        int drawableResId = 0;
        int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
        boolean isNight = (hourOfDay > 17) || (hourOfDay < 7);
        switch (city.currentObservation.icon) {
            case "cloudy":
                drawableResId = isNight ? R.drawable.night_cloudy_partly : R.drawable.day_cloudy;
                break;
            case "partlycloudy":
                drawableResId = isNight ? R.drawable.night_cloudy_partly : R.drawable.day_cloudy_partly;
                break;
            case "mostlycloudy":
                drawableResId = isNight ? R.drawable.night_cloudy_mostly : R.drawable.day_cloudy_mostly;
                break;
            case "clear":
                drawableResId = isNight ? R.drawable.night_clear : R.drawable.day_clear;
                break;
            case "tstorms":
                drawableResId = R.drawable.day_thunderstorms;
                break;
            case "rain":
                drawableResId = R.drawable.day_rain;
                break;
        }
        holder.rlCardRoot.setBackgroundResource(drawableResId);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public void update(List<CityConditions> newData) {
        mCities.clear();
        mCities.addAll(newData);
        notifyDataSetChanged();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rlCardRoot) RelativeLayout rlCardRoot;
        @BindView(R.id.tvLocalTime) TextView tvLocalTime;
        @BindView(R.id.ivCurrentLocationIcon) ImageView ivCurrentLocationIcon;
        @BindView(R.id.tvDisplayName) TextView tvDisplayName;
        @BindView(R.id.tvCurrentTemperature) TextView tvCurrentTemperature;

        CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
