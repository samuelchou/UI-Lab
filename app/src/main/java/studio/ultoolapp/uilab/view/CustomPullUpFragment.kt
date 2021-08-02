package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import studio.ultoolapp.uilab.R
import studio.ultoolapp.uilab.databinding.FragmentPullUpLoadMoreBinding
import studio.ultoolapp.uilab.databinding.ItemLoadingBinding
import studio.ultoolapp.uilab.databinding.ItemSimpleDataBinding
import studio.ultoolapp.uilab.view.component.EasyLoadMoreAdapter
import studio.ultoolapp.uilab.view.component.SimpleData
import studio.ultoolapp.uilab.view.component.SimpleItemDiffCallback
import java.util.*
import kotlin.concurrent.schedule

class CustomPullUpFragment : Fragment() {

    private lateinit var binding: FragmentPullUpLoadMoreBinding
    private var alreadyLoaded = 0
    private lateinit var mAdapter: EasyLoadMoreAdapter<SimpleData, ItemSimpleDataBinding, ItemLoadingBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPullUpLoadMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
    }

    private fun setupList() {
        mAdapter =
            object : EasyLoadMoreAdapter<SimpleData, ItemSimpleDataBinding, ItemLoadingBinding>(
                R.layout.item_simple_data,
                R.layout.item_loading,
                SimpleItemDiffCallback,
                this@CustomPullUpFragment
            ) {

                override fun bindData(
                    binding: ItemSimpleDataBinding,
                    position: Int,
                    data: SimpleData,
                ) {
                    binding.textTitle.text = data.text
                    binding.textSubTitle.text = "ID: ${data.id}"
                }

                override fun bindLoading(binding: ItemLoadingBinding, position: Int) {
                    binding.textView.text = "Loading...[Current loaded $alreadyLoaded]"
                }

                override fun loadMore() {
                    this@CustomPullUpFragment.loadMore()
                }

                override fun canLoadMore(totalItemCountNow: Int): Boolean {
                    return alreadyLoaded <= 50
                }
            }

        binding.itemContainer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            mAdapter.setupWithRecyclerView(this)
        }

        mAdapter.submitList(getDummyList(30))
        alreadyLoaded = 30
    }

    private fun loadMore() {
        Timer().schedule(800) {
            binding.root.post {
                alreadyLoaded += 10
                mAdapter.submitList(getDummyList(alreadyLoaded))
                mAdapter.isLoading = false
            }
        }
    }

    private fun getDummyList(size: Int): List<SimpleData> {
        return mutableListOf<SimpleData>().apply {
            for (i in 0 until size) {
                add(SimpleData("ID%03d".format(i), "Text Title $i"))
            }
        }
    }
}