package com.experts.dada

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import com.experts.dada.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        binding.diaryCv.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
                Toast.makeText(activity, "$year. ${month + 1}. $dayOfMonth", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, MemoActivity::class.java)
                intent.putExtra("year", year)
                intent.putExtra("month", month)
                intent.putExtra("dayOfMonth", dayOfMonth)
                startActivity(intent)
            }
        })

        return binding.root
    }
}