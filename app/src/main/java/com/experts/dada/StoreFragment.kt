package com.experts.dada

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.experts.dada.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {
    lateinit var binding: FragmentStoreBinding
    private lateinit var adapter: StoreAdapter

    private val sharedPrefFile = "pointsPreference"
    private val defaultPoints = 9000
    private var points: Int = defaultPoints

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)

        adapter = StoreAdapter(requireContext())
        binding.storeContentGv.adapter = adapter

        // SharedPreferences에서 포인트 불러오기
        val sharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        points = sharedPreferences.getInt("points", defaultPoints)

        binding.storeMypointTv.text = points.toString()

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
                Toast.makeText(requireContext(), "${totalCount}개 구매 완료! 남은 포인트: $points", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "포인트가 부족합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun handlePurchase(selectedItems: List<Int>) {
        // 선택된 아이템들의 ID를 처리하는 로직을 여기에 추가합니다.
        // 예를 들어, 선택된 아이템들을 로그에 출력합니다.
        println("Selected items: $selectedItems")
    }

    private fun savePoints(sharedPreferences: SharedPreferences) {
        with(sharedPreferences.edit()) {
            putInt("points", points)
            apply()
        }
    }
}