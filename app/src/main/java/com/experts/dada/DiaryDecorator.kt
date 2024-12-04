package com.experts.dada

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DiaryDecorator(
    private val date: CalendarDay, private val drawable: Drawable)
    : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == date
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        view.addSpan(ForegroundColorSpan(Color.TRANSPARENT))    // 날짜 텍스트 숨기기
    }
}