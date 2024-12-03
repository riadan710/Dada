package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.experts.dada.databinding.FragmentDiaryMemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DiaryMemoFragment : Fragment() {

    lateinit var binding: FragmentDiaryMemoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMemoBinding.inflate(inflater, container, false)
        initViewPager()

        val year = arguments?.getInt("year") ?: 0
        val month = arguments?.getInt("month")?.let { it + 1 } ?: 0
        val dayOfMonth = arguments?.getInt("dayOfMonth") ?: 0

        binding.memoDateTv.text = String.format("%04d년 %02d월 %02d일", year, month, dayOfMonth)


        binding.memoCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.memoNotStarIv.setOnClickListener {
            binding.memoNotStarIv.visibility = View.GONE
            binding.memoStarIv.visibility = View.VISIBLE
        }
        binding.memoStarIv.setOnClickListener {
            binding.memoNotStarIv.visibility = View.VISIBLE
            binding.memoStarIv.visibility = View.GONE
        }

        // 다이어리 저장하기
        binding.memoSaveLl.setOnClickListener {
            saveDiary(year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun initViewPager() {
        val viewPagerAdapter = DiaryMemoViewPagerAdapter(requireActivity())
        binding.memoVp.adapter = viewPagerAdapter
    }

    private fun saveDiary(year : Int, month : Int, dayOfMonth : Int) {
        val date = String.format("%04d-%02d-%02d", year, month, dayOfMonth)
        val content = view?.findViewById<EditText>(R.id.memo2_content_et)?.text.toString()
        val stampId = AppData.diarySelectedItem
        val weight = view?.findViewById<EditText>(R.id.memo2_weight2_et)?.text.toString()
        val isStar = (binding.memoStarIv.visibility == View.VISIBLE)
        val bodyImg = R.drawable.eyebody_example // 임시값

        // 입력 값이 비어있는지 체크
        if (weight.isEmpty()) {
            Toast.makeText(requireContext(), "몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (content.isEmpty()) {
            Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(stampId == 0) {
            Toast.makeText(requireContext(), "스탬프를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val diary = Diary(
            date = date,
            content = content,
            stampId = stampId,
            weight = weight,
            isStar = isStar,
            bodyImg = bodyImg
        )


        lifecycleScope.launch(Dispatchers.IO) {
            val existingDiary = DiaryDatabase.getDatabase(requireContext()).diaryDao().getDiary(date).firstOrNull()

            if (existingDiary != null) {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "이미 해당 날짜에 다이어리가 존재합니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                DiaryDatabase.getDatabase(requireContext()).diaryDao().insert(diary)
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "다이어리가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }
}