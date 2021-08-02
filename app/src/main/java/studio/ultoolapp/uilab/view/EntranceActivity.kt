package studio.ultoolapp.uilab.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import studio.ultoolapp.uilab.databinding.ActivityEntranceBinding

class EntranceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()
    }

    private fun setupBinding() {
        // TODO: 2021/8/2 setup btn click link
    }
}