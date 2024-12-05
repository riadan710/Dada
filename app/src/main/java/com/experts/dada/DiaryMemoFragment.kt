package com.experts.dada

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val month = arguments?.getInt("month") ?: 0
        val dayOfMonth = arguments?.getInt("dayOfMonth") ?: 0

        // 해당 날짜에 다이어리가 존재한다면 불러오기
        openDiary(year, month, dayOfMonth)

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

        // 다이어리 삭제하기
        binding.memoDeleteTv.setOnClickListener {
            // 삭제 확인 다이얼로그
            showDeleteConfirmationDialog(year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun initViewPager() {
        val viewPagerAdapter = DiaryMemoViewPagerAdapter(requireActivity())
        binding.memoVp.adapter = viewPagerAdapter
    }

    private fun openDiary(year : Int, month : Int, dayOfMonth : Int) {
        val date = String.format("%04d-%02d-%02d", year, month, dayOfMonth)

        lifecycleScope.launch(Dispatchers.IO) {
            val existingDiary = DiaryDatabase.getDatabase(requireContext()).diaryDao().getDiary(date).firstOrNull()

            if (existingDiary != null) {
                launch(Dispatchers.Main) {
                    AppData.diaryContent = existingDiary.content
                    AppData.diaryWeight = existingDiary.weight
                    AppData.diarySelectedItem = existingDiary.stampId
                    AppData.diaryImage = existingDiary.bodyImg
                    if (existingDiary.isStar) {
                        binding.memoStarIv.visibility = View.VISIBLE
                        binding.memoNotStarIv.visibility = View.GONE
                    } else {
                        binding.memoStarIv.visibility = View.GONE
                        binding.memoNotStarIv.visibility = View.VISIBLE
                    }
                    binding.memoDeleteTv.visibility = View.VISIBLE
                    // bodyImg 처리 (필요 시 추가 구현)
                }
            } else {
                launch(Dispatchers.Main) {
                    AppData.diarySelectedItem = 0
                    AppData.diaryContent = ""
                    AppData.diaryWeight = ""
                    AppData.diaryImage = ""
                    binding.memoDeleteTv.visibility = View.GONE
                }
            }
        }
    }

    private fun saveDiary(year : Int, month : Int, dayOfMonth : Int) {
        val date = String.format("%04d-%02d-%02d", year, month, dayOfMonth)
        val content = view?.findViewById<EditText>(R.id.memo2_content_et)?.text.toString()
        val stampId = AppData.diarySelectedItem
        val weight = view?.findViewById<EditText>(R.id.memo2_weight2_et)?.text.toString()
        val isStar = (binding.memoStarIv.visibility == View.VISIBLE)
        val bodyImg = AppData.diaryImage

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
                // existingDiary의 id를 사용하여 업데이트
                val updatedDiary = existingDiary.copy(
                    content = content,
                    stampId = stampId,
                    weight = weight,
                    isStar = isStar,
                    bodyImg = bodyImg
                )

                DiaryDatabase.getDatabase(requireContext()).diaryDao().update(updatedDiary)
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "다이어리가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
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

    private fun showDeleteConfirmationDialog(year: Int, month: Int, dayOfMonth: Int) {
        // 다이얼로그 생성
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("삭제 확인")
            .setMessage("정말로 다이어리를 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                // 사용자가 '삭제'를 클릭했을 때
                deleteDiary(year, month, dayOfMonth)
            }
            .setNegativeButton("취소") { dialog, _ ->
                // 사용자가 '취소'를 클릭했을 때
                dialog.dismiss()  // 다이얼로그 닫기
            }
            .create()

        // 다이얼로그 텍스트 색 main_blue로 변경
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00C1D3")) // main_blue 색으로 설정
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY) // grey 색으로 설정
        }

        // 다이얼로그 보여주기
        dialog.show()
    }

    fun deleteDiary(year : Int, month : Int, dayOfMonth : Int) {
        val date = String.format("%04d-%02d-%02d", year, month, dayOfMonth)

        lifecycleScope.launch(Dispatchers.IO) {
            val existingDiary = DiaryDatabase.getDatabase(requireContext()).diaryDao().getDiary(date).firstOrNull()

            if (existingDiary != null) {
                DiaryDatabase.getDatabase(requireContext()).diaryDao().delete(existingDiary)
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "다이어리가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "삭제할 다이어리가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}