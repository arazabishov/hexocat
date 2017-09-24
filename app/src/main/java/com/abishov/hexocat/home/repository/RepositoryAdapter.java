package com.abishov.hexocat.home.repository;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.CircleTransformation;
import com.abishov.hexocat.home.trending.TrendingItemView;
import com.abishov.hexocat.home.trending.TrendingViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public final class RepositoryAdapter extends Adapter<RepositoryAdapter.RepositoryViewHolder> implements Consumer<List<TrendingViewModel>> {
    private final LayoutInflater layoutInflater;
    private final Picasso picasso;
    private final List<TrendingViewModel> repositories;
    private final Transformation transformation;
    private final TrendingViewClickListener trendingViewClickListener;

    RepositoryAdapter(LayoutInflater layoutInflater, Picasso picasso, TrendingViewClickListener clickListener) {
        this.layoutInflater = layoutInflater;
        this.picasso = picasso;
        this.trendingViewClickListener = clickListener;
        this.repositories = new ArrayList<>();
        this.transformation = new CircleTransformation();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder((TrendingItemView) layoutInflater.inflate(
                R.layout.recyclerview_item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bindTo(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    @Override
    public void accept(List<TrendingViewModel> trendingViewModels) throws Exception {
        repositories.clear();
        repositories.addAll(trendingViewModels);
        notifyDataSetChanged();
    }

    public interface TrendingViewClickListener {
        void onRepositoryClick(TrendingViewModel repository);
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {
        private final TrendingItemView trendingItemView;

        RepositoryViewHolder(TrendingItemView itemView) {
            super(itemView);
            trendingItemView = itemView;
            trendingItemView.setOnClickListener(v -> {
                TrendingViewModel repository = repositories.get(getAdapterPosition());
                trendingViewClickListener.onRepositoryClick(repository);
            });
        }

        void bindTo(TrendingViewModel viewModel) {
            trendingItemView.bindTo(viewModel, picasso, transformation);
        }
    }
}
