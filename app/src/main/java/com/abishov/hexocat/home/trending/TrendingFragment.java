package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abishov.hexocat.Hexocat;
import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.BaseFragment;
import com.abishov.hexocat.home.repository.RepositoryAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class TrendingFragment extends BaseFragment implements TrendingView, RepositoryAdapter.TrendingViewClickListener {
    public static final String TAG = TrendingFragment.class.getSimpleName();

    private static final String STATE_VIEW = "state:trendingViewState";
    private static final String STATE_RECYCLER_VIEW = "state:trendingRecyclerViewState";

    @BindView(R.id.recyclerview_trending)
    RecyclerView recyclerViewTrending;

    @BindView(R.id.progressbar_trending)
    ProgressBar progressBarLoading;

    @BindView(R.id.button_retry)
    Button buttonRetry;

    @Inject
    TrendingPresenter trendingPresenter;

    @Inject
    Picasso picasso;

    private RepositoryAdapter repositoryAdapter;
    private TrendingViewState viewState;
    private Parcelable recyclerViewState;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Nonnull
    public static TrendingFragment create() {
        return new TrendingFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((Hexocat) context.getApplicationContext()).networkComponent()
                .plus(new TrendingModule())
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bind(this, view);

        setupRecyclerView(savedInstanceState);

        progressBarLoading.setVisibility(View.GONE);
        buttonRetry.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            viewState = savedInstanceState.getParcelable(STATE_VIEW);
        }

        trendingPresenter.onAttach(this, viewState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (viewState != null) {
            outState.putParcelable(STATE_VIEW, viewState);
            outState.putParcelable(STATE_RECYCLER_VIEW,
                    recyclerViewLayoutManager.onSaveInstanceState());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        trendingPresenter.onDetach();
    }

    @Override
    public Observable<Object> retryActions() {
        return RxView.clicks(buttonRetry);
    }

    @Nonnull
    @Override
    public Consumer<TrendingViewState> renderRepositories() {
        return state -> {
            viewState = state;

            recyclerViewTrending.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
            progressBarLoading.setVisibility(state.isInProgress() ? View.VISIBLE : View.GONE);
            buttonRetry.setVisibility(state.isFailure() ? View.VISIBLE : View.GONE);

            if (state.isSuccess()) {
                repositoryAdapter.accept(state.items());
            } else if (state.isFailure()) {
                Toast.makeText(getActivity(),
                        state.error(), Toast.LENGTH_SHORT).show();
            }

            if (recyclerViewState != null) {
                recyclerViewLayoutManager.onRestoreInstanceState(recyclerViewState);
                recyclerViewState = null;
            }
        };
    }

    @Override
    public void onRepositoryClick(RepositoryViewModel repository) {
        Toast.makeText(getActivity(), repository.name(), Toast.LENGTH_SHORT).show();
    }

    private void setupRecyclerView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW);
        }

        repositoryAdapter = new RepositoryAdapter(LayoutInflater.from(getActivity()), picasso, this);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTrending.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewTrending.setAdapter(repositoryAdapter);
        recyclerViewTrending.addItemDecoration(new DividerItemDecoration(
                recyclerViewTrending.getContext(), DividerItemDecoration.VERTICAL));
    }
}
