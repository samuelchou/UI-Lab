package studio.ultoolapp.uilab.view

import android.os.Bundle
import android.widget.Toast
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
        binding.btnAdapter.setOnClickListener {
            MainActivity.start(this)
        }
        binding.btnView.setOnClickListener {
            Toast.makeText(this, "Page not set yet", Toast.LENGTH_SHORT).show()
        }
    }
}