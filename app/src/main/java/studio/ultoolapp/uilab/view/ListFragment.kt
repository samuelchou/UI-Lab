package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import studio.ultoolapp.uilab.databinding.FragmentListBinding
import studio.ultoolapp.uilab.view.component.SimpleData
import studio.ultoolapp.uilab.view.component.SimpleItemAdapter
import java.util.*
import kotlin.concurrent.schedule

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var mAdapter: SimpleItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupSwipeRefresh()
    }

    private fun setupList() {
        mAdapter = SimpleItemAdapter()
        binding.itemContainer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        mAdapter?.submitList(getDummyList(System.currentTimeMillis()))
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.isRefreshing = true // reset loading status
        Timer().schedule(800) {
            binding.root.post {
                mAdapter?.submitList(getDummyList(System.currentTimeMillis()))
                binding.swipeRefreshLayout.isRefreshing = false // reset loading status
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