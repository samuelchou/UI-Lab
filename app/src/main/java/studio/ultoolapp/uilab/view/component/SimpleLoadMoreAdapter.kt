package studio.ultoolapp.uilab.view.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.ultoolapp.uilab.databinding.ItemLoadingBinding
import studio.ultoolapp.uilab.databinding.ItemSimpleDataBinding

abstract class SimpleLoadMoreAdapter :
    ListAdapter<SimpleData, RecyclerView.ViewHolder>(SimpleItemDiffCallback) {
    companion object {
        private const val TAG = "SimpleLoadMoreAdapter"

        private const val VIEW_HOLDER_TYPE_ITEM = 0
        private const val VIEW_HOLDER_TYPE_LOADING = 1
    }

    /**
     * When item comes to (last - [preloadThreshold])th one, start loading.
     */
    var preloadThreshold = 2
    var isLoading = false

    fun setupWithRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = this

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if (isLoading.not() && lastVisibleItem >= itemCount - 1 - preloadThreshold) {
                    Log.d(TAG, "onScrolled: start loading: item scrolls to $lastVisibleItem")
                    isLoading = true
                    loadMore()
                }
            }
        })
    }

    abstract fun loadMore()

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            VIEW_HOLDER_TYPE_LOADING
        } else VIEW_HOLDER_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_HOLDER_TYPE_LOADING) {
            ItemLoadingBinding.inflate(layoutInflater, parent, false).apply {
                return object : RecyclerView.ViewHolder(this.root) {}
            }
        } else ItemSimpleDataBinding.inflate(layoutInflater, parent, false).apply {
            return SimpleItemViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SimpleItemViewHolder) {
            holder.setItem(getItem(position))
        }
    }
}