package com.abishov.hexocat.home.trending

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.abishov.hexocat.common.views.BaseFragment
import com.abishov.hexocat.components.RepositoryViewModel
import com.abishov.hexocat.github.filters.SearchQuery
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.Clock
import org.threeten.bp.LocalDate
import javax.inject.Inject

private const val ARG_DAYS = "arg:days"

class TrendingFragment : BaseFragment(), TrendingContract.View {
  private val retryButtonSubject = PublishSubject.create<Any>()

  companion object {
    fun create(days: Int): TrendingFragment {
      return TrendingFragment().apply {
        arguments = Bundle().apply {
          putInt(ARG_DAYS, days)
        }
      }
    }
  }

  @Inject
  internal lateinit var clock: Clock

  @Inject
  internal lateinit var presenter: TrendingContract.Presenter

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? = ComposeView(requireContext())

  override fun onViewCreated(trendingView: View, savedInstanceState: Bundle?) {
    presenter.onAttach(this)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.onDetach()
  }

  override fun searchQueries(): Observable<SearchQuery> {
    val days = requireArguments().getInt(ARG_DAYS)
    val searchQuery = SearchQuery(LocalDate.now(clock).minusDays(days.toLong()))

    return retryButtonSubject
      .startWith(Any())
      .switchMap { Observable.just(searchQuery) }
  }

  override fun bindTo() = Consumer<TrendingViewState> {
    val rootView = view

    val onRepositoryClick: (RepositoryViewModel) -> Unit = { repository ->
      requireContext().startActivity(
        Intent(Intent.ACTION_VIEW, repository.url)
      )
    }

    val onRetry = {
      retryButtonSubject.onNext(Object())
    }

    if (rootView is ComposeView) {
      rootView.setContent { Trending(it, onRepositoryClick, onRetry) }
    }
  }
}
