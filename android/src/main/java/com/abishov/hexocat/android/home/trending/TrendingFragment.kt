package com.abishov.hexocat.android.home.trending

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.abishov.hexocat.android.common.views.BaseFragment
import com.abishov.hexocat.shared.filters.SearchQuery
import com.abishov.hexocat.shared.models.RepositoryModel
import dagger.android.support.AndroidSupportInjection
import org.threeten.bp.Clock
import org.threeten.bp.LocalDate
import javax.inject.Inject

private const val ARG_DAYS = "arg:days"

class TrendingFragment : BaseFragment() {

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
    internal lateinit var trendingViewModelFactory: TrendingViewModelFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext())

    override fun onViewCreated(trendingView: View, savedInstanceState: Bundle?) {
        val days = requireArguments().getInt(ARG_DAYS)
        val searchQuery = SearchQuery(LocalDate.now(clock).minusDays(days.toLong()).toString())

        val viewModel = ViewModelProvider(this, trendingViewModelFactory)
            .get<TrendingViewModel>()

        viewModel.fetchRepositories(searchQuery)
        viewModel.screenState.observe(viewLifecycleOwner) {
            val rootView = view

            val onRepositoryClick: (RepositoryModel) -> Unit = { repository ->
                requireContext().startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(repository.url))
                )
            }

            val onRetry = {
                viewModel.fetchRepositories(searchQuery)
            }

            if (rootView is ComposeView) {
                rootView.setContent { Trending(it, onRepositoryClick, onRetry) }
            }
        }
    }
}
