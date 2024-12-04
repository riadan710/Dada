package com.experts.dada

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.experts.dada.databinding.FragmentDiaryBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        // Custom Day Decorator 추가
        val specialDate = CalendarDay.from(2024, 12, 25) // 예: 2024년 12월 25일
        val drawable: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.stamp1)

        drawable?.let {
            binding.diaryCv.addDecorator(DiaryDecorator(specialDate, it))
        }

        // 같은 날짜 두 번 클릭 시 해당 날짜 다이어리 작성으로 이동
        var preSelectedDate: CalendarDay? = null
        binding.diaryCv.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            // 두번 눌러야 들어가지게
            if (preSelectedDate == date) {
                val bundle = Bundle().apply {
                    putInt("year", date.year)
                    putInt("month", date.month)
                    putInt("dayOfMonth", date.day)
                }

                val fragmentMemo = DiaryMemoFragment().apply {
                    arguments = bundle
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragmentMemo)
                    .addToBackStack(null)
                    .commit()
            } else {
                preSelectedDate = date
            }
        })

        return binding.root
    }
}