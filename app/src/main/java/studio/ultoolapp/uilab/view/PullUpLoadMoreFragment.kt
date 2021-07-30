package studio.ultoolapp.uilab.view

import android.os.Bundle
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

class PullUpLoadMoreFragment : Fragment() {

    private lateinit var binding: FragmentPullUpLoadMoreBinding

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
        val mAdapter = SimpleItemAdapter()
        binding.itemContainer.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastVisibleItem =
                        (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val lastItem = mAdapter.itemCount - 1
                    if (lastVisibleItem >= lastItem) {
                        Toast.makeText(context, "Reached bottom.", Toast.LENGTH_SHORT).show()
                        // TODO: 2021/7/30 load more data
                    }
                }
            })
        }

        mAdapter.submitList(getDummyList(30))
    }

    private fun getDummyList(size: Int): List<SimpleData> {
        return mutableListOf<SimpleData>().apply {
            for (i in 0 until size) {
                add(SimpleData("ID%03d".format(i), "Text Title $i"))
            }
        }
    }
}