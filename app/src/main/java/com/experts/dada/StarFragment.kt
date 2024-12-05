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

class StarFragment : Fragment() {

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
            adapter = DiaryAdapter(starDiaries)
            recyclerView.adapter = adapter
        }
    }
}