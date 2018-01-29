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
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.abishov.hexocat.R;
import com.abishov.hexocat.common.views.DividerItemDecoration;
import com.abishov.hexocat.home.repository.RepositoryAdapter;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class TrendingView extends FrameLayout {

  @BindView(R.id.swipe_refresh_layout_trending)
  SwipeRefreshLayout swipeRefreshLayout;

  @BindView(R.id.recyclerview_trending)
  RecyclerView recyclerViewTrending;

  @BindView(R.id.button_retry)
  Button buttonRetry;

  @BindView(R.id.textview_error)
  TextView textViewError;

  @BindDimen(R.dimen.trending_divider_padding_start)
  float dividerPaddingStart;

  private RepositoryAdapter repositoryAdapter;

  public TrendingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    repositoryAdapter = new RepositoryAdapter(LayoutInflater.from(getContext()),
        viewModel -> Toast.makeText(getContext(), viewModel.name(), Toast.LENGTH_SHORT).show());
    RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getContext());
    recyclerViewTrending.setLayoutManager(recyclerViewLayoutManager);
    recyclerViewTrending.setAdapter(repositoryAdapter);
    recyclerViewTrending.addItemDecoration(new DividerItemDecoration(getContext(),
        DividerItemDecoration.VERTICAL, dividerPaddingStart, isRtl()));

    buttonRetry.setVisibility(View.GONE);
  }

  public Observable<Object> onSwipeRefreshLayout() {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout);
  }

  public Observable<Object> onRetry() {
    return RxView.clicks(buttonRetry);
  }

  public Consumer<TrendingViewState> bindTo() { // NOPMD
    return state -> {
      recyclerViewTrending.setVisibility(state.isSuccess() ? View.VISIBLE : View.GONE);
      swipeRefreshLayout.setRefreshing(state.isInProgress());
      buttonRetry.setVisibility(state.isFailure() ? View.VISIBLE : View.GONE);
      textViewError.setVisibility(state.isFailure() ? View.VISIBLE : View.GONE);

      if (state.isSuccess()) {
        repositoryAdapter.accept(state.items());
      } else if (state.isFailure()) {
        textViewError.setText(state.error());
      }
    };
  }

  private boolean isRtl() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 &&
        getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
  }
}
