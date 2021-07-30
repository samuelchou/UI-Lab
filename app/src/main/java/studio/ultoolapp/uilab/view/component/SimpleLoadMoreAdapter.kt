package studio.ultoolapp.uilab.view.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.ultoolapp.uilab.databinding.ItemLoadingBinding
import studio.ultoolapp.uilab.databinding.ItemSimpleDataBinding

class SimpleLoadMoreAdapter :
    ListAdapter<SimpleData, RecyclerView.ViewHolder>(SimpleItemDiffCallback) {
    companion object {
        private const val VIEW_HOLDER_TYPE_ITEM = 0
        private const val VIEW_HOLDER_TYPE_LOADING = 1
    }

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