package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.abishov.hexocat.R;
import com.abishov.hexocat.common.views.BaseFragment;
import com.abishov.hexocat.github.filters.SearchQuery;
import dagger.android.support.AndroidSupportInjection;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;
import org.threeten.bp.Clock;
import org.threeten.bp.LocalDate;

public final class TrendingFragment extends BaseFragment implements TrendingContract.View {

  private static final String ARG_DAYS = "arg:days";

  @Inject
  Clock clock;

  @Inject
  TrendingContract.Presenter presenter;

  @Nullable
  private TrendingView view;

  public static TrendingFragment create(int days) {
    Bundle arguments = new Bundle();
    arguments.putInt(ARG_DAYS, days);

    TrendingFragment trendingFragment = new TrendingFragment();
    trendingFragment.setArguments(arguments);
    return trendingFragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.trending_view, container, false);
  }

  @Override
  @SuppressFBWarnings("BC_UNCONFIRMED_CAST")
  public void onViewCreated(@NonNull View trendingView, @Nullable Bundle savedInstanceState) {
    view = (TrendingView) trendingView;
    presenter.onAttach(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.onDetach();
    view = null;
  }

  @Override
  public Observable<SearchQuery> searchQueries() {
    if (view != null) {
      int days = getArguments().getInt(ARG_DAYS);
      SearchQuery searchQuery = new SearchQuery.Builder()
          .createdSince(LocalDate.now(clock).minusDays(days))
          .build();
      return Observable.merge(view.onSwipeRefreshLayout(), view.onRetry())
          .startWith(new Object())
          .switchMap(event -> Observable.just(searchQuery));
    }

    return Observable.empty();
  }

  @Override
  public Consumer<TrendingViewState> bindTo() {
    if (view == null) {
      return (state) -> {
        // noop
      };
    }

    return view.bindTo();
  }
}
