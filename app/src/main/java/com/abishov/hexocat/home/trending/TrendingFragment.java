package com.abishov.hexocat.home.trending;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abishov.hexocat.Hexocat;
import com.abishov.hexocat.R;
import com.abishov.hexocat.commons.network.github.SearchQuery;
import com.abishov.hexocat.commons.views.BaseFragment;

import org.threeten.bp.LocalDate;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class TrendingFragment extends BaseFragment implements TrendingContract.View {
    private static final String ARG_DAYS = "arg:days";

    private TrendingView view;

    @Inject
    TrendingPresenter presenter;

    public static TrendingFragment create(int days) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_DAYS, days);

        TrendingFragment trendingFragment = new TrendingFragment();
        trendingFragment.setArguments(arguments);
        return trendingFragment;
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
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trending_view, container, false);
    }

    @Override
    public void onViewCreated(View trendingView, @Nullable Bundle savedInstanceState) {
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
        int days = getArguments().getInt(ARG_DAYS);
        SearchQuery searchQuery = new SearchQuery.Builder()
                .createdSince(LocalDate.now().minusDays(days))
                .build();
        return Observable.merge(view.onSwipeRefreshLayout(), view.onRetry())
                .startWith(new Object())
                .switchMap(event -> Observable.just(searchQuery));
    }

    @Override
    public Consumer<TrendingViewState> bindTo() {
        return view.bindTo();
    }
}