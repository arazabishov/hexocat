package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abishov.hexocat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

final class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final List<RepositoryViewModel> repositories;

    RepositoryAdapter(@NonNull LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        this.repositories = new ArrayList<>();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(layoutInflater.inflate(
                R.layout.recyclerview_item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        RepositoryViewModel repository = repositories.get(position);
        holder.textViewRepositoryName.setText(repository.name());
        holder.textViewRepositoryDescription.setText(repository.description());
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    void swap(@NonNull List<RepositoryViewModel> repositories) {
        this.repositories.clear();
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_repository_name)
        TextView textViewRepositoryName;

        @BindView(R.id.textview_repository_description)
        TextView textViewRepositoryDescription;

        RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
