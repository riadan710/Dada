package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.experts.dada.databinding.FragmentDiaryMemo2Binding
import kotlinx.coroutines.launch

class DiaryMemo2Fragment : Fragment() {
    lateinit var binding: FragmentDiaryMemo2Binding
    private var selectedItemId: Int? = null // 선택된 이미지의 ID를 저장할 변수
    private lateinit var stampDao: StampDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryMemo2Binding.inflate(inflater, container,false)

        // StampDao 초기화
        val diaryDatabase = DiaryDatabase.getDatabase(requireContext())
        stampDao = diaryDatabase.stampDao()

        // 로딩된 데이터 적용
        binding.memo2Weight2Et.setText(AppData.diaryWeight)
        binding.memo2ContentEt.setText(AppData.diaryContent)

        // 구매된 스탬프를 불러와서 이미지 뷰를 동적으로 추가
        loadPurchasedStamps()

        return binding.root
    }

    private fun loadPurchasedStamps() {
        lifecycleScope.launch {
            val purchasedStamps = stampDao.getAllPurchasedStamps()

            // LinearLayout에 이미지 뷰들을 동적으로 추가합니다.
            val linearLayout = binding.memo2StampHsv.getChildAt(0) as LinearLayout // LinearLayout을 참조
            purchasedStamps.forEachIndexed { index, stamp ->
                val imageView = ImageView(requireContext())
                imageView.setImageResource(stamp.stampImg)

                imageView.layoutParams = LinearLayout.LayoutParams(150, 150).apply {
                    if (index != 0) marginStart = 20 // 여백 설정
                }

                // 클릭 이벤트 처리
                imageView.setOnClickListener {
                    // 기존에 선택된 이미지가 있다면, 배경을 투명으로 되돌림
                    selectedItemId?.let { id ->
                        val prevSelectedImage = linearLayout.getChildAt(id - 1) as ImageView
                        prevSelectedImage.setBackgroundColor(android.graphics.Color.TRANSPARENT) // 배경을 투명으로
                    }

                    // 새로 클릭된 이미지의 ID를 저장하고, 배경을 회색으로 설정
                    selectedItemId = stamp.id.toInt() // Stamp의 ID를 사용
                    imageView.setBackgroundColor(android.graphics.Color.LTGRAY) // 배경을 회색으로 설정

                    // 선택된 스탬프 id 저장
                    AppData.diarySelectedItem = selectedItemId ?: 0
                }

                // 만약 현재 이미지가 선택된 스탬프라면 배경을 회색으로 설정
                if (stamp.id.toInt() == AppData.diarySelectedItem) {
                    imageView.setBackgroundColor(android.graphics.Color.LTGRAY)
                    selectedItemId = stamp.id.toInt()
                }

                linearLayout.addView(imageView)
            }
        }
    }
}