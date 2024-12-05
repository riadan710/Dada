package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.experts.dada.databinding.FragmentSettingStampBinding
import kotlinx.coroutines.launch

class SettingStampFragment : Fragment() {
    lateinit var binding: FragmentSettingStampBinding
    private lateinit var adapter: StoreAdapter
    private lateinit var stampDao: StampDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingStampBinding.inflate(inflater, container, false)

        adapter = StoreAdapter(requireContext())
        binding.stampContentGv.adapter = adapter

        // StampDao 초기화
        val diaryDatabase = DiaryDatabase.getDatabase(requireContext())
        stampDao = diaryDatabase.stampDao()

        // 구매한 스탬프 목록 불러오기
        loadPurchasedStamps()

        binding.stampCloseIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun loadPurchasedStamps() {
        lifecycleScope.launch {
            val purchasedStamps = stampDao.getAllPurchasedStamps()
            adapter.setStamps(purchasedStamps)
        }
    }
}