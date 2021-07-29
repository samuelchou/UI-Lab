package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.ultoolapp.uilab.databinding.FragmentListBinding
import java.util.*
import kotlin.concurrent.schedule

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

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
        setupSwipeRefresh()
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
                binding.textView.text = "Hello List\nTime: ${System.currentTimeMillis()}"
                binding.swipeRefreshLayout.isRefreshing = false // reset loading status
            }
        }
    }
}