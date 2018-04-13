package com.abishov.hexocat.home.repository

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.abishov.hexocat.R
import com.abishov.hexocat.home.repository.RepositoryAdapter.RepositoryViewHolder
import io.reactivex.functions.Consumer
import java.util.*

class RepositoryAdapter(
  private val layoutInflater: LayoutInflater,
  private val trendingViewClickListener: TrendingViewClickListener,
  private val repositories: MutableList<RepositoryViewModel> = ArrayList()
) : Adapter<RepositoryViewHolder>(), Consumer<List<RepositoryViewModel>> {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
    return RepositoryViewHolder(
      layoutInflater.inflate(
        R.layout.recyclerview_item_repository, parent, false
      ) as RepositoryItemView
    )
  }

  override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
    holder.bindTo(repositories[position])
  }

  override fun getItemCount(): Int {
    return repositories.size
  }

  override fun accept(repositoryViewModels: List<RepositoryViewModel>) { // NOPMD
    repositories.clear()
    repositories.addAll(repositoryViewModels)
    notifyDataSetChanged()
  }

  interface TrendingViewClickListener {
    fun onRepositoryClick(repository: RepositoryViewModel)
  }

  inner class RepositoryViewHolder(private val repositoryItemView: RepositoryItemView) :
    RecyclerView.ViewHolder(repositoryItemView) {

    init {
      repositoryItemView.setOnClickListener {
        val repository = repositories[adapterPosition]
        trendingViewClickListener.onRepositoryClick(repository)
      }
    }

    fun bindTo(viewModel: RepositoryViewModel) {
      repositoryItemView.bindTo(viewModel)
    }
  }
}
