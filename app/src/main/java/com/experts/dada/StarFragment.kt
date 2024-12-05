package com.experts.dada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StarFragment : Fragment(), DiaryAdapter.OnItemClickListener {

    private lateinit var diaryDao: DiaryDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.star_rv)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        diaryDao = DiaryDatabase.getDatabase(requireContext()).diaryDao()

        // Load star diaries
        loadStarDiaries()
    }

    private fun loadStarDiaries() {
        lifecycleScope.launch {
            val starDiaries = withContext(Dispatchers.IO) {
                diaryDao.getStarDiary()
            }
            adapter = DiaryAdapter(starDiaries, this@StarFragment)
            recyclerView.adapter = adapter
        }
    }

    override fun onItemClick(diary: Diary) {
        val dateParts = diary.date.split("-")
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt()
        val dayOfMonth = dateParts[2].toInt()

        // 프래그먼트 전환 처리
        val diaryMemoFragment = DiaryMemoFragment().apply {
            arguments = Bundle().apply {
                putInt("year", year)
                putInt("month", month)
                putInt("dayOfMonth", dayOfMonth)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frm, diaryMemoFragment)
            .addToBackStack(null)
            .commit()
    }
}