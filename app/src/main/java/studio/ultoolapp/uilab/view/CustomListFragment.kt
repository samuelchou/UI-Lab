package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import studio.ultoolapp.uilab.R
import studio.ultoolapp.uilab.databinding.FragmentListBinding
import studio.ultoolapp.uilab.databinding.ItemSimpleDataBinding
import studio.ultoolapp.uilab.view.component.EasyBindingAdapter
import studio.ultoolapp.uilab.view.component.SimpleData
import studio.ultoolapp.uilab.view.component.SimpleItemDiffCallback
import java.util.*
import kotlin.concurrent.schedule

class CustomListFragment : Fragment() {

    private lateinit var mBinding: FragmentListBinding
    private var mAdapter: EasyBindingAdapter<SimpleData, ItemSimpleDataBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupSwipeRefresh()
    }

    private fun setupList() {
        mAdapter = object : EasyBindingAdapter<SimpleData, ItemSimpleDataBinding>(
            R.layout.item_simple_data, SimpleItemDiffCallback) {
            override fun bindData(binding: ItemSimpleDataBinding, position: Int, data: SimpleData) {
                binding.textTitle.text = data.text
                binding.textSubTitle.text = "ID: ${data.id}"
            }
        }
        mBinding.itemContainer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        mAdapter?.submitList(getDummyList(System.currentTimeMillis()))
    }

    private fun setupSwipeRefresh() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        mBinding.swipeRefreshLayout.isRefreshing = true // reset loading status
        Timer().schedule(800) {
            mBinding.root.post {
                mAdapter?.submitList(getDummyList(System.currentTimeMillis()))
                mBinding.swipeRefreshLayout.isRefreshing = false // reset loading status
            }
        }
    }

    private fun getDummyList(time: Long): List<SimpleData> {
        return mutableListOf<SimpleData>().apply {
            for (i in 0 until 30) {
                add(SimpleData("ID%03d".format(i), "Text Title $i (Refreshed at $time)"))
            }
        }
    }
}