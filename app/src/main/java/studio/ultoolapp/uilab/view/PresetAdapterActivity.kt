package studio.ultoolapp.uilab.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import studio.ultoolapp.uilab.R
import studio.ultoolapp.uilab.databinding.ActivityPresetAdapterBinding

class PresetAdapterActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            Intent(context, PresetAdapterActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

    private lateinit var binding: ActivityPresetAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preset_adapter)

        setupPages()
    }

    private fun setupPages() {
        binding.viewPager.run {
            adapter = object : FragmentStateAdapter(this@PresetAdapterActivity) {
                override fun getItemCount(): Int {
                    return 2
                }

                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> ListFragment()
                        else -> PullUpLoadMoreFragment()
                    }
                }
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })

            binding.bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.listFragment -> setCurrentItem(0, true)
                    else -> setCurrentItem(1, true)
                }
                true
            }
        }

    }
}