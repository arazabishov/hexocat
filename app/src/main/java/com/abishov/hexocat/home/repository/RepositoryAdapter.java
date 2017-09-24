package com.abishov.hexocat.home.repository;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.CircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public final class RepositoryAdapter extends Adapter<RepositoryAdapter.RepositoryViewHolder> implements Consumer<List<RepositoryViewModel>> {
    private final LayoutInflater layoutInflater;
    private final Picasso picasso;
    private final List<RepositoryViewModel> repositories;
    private final Transformation transformation;
    private final TrendingViewClickListener trendingViewClickListener;

    public RepositoryAdapter(LayoutInflater layoutInflater, Picasso picasso, TrendingViewClickListener clickListener) {
        this.layoutInflater = layoutInflater;
        this.picasso = picasso;
        this.trendingViewClickListener = clickListener;
        this.repositories = new ArrayList<>();
        this.transformation = new CircleTransformation();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder((RepositoryItemView) layoutInflater.inflate(
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
    public void accept(List<RepositoryViewModel> repositoryViewModels) throws Exception {
        repositories.clear();
        repositories.addAll(repositoryViewModels);
        notifyDataSetChanged();
    }

    public interface TrendingViewClickListener {
        void onRepositoryClick(RepositoryViewModel repository);
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {
        private final RepositoryItemView repositoryItemView;

        RepositoryViewHolder(RepositoryItemView itemView) {
            super(itemView);
            repositoryItemView = itemView;
            repositoryItemView.setOnClickListener(v -> {
                RepositoryViewModel repository = repositories.get(getAdapterPosition());
                trendingViewClickListener.onRepositoryClick(repository);
            });
        }

        void bindTo(RepositoryViewModel viewModel) {
            repositoryItemView.bindTo(viewModel, picasso, transformation);
        }
    }
}
