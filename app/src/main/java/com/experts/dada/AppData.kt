package com.experts.dada

import android.app.Application

class AppData : Application() {
    companion object {
        // 전역 변수 정의
        var diarySelectedItem = 0

        var diaryContent = ""

        var diaryWeight = ""
    }
}