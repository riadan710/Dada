package com.experts.dada

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.experts.dada.databinding.FragmentDiaryBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.coroutines.launch

class DiaryFragment : Fragment() {

    lateinit var binding: FragmentDiaryBinding

    private lateinit var diaryDatabase: DiaryDatabase
    private lateinit var diaryDao: DiaryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        diaryDatabase = DiaryDatabase.getDatabase(requireContext()) // Database 인스턴스 초기화
        diaryDao = diaryDatabase.diaryDao() // DAO 초기화

        // 초기 화면을 로드할 때, 현재 월에 해당하는 다이어리 정보를 로드
        val currentMonth = "${binding.diaryCv.currentDate.year}-${binding.diaryCv.currentDate.month}"
        loadDiaryForMonth(currentMonth)

        // 월이 변경될 때마다 해당 달의 다이어리 정보 조회
        binding.diaryCv.setOnMonthChangedListener { widget, date ->
            val selectedMonth = "${date.year}-${date.month}"
            loadDiaryForMonth(selectedMonth)
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

    private fun loadDiaryForMonth(month: String) {
        lifecycleScope.launch {
            diaryDao.getDiaryByMonth(month).collect { diaries ->
                // 기존 데코레이터 제거
                binding.diaryCv.removeDecorators()

                // 각 날짜에 대해 스탬프 이미지 설정
                diaries.forEach { diary ->
                    val dateParts = diary.date.split("-")
                    val year = dateParts[0].toInt()
                    val month = dateParts[1].toInt()
                    val day = dateParts[2].toInt()
                    val calendarDay = CalendarDay.from(year, month, day)

                    val stampId = diary.stampId
                    val resourceName = "stamp$stampId"  // "stamp1", "stamp2"와 같은 문자열 생성

                    // 동적으로 리소스를 가져옴
                    val resId = resources.getIdentifier(resourceName, "drawable", requireContext().packageName)
                    val stampDrawable: Drawable? = if (resId != 0) {
                        ContextCompat.getDrawable(requireContext(), resId)
                    } else {
                        null  // 리소스가 없으면 null 처리
                    }

                    // 해당 날짜에 대한 스탬프 이미지 설정
                    stampDrawable?.let {
                        binding.diaryCv.addDecorator(DiaryDecorator(calendarDay, it))
                    }
                }
            }
        }
    }
}