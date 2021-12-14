package studio.ultoolapp.uilab.view.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * EasyLoadMoreAdapter by STC, 2021/08
 * Usage: set on a linear list with items, it will provides a "Load More" operations on scroll to bottom.
 *
 * Steps:
 * 1. Prepare Layout: item and loading, with DataBinding structure.
 * 2. Inherit and Override: [bindData], [canLoadMore], [loadMore] (and [bindLoading] if needed)
 * 3. Prepare RecyclerView: must be set with LinearLayoutManager. Horizontal / Vertical is both accepted.
 * 4. Add Scroll Listener: call [setupWithRecyclerView]
 * 5. Enjoy your endless-scroll list!
 */
abstract class EasyLoadMoreAdapter<ITEM, ITEM_VB : ViewDataBinding, LOADING_VB : ViewDataBinding>(
    @LayoutRes private val itemLayoutId: Int,
    @LayoutRes private val loadingLayoutId: Int,
    diff: DiffUtil.ItemCallback<ITEM>,
    private val lifecycleOwner: LifecycleOwner? = null,
) : ListAdapter<ITEM, RecyclerView.ViewHolder>(diff) {
    companion object {
        private const val TAG = "EasyLoadMoreAdapter"

        private const val VIEW_HOLDER_TYPE_ITEM = 0
        private const val VIEW_HOLDER_TYPE_LOADING = 1
    }

    class AbViewHolder<VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * When item comes to (last - [preloadThreshold])th one, start loading.
     */
    var preloadThreshold = 2
    var isLoading = false
        set(value) {
            loadingViewHolder?.itemView?.isVisible = value
            field = value
        }

    private var loadingViewHolder: RecyclerView.ViewHolder? = null

    private lateinit var originalLoadingParams: ViewGroup.LayoutParams

    fun setupWithRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = this

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if (canLoadMore(itemCount - 1).not()) return
                if (isLoading.not() && lastVisibleItem >= itemCount - 1 - preloadThreshold) {
                    Log.d(TAG, "onScrolled: start loading: item scrolls to $lastVisibleItem")
                    isLoading = true
                    loadMore()
                }
            }
        })
    }

    abstract fun loadMore()

    abstract fun canLoadMore(totalItemCountNow: Int): Boolean

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
        return if (viewType != VIEW_HOLDER_TYPE_LOADING) {
            val binding: ITEM_VB =
                DataBindingUtil.inflate(layoutInflater, itemLayoutId, parent, false)
            lifecycleOwner?.let { binding.lifecycleOwner = it }
            AbViewHolder(binding)
        } else {
            val binding: LOADING_VB =
                DataBindingUtil.inflate(layoutInflater, loadingLayoutId, parent, false)
            lifecycleOwner?.let { binding.lifecycleOwner = it }
            AbViewHolder(binding).apply {
                originalLoadingParams = itemView.layoutParams
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_HOLDER_TYPE_ITEM) {
            if (holder is AbViewHolder<*>) {
                @Suppress("UNCHECKED_CAST")
                bindData(holder.binding as ITEM_VB, position, getItem(position))
            } else {
                Log.e(
                    TAG, "onBindViewHolder: failed bind data at position $position: " +
                            "type detected as " + holder::class.simpleName,
                    TypeCastException("ViewHolder")
                )
            }
        } else if (getItemViewType(position) == VIEW_HOLDER_TYPE_LOADING) {
            holder.itemView.isVisible = isLoading
            loadingViewHolder = holder
            // to avoid spaces at bottom when cannot load anymore
            holder.itemView.layoutParams = if (isLoading) {
                originalLoadingParams
            } else {
                RecyclerView.LayoutParams(0, 0)
            }

            if (holder is AbViewHolder<*>) {
                @Suppress("UNCHECKED_CAST")
                bindLoading(holder.binding as LOADING_VB, position)
            } else {
                Log.e(
                    TAG, "onBindViewHolder: failed bind loading at position $position: " +
                            "type detected as " + holder::class.simpleName,
                    TypeCastException("ViewHolder")
                )
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder == loadingViewHolder) loadingViewHolder = null
    }

    abstract fun bindData(binding: ITEM_VB, position: Int, data: ITEM)

    /**
     * override this function if you need custom loading.
     */
    open fun bindLoading(binding: LOADING_VB, position: Int) {}
}