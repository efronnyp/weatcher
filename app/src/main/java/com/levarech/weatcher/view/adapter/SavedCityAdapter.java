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
import com.levarech.weatcher.domain.city.CityCondition;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EFR on 19/03/2017.
 * RecyclerView adapter for saved city data list.
 */

public class SavedCityAdapter extends RecyclerView.Adapter<SavedCityAdapter.CityViewHolder> {

    private static final String TAG = "CityListViewAdapter";

    private List<CityCondition> mCities;
    private Context mContext;
    private OnItemClickListener<CityCondition> mItemClickListener;
    private OnItemLongClickListener<CityCondition> mItemLongClickListener;

    public SavedCityAdapter(List<CityCondition> cityConditionList, Context context) {
        this.mCities = new ArrayList<>();
        this.mCities.addAll(cityConditionList);
        this.mContext = context;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_saved_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        CityCondition city = mCities.get(position);

        /*if (BuildConfig.DEBUG) {
            Log.d(TAG, observation.toString());
        }*/

        holder.rlCardRoot.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onClick(view, position, city);
            }
        });
        holder.rlCardRoot.setOnLongClickListener(view -> {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onLongClick(view, position, city);
                return true;
            }
            return false;
        });

        // Set calendar to this city's localtime
        String timezoneStr = city.getLocalTzLong();
        if (timezoneStr == null) {
            timezoneStr = city.getLocalTzShort();
        }
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timezoneStr));
        DateFormat formatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);
        formatter.setTimeZone(TimeZone.getTimeZone(timezoneStr));
        String localTime = formatter.format(calendar.getTime());
        holder.tvLocalTime.setText(localTime);

        holder.ivCurrentLocationIcon.setVisibility(city.isCurrentCity() ? View.VISIBLE :View.GONE);
        holder.tvDisplayName.setText(city.getCityName());
        holder.tvCurrentTemperature.setText(String.format("%s°", city.getTempC()));

        // Set card background
        int drawableResId = 0;
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        boolean isNight = (hourOfDay > 17) || (hourOfDay < 6);
        switch (city.getIcon()) {
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
            case "hazy":
                drawableResId = isNight ? R.drawable.night_hazy : R.drawable.day_hazy;
                break;
            case "fog":
                drawableResId = R.drawable.other_fog;
        }
        holder.rlCardRoot.setBackgroundResource(drawableResId);
        int defaultCardHeight = mContext.getResources().getDimensionPixelSize(R.dimen.city_card_height);
        int defaultCardPadding = mContext.getResources().getDimensionPixelSize(R.dimen.city_card_padding);
        if (position == 0) {
            int resId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                int statusBarHeight = mContext.getResources().getDimensionPixelSize(resId);
                holder.rlCardRoot.setPadding(defaultCardPadding, (int) (1.5*statusBarHeight),
                        defaultCardPadding, defaultCardPadding);
                if (holder.rlCardRoot.getLayoutParams().height == defaultCardHeight) {
                    holder.rlCardRoot.getLayoutParams().height = defaultCardHeight + statusBarHeight;
                    holder.rlCardRoot.requestLayout();
                }
            }
        } else {
            holder.rlCardRoot.setPadding(defaultCardPadding, defaultCardPadding,
                    defaultCardPadding, defaultCardPadding);
            holder.rlCardRoot.getLayoutParams().height = defaultCardHeight;
            holder.rlCardRoot.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public SavedCityAdapter setOnItemClickListener(OnItemClickListener<CityCondition> listener) {
        mItemClickListener = listener;
        return this;
    }

    public SavedCityAdapter setOnItemLongClickListener(OnItemLongClickListener<CityCondition> listener) {
        mItemLongClickListener = listener;
        return this;
    }

    public void update(List<CityCondition> newData) {
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
