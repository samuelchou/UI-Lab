package studio.ultoolapp.uilab.view.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class EasyBindingAdapter<ITEM, ITEM_VB : ViewDataBinding>(
    @LayoutRes private val itemLayoutId: Int,
    diff: DiffUtil.ItemCallback<ITEM>,
    private val lifecycleOwner: LifecycleOwner? = null,
) : ListAdapter<ITEM, EasyBindingAdapter.AbViewHolder<ITEM_VB>>(diff) {

    class AbViewHolder<VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbViewHolder<ITEM_VB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ITEM_VB =
            DataBindingUtil.inflate(layoutInflater, itemLayoutId, parent, false)
        lifecycleOwner?.let { binding.lifecycleOwner = it }
        return AbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbViewHolder<ITEM_VB>, position: Int) {
        bindData(holder.binding, position, getItem(position))
    }

    abstract fun bindData(binding: ITEM_VB, position: Int, data: ITEM)
}
