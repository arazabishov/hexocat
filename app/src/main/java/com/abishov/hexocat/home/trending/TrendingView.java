package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.abishov.hexocat.Hexocat;
import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.network.github.SearchQuery;
import com.abishov.hexocat.commons.views.DividerItemDecoration;
import com.abishov.hexocat.home.repository.RepositoryAdapter;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class TrendingView extends FrameLayout implements TrendingContract.View {

    @BindView(R.id.swipe_refresh_layout_trending)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerview_trending)
    RecyclerView recyclerViewTrending;

    @BindView(R.id.button_retry)
    Button buttonRetry;

    @BindDimen(R.dimen.trending_divider_padding_start)
    float dividerPaddingStart;

    @Inject
    TrendingPresenter trendingPresenter;

    @Inject
    Picasso picasso;

    private RepositoryAdapter repositoryAdapter;

    public TrendingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            ((Hexocat) context.getApplicationContext()).networkComponent()
                    .plus(new TrendingModule())
                    .inject(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        repositoryAdapter = new RepositoryAdapter(LayoutInflater.from(getContext()), picasso,
                viewModel -> Toast.makeText(getContext(), viewModel.name(), Toast.LENGTH_SHORT).show());
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTrending.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewTrending.setAdapter(repositoryAdapter);
        recyclerViewTrending.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL, dividerPaddingStart, isRtl()));

        buttonRetry.setVisibility(View.GONE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        trendingPresenter.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        trendingPresenter.onDetach();
    }

    @Override
    public Observable<SearchQuery> fetchRepositories() {
        Observable<Object> refreshActions = RxSwipeRefreshLayout.refreshes(swipeRefreshLayout);
        Observable<Object> retryActions = RxView.clicks(buttonRetry);

        SearchQuery searchQuery = new SearchQuery.Builder()
                .createdSince(LocalDate.now().minusMonths(1))
                .build();
        return Observable.merge(refreshActions, retryActions)
                .startWith(new Object())
                .switchMap(event -> Observable.just(searchQuery));
    }

    @Override
    public Consumer<TrendingViewState> renderRepositories() {
        return state -> {
            recyclerViewTrending.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
            swipeRefreshLayout.setRefreshing(state.isInProgress());
            buttonRetry.setVisibility(state.isFailure() ? View.VISIBLE : View.GONE);

            if (state.isSuccess()) {
                repositoryAdapter.accept(state.items());
            } else if (state.isFailure()) {
                Toast.makeText(getContext(),
                        state.error(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private boolean isRtl() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 &&
                getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }
}
