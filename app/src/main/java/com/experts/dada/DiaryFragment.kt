package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.experts.dada.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        // 같은 날짜 두 번 클릭 시 해당 날짜 다이어리 작성으로 이동
        var preSelectedDate: Triple<Int, Int, Int>? = null
        binding.diaryCv.setOnDateChangeListener {_, year, month, dayOfMonth ->
            val selectedDate = Triple(year, month, dayOfMonth)

            if (preSelectedDate == selectedDate) {
                val bundle = Bundle().apply {
                    putInt("year", year)
                    putInt("month", month)
                    putInt("dayOfMonth", dayOfMonth)
                }

                val fragmentMemo = DiaryMemoFragment().apply {
                    arguments = bundle
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragmentMemo)
                    .addToBackStack(null)
                    .commit()
            }
            else {
                preSelectedDate = selectedDate
            }
        }

        return binding.root
    }
}