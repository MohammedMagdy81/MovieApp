package ddd.magdy.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ddd.magdy.movieapp.databinding.ItemLoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadMoreAdapter.LoadMoreViewHolder>() {

    inner class LoadMoreViewHolder(private val loadMoreBinding: ItemLoadMoreBinding,retry: () -> Unit) :
        RecyclerView.ViewHolder(loadMoreBinding.root) {

        init {
            loadMoreBinding.btnRetry.setOnClickListener {
                retry()
            }
        }


        fun setData(state: LoadState) {
            loadMoreBinding.apply {
                progressLoadMore.isVisible = state is LoadState.Loading
                tvErrorHappen.isVisible = state is LoadState.Error
                btnRetry.isVisible = state is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreViewHolder {
        val loadMoreBinding: ItemLoadMoreBinding = ItemLoadMoreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LoadMoreViewHolder(loadMoreBinding,retry)
    }
}