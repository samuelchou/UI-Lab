package studio.ultoolapp.uilab.view.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.ultoolapp.uilab.databinding.ItemSimpleDataBinding

class SimpleItemAdapter : ListAdapter<SimpleData, SimpleItemViewHolder>(SimpleItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSimpleDataBinding.inflate(layoutInflater, parent, false)
        return SimpleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
        holder.setItem(getItem(position))
    }
}

class SimpleItemViewHolder(private val binding: ItemSimpleDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: SimpleData) {
        binding.textTitle.text = item.text
        binding.textSubTitle.text = "ID: ${item.id}"
    }
}

object SimpleItemDiffCallback : DiffUtil.ItemCallback<SimpleData>() {
    override fun areItemsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SimpleData, newItem: SimpleData): Boolean {
        return oldItem == newItem
    }

}