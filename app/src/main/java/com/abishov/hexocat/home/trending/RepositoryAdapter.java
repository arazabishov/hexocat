package com.abishov.hexocat.home.trending;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.CircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

final class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Picasso picasso;
    private final List<RepositoryViewModel> repositories;
    private final Transformation transformation;

    RepositoryAdapter(@NonNull LayoutInflater layoutInflater, @NonNull Picasso picasso) {
        this.layoutInflater = layoutInflater;
        this.picasso = picasso;
        this.repositories = new ArrayList<>();
        this.transformation = new CircleTransformation();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(layoutInflater.inflate(
                R.layout.recyclerview_item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.update(repositories.get(position));
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

    class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_owner_logo)
        ImageView imageViewLogo;

        @BindView(R.id.textview_repository_name)
        TextView textViewRepositoryName;

        @BindView(R.id.textview_repository_description)
        TextView textViewRepositoryDescription;

        RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void update(RepositoryViewModel viewModel) {
            textViewRepositoryName.setText(viewModel.name());
            textViewRepositoryDescription.setText(viewModel.description());

            picasso.load(viewModel.avatarUrl())
                    .transform(transformation)
                    .fit().centerCrop()
                    .into(imageViewLogo);
        }
    }
}
