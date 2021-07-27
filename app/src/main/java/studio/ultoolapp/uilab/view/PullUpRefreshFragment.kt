package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.ultoolapp.uilab.databinding.FragmentPullUpRefreshBinding

class PullUpRefreshFragment : Fragment() {

    private lateinit var binding: FragmentPullUpRefreshBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPullUpRefreshBinding.inflate(inflater, container, false)
        return binding.root
    }
}