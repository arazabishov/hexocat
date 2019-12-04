package com.abishov.hexocat.home.trending

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.abishov.hexocat.R
import com.abishov.hexocat.common.views.DividerItemDecoration
import com.abishov.hexocat.home.repository.RepositoryAdapter
import com.abishov.hexocat.home.repository.RepositoryAdapter.TrendingViewClickListener
import com.abishov.hexocat.home.repository.RepositoryViewModel
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable

class TrendingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

  @BindView(R.id.swipe_refresh_layout_trending)
  internal lateinit var swipeRefreshLayout: SwipeRefreshLayout

  @BindView(R.id.recyclerview_trending)
  internal lateinit var recyclerViewTrending: RecyclerView

  @BindView(R.id.button_retry)
  internal lateinit var buttonRetry: Button

  @BindView(R.id.textview_error)
  internal lateinit var textViewError: TextView

  private lateinit var repositoryAdapter: RepositoryAdapter

  private var dividerPaddingStart: Float =
    resources.getDimension(R.dimen.trending_divider_padding_start)

  private val isRtl: Boolean
    get() {
      return layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

  override fun onFinishInflate() {
    super.onFinishInflate()
    ButterKnife.bind(this)

    buttonRetry.visibility = View.GONE

    repositoryAdapter = RepositoryAdapter(
      LayoutInflater.from(context),
      onRepositoryItemClick()
    )

    recyclerViewTrending.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = repositoryAdapter

      addItemDecoration(
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL, dividerPaddingStart, isRtl)
      )
    }
  }

  fun onSwipeRefreshLayout(): Observable<Any> {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
  }

  fun onRetry(): Observable<Any> {
    return RxView.clicks(buttonRetry)
  }

  fun bindTo(state: TrendingViewState) {
    recyclerViewTrending.visibility =
        if (state is TrendingViewState.Success) {
          View.VISIBLE
        } else {
          View.GONE
        }

    swipeRefreshLayout.isRefreshing = state is TrendingViewState.InProgress
    buttonRetry.visibility = if (state is TrendingViewState.Failure) View.VISIBLE else View.GONE
    textViewError.visibility = if (state is TrendingViewState.Failure) View.VISIBLE else View.GONE

    if (state is TrendingViewState.Success) {
      repositoryAdapter.accept(state.items)
    } else if (state is TrendingViewState.Failure) {
      textViewError.text = state.error
    }
  }

  private fun onRepositoryItemClick(): TrendingViewClickListener {
    return object : TrendingViewClickListener {
      override fun onRepositoryClick(repository: RepositoryViewModel) {
        context.startActivity(
          Intent(Intent.ACTION_VIEW, Uri.parse(repository.url))
        )
      }
    }
  }
}
