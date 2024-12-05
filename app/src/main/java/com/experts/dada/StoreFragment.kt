package com.experts.dada

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.experts.dada.AppData.Companion.defaultPoints
import com.experts.dada.databinding.FragmentStoreBinding
import kotlinx.coroutines.launch

class StoreFragment : Fragment() {
    lateinit var binding: FragmentStoreBinding
    private lateinit var adapter: StoreAdapter
    private lateinit var stampDao: StampDao

    private val sharedPrefFile = "pointsPreference"
    private var points: Int = defaultPoints // 초기값

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)

        // adapter 설정
        adapter = StoreAdapter(requireContext())
        binding.storeContentGv.adapter = adapter

        // StampDao 초기화
        val diaryDatabase = DiaryDatabase.getDatabase(requireContext())
        stampDao = diaryDatabase.stampDao()

        // SharedPreferences에서 포인트 불러오기
        val sharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        points = sharedPreferences.getInt("points", defaultPoints)
        binding.storeMypointTv.text = points.toString()

        // 스탬프 목록 불러오기
        loadAvailableStamps()

        // 구매하기 버튼 클릭 이벤트 처리
        binding.storeBuyLl.setOnClickListener {
            val selectedItems = adapter.getSelectedItems()
            val totalCount = selectedItems.size
            val totalCost = totalCount * 200 // 선택된 이미지 하나당 200 포인트

            if(totalCount == 0) {
                Toast.makeText(requireContext(), "구매할 상품을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (points >= totalCost) {
                points -= totalCost
                handlePurchase(selectedItems)
                binding.storeMypointTv.text = points.toString()
                adapter.clearSelection() // 선택 해제
                savePoints(sharedPreferences)
                Toast.makeText(requireContext(), "${totalCount}개 구매 완료! 남은 포인트: $points", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loadAvailableStamps() {
        lifecycleScope.launch {
            val availableStamps = stampDao.getAllAvailableStamps()  // DB에서 구매되지 않은 스탬프들을 가져옴
            adapter.setStamps(availableStamps)  // StoreAdapter에 스탬프 목록 설정
        }
    }

    private fun handlePurchase(selectedItems: List<Stamp>) {
        // 선택된 아이템들의 구매 상태 업데이트
        lifecycleScope.launch {
            // 선택된 아이템들의 isPurchase를 true로 업데이트
            for (stamp in selectedItems) {
                stampDao.purchaseStamp(stamp.id)  // DB에서 구매 처리
            }
            // 구매 후 StoreAdapter에서 DB를 갱신하여 UI 반영
            loadAvailableStamps()  // DB에서 구매되지 않은 스탬프들을 새로 불러옴
        }
    }

    private fun savePoints(sharedPreferences: SharedPreferences) {
        with(sharedPreferences.edit()) {
            putInt("points", points)
            apply()
        }
    }
}