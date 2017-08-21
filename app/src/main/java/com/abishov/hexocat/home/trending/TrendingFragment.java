package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Bundle;
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
import com.abishov.hexocat.commons.views.ViewState;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public final class TrendingFragment extends BaseFragment implements TrendingView {

    @BindView(R.id.recyclerview_trending)
    RecyclerView recyclerViewTrending;

    @Nullable
    RepositoryAdapter repositoryAdapter;

    @Inject
    TrendingPresenter trendingPresenter;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        repositoryAdapter = new RepositoryAdapter(LayoutInflater.from(getActivity()));
        recyclerViewTrending.setLayoutManager(layoutManager);
        recyclerViewTrending.setAdapter(repositoryAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        trendingPresenter.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        trendingPresenter.onAttach(this);
    }

    @Nonnull
    @Override
    public Consumer<ViewState<RepositoryViewModel>> renderRepositories() {
        return state -> {
            if (state.isSuccess()) {
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
        };
    }
}
