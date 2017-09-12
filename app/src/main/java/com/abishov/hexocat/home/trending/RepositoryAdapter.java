package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

        @BindView(R.id.textview_repository_forks)
        TextView textViewForks;

        @BindView(R.id.textview_repository_stars)
        TextView textViewStars;

        RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Context ctx = itemView.getContext();

            Drawable drawableForks = DrawableCompat.wrap(
                    ContextCompat.getDrawable(ctx, R.drawable.ic_fork));
            Drawable drawableStars = DrawableCompat.wrap(
                    ContextCompat.getDrawable(ctx, R.drawable.ic_star));

            TypedValue textColorAttribute = new TypedValue();
            ctx.getTheme().resolveAttribute(android.R.attr.textColorSecondary,
                    textColorAttribute, true);
            int textColorAttributeValue = ContextCompat.getColor(
                    itemView.getContext(), textColorAttribute.resourceId);

            DrawableCompat.setTint(drawableForks.mutate(), textColorAttributeValue);
            DrawableCompat.setTint(drawableStars.mutate(), textColorAttributeValue);

            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textViewForks,
                    drawableForks, null, null, null);
            TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textViewStars,
                    drawableStars, null, null, null);
        }

        void update(RepositoryViewModel viewModel) {
            textViewRepositoryName.setText(viewModel.name());
            textViewRepositoryDescription.setText(viewModel.description());
            textViewForks.setText(viewModel.forks());
            textViewStars.setText(viewModel.stars());

            picasso.load(viewModel.avatarUrl())
                    .transform(transformation)
                    .fit().centerCrop()
                    .into(imageViewLogo);
        }
    }
}
