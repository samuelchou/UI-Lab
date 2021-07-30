package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import studio.ultoolapp.uilab.databinding.FragmentPullUpLoadMoreBinding
import studio.ultoolapp.uilab.view.component.SimpleData
import studio.ultoolapp.uilab.view.component.SimpleItemAdapter
import java.util.*
import kotlin.concurrent.schedule

class PullUpLoadMoreFragment : Fragment() {

    private lateinit var binding: FragmentPullUpLoadMoreBinding
    private var alreadyLoaded = 0
    private var isLoading = false

    private var mAdapter: SimpleItemAdapter? = null

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
        mAdapter = SimpleItemAdapter()
        binding.itemContainer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItem =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val lastItem = adapter!!.itemCount - 1
                    Log.d("PullUpLoadMore",
                        "onScrolled: scroll to $lastVisibleItem / last item is $lastItem")
                    if (lastVisibleItem >= lastItem && isLoading.not()) {
                        Toast.makeText(context, "Reached bottom.", Toast.LENGTH_SHORT).show()
                        loadMore()
                    }
                }
            })
        }

        mAdapter?.submitList(getDummyList(30))
        alreadyLoaded = 30
    }

    private fun loadMore() {
        isLoading = true
        Timer().schedule(800) {
            binding.root.post {
                alreadyLoaded += 10
                mAdapter?.submitList(getDummyList(alreadyLoaded))
                isLoading = false
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