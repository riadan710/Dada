package com.experts.dada

import android.app.Application

class AppData : Application() {
    companion object {
        // 전역 변수 정의
        var diarySelectedItem = 0

        var diaryContent = ""

        var diaryWeight = ""

        var diaryImage = ""


        // 초기값
        val defaultPoints = 3000
    }

    override fun onCreate() {
        super.onCreate()

        // StampSeeder 호출하여 초기 스탬프 삽입
        val stampSeeder = StampSeeder(this)
        stampSeeder.seedStamps()
    }
}