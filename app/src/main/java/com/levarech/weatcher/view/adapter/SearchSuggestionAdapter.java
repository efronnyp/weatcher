package com.levarech.weatcher.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.levarech.weatcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EFR on 19/03/2017.
 * Adapter for city suggestion list.
 */

public class SearchSuggestionAdapter extends
        RecyclerView.Adapter<SearchSuggestionAdapter.SearchSuggestionViewHolder> {

    private List<AutocompletePrediction> mPredictions;
    private Context mContext;
    private OnItemClickListener<AutocompletePrediction> mItemClickListener;

    public SearchSuggestionAdapter(Context context, List<AutocompletePrediction> predictions) {
        mPredictions = new ArrayList<>();
        mPredictions.addAll(predictions);
        mContext = context;
    }

    @Override
    public SearchSuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_search_city, parent, false);
        return new SearchSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchSuggestionViewHolder holder, int position) {
        AutocompletePrediction prediction = mPredictions.get(position);
        holder.rootView.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onClick(view, position, mPredictions.get(position));
            }
        });
        holder.tvPlaceName.setText(prediction.getFullText(null));
    }

    @Override
    public int getItemCount() {
        return mPredictions.size();
    }

    public void setOnItemClickListener(OnItemClickListener<AutocompletePrediction> listener) {
        this.mItemClickListener = listener;
    }

    public void update(List<AutocompletePrediction> newPredictions) {
        mPredictions.clear();
        mPredictions.addAll(newPredictions);
        notifyDataSetChanged();
    }

    class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.searchCityRoot) View rootView;
        @BindView(R.id.tvPlaceName) TextView tvPlaceName;

        SearchSuggestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
