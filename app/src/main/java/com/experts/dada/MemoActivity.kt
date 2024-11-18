package com.experts.dada

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.experts.dada.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val year = intent.getIntExtra("year", -1)
        val month = intent.getIntExtra("month", -1)
        val dayOfMonth = intent.getIntExtra("dayOfMonth", -1)

        if (year != -1 && month != -1 && dayOfMonth != -1) {
            binding.date.text = "$year. ${month + 1}. $dayOfMonth"
        }

        binding.memoCloseIv.setOnClickListener {
            finish()
        }
    }
}