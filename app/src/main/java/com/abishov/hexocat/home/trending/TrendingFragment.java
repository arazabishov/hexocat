package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abishov.hexocat.Hexocat;
import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.views.BaseFragment;
import com.squareup.picasso.Picasso;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public final class TrendingFragment extends BaseFragment implements TrendingView {
    public static final String TAG = TrendingFragment.class.getSimpleName();

    private static final String STATE_VIEW = "state:trendingViewState";
    private static final String STATE_RECYCLER_VIEW = "state:trendingRecyclerViewState";

    @BindView(R.id.recyclerview_trending)
    RecyclerView recyclerViewTrending;

    @Inject
    TrendingPresenter trendingPresenter;

    @Inject
    Picasso picasso;

    @Nullable
    private RepositoryAdapter repositoryAdapter;

    @Nullable
    private TrendingViewState viewState;

    @Nullable
    private Parcelable recyclerViewState;

    @Nullable
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

        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        repositoryAdapter = new RepositoryAdapter(LayoutInflater.from(getActivity()), picasso);
        recyclerViewTrending.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewTrending.setAdapter(repositoryAdapter);

        if (savedInstanceState != null) {
            viewState = savedInstanceState.getParcelable(STATE_VIEW);
            recyclerViewState = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW);
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

    @Nonnull
    @Override
    public Consumer<TrendingViewState> renderRepositories() {
        return state -> {
            viewState = state;

            if (state.isSuccess()) {
                // use diff util to update the list
                repositoryAdapter.swap(state.items());
            } else if (state.isFailure()) {
                Toast.makeText(getActivity(),
                        state.error(), Toast.LENGTH_SHORT).show();
            } else if (state.isInProgress()) {
                Toast.makeText(getActivity(),
                        "In progress", Toast.LENGTH_SHORT).show();
            } else if (state.isIdle()) {
                Toast.makeText(getActivity(),
                        "Idle", Toast.LENGTH_SHORT).show();
            }

            if (recyclerViewState != null) {
                recyclerViewLayoutManager.onRestoreInstanceState(recyclerViewState);
                recyclerViewState = null;
            }
        };
    }
}
