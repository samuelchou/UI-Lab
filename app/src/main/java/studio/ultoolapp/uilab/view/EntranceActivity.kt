package studio.ultoolapp.uilab.view

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.ChipDrawable
import studio.ultoolapp.uilab.R
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
            PresetAdapterActivity.start(this)
        }
        binding.btnView.setOnClickListener {
            CustomAdapterActivity.start(this)
        }

        val chipDrawable = ChipDrawable.createFromResource(this, R.xml.intext_chip)
        chipDrawable.apply {
            setTextColor(Color.GREEN)
            text = "CHIP"
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val span = ImageSpan(chipDrawable)

        val spannableString = SpannableString(binding.textView.text)
        spannableString.setSpan(
            span, 6, spannableString.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.textView.text = spannableString
    }
}