package studio.ultoolapp.uilab.view.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class EasyBindingAdapter<T, VB : ViewDataBinding>(
    diff: DiffUtil.ItemCallback<T>,
    @LayoutRes val bindingLayoutId: Int,
) : ListAdapter<T, EasyBindingAdapter.AbViewHolder<VB>>(diff) {

    class AbViewHolder<VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbViewHolder<VB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: VB =
            DataBindingUtil.inflate(layoutInflater, bindingLayoutId, parent, false)
        return AbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbViewHolder<VB>, position: Int) {
        bindData(holder.binding, position, getItem(position))
    }

    abstract fun bindData(binding: VB, position: Int, data: T)
}
