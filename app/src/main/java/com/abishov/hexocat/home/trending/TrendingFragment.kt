package com.abishov.hexocat.home.trending

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abishov.hexocat.R
import com.abishov.hexocat.common.views.BaseFragment
import com.abishov.hexocat.github.filters.SearchQuery
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.threeten.bp.Clock
import org.threeten.bp.LocalDate
import javax.inject.Inject

private const val ARG_DAYS = "arg:days"

fun create(days: Int): TrendingFragment {
  return TrendingFragment().apply {
    arguments = Bundle().apply {
      putInt(ARG_DAYS, days)
    }
  }
}

class TrendingFragment : BaseFragment(), TrendingContract.View {

  @Inject
  internal lateinit var clock: Clock

  @Inject
  internal lateinit var presenter: TrendingContract.Presenter

  private var view: TrendingView? = null

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ) = inflater.inflate(R.layout.trending_view, container, false)

  // @SuppressFBWarnings("BC_UNCONFIRMED_CAST")
  override fun onViewCreated(trendingView: View, savedInstanceState: Bundle?) {
    view = trendingView as TrendingView
    presenter.onAttach(this)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.onDetach()
    view = null
  }

  override fun searchQueries(): Observable<SearchQuery> {
    val trendingView = view ?: return Observable.empty()

    val days = arguments!!.getInt(ARG_DAYS)
    val searchQuery = SearchQuery.Builder()
      .createdSince(LocalDate.now(clock).minusDays(days.toLong()))
      .build()
    return Observable.merge(trendingView.onSwipeRefreshLayout(), trendingView.onRetry())
      .startWith(Any())
      .switchMap { Observable.just(searchQuery) }
  }

  override fun bindTo() = Consumer<TrendingViewState> { view?.bindTo(it) }

}
