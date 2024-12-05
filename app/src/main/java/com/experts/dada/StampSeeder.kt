package com.experts.dada

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StampSeeder(private val context: Context) {

    private val stampDao: StampDao

    init {
        val diaryDatabase = DiaryDatabase.getDatabase(context)
        stampDao = diaryDatabase.stampDao()
    }

    // DB에 초기 스탬프 삽입
    fun seedStamps() {
        // CoroutineScope를 사용하여 비동기 작업 수행
        CoroutineScope(Dispatchers.IO).launch {
            val existingStamps = stampDao.getAllAvailableStamps()

            // 이미 존재하는 스탬프가 없으면, 초기 스탬프 삽입
            if (existingStamps.isEmpty()) {
                insertInitialStamps()
            }
        }
    }

    // 초기 스탬프 데이터를 삽입하는 함수
    private suspend fun insertInitialStamps() {
        // drawable 폴더에서 이미지 파일을 참조하여 스탬프 객체 생성
        val stamps = mutableListOf<Stamp>()

        for (i in 1..27) {
            val imageResourceName = "stamp$i"  // stamp1, stamp2, ..., stamp27
            val resourceId = context.resources.getIdentifier(imageResourceName, "drawable", context.packageName)

            if (resourceId != 0) {
                // Stamp 객체 생성
                val stamp = Stamp(
                    id = i.toLong(),
                    stampImg = resourceId,
                    isPurchase = false
                )
                stamps.add(stamp)
            }
        }

        // 스탬프 객체들 삽입
        for (stamp in stamps) {
            stampDao.insertStamp(stamp)
        }
    }
}
