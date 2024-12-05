package com.experts.dada

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class StoreAdapter(private val context : Context) : BaseAdapter() {

    private var stamps = listOf<Stamp>()
    private val selectedItems = mutableListOf<Stamp>()  // 선택된 스탬프 목록

    // 스탬프 목록을 업데이트하는 메서드
    fun setStamps(stamps: List<Stamp>) {
        this.stamps = stamps
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView : ImageView

        if(convertView == null) {
            imageView = ImageView(context)
            imageView.run {
                layoutParams = ViewGroup.LayoutParams(200, 200)
                scaleType = ImageView.ScaleType.FIT_CENTER
                setPadding(10,10,10,10)
            }
        }
        else {
            imageView = convertView as ImageView
        }

        // 스탬프 이미지 설정
        imageView.setImageResource(stamps[position].stampImg)

        // 클릭 시 선택 상태 처리
        imageView.setOnClickListener {
            val selectedStamp = stamps[position]

            if (selectedItems.contains(selectedStamp)) {
                selectedItems.remove(selectedStamp)  // 선택 해제
                imageView.setBackgroundColor(Color.TRANSPARENT)
            } else {
                selectedItems.add(selectedStamp)  // 선택
                imageView.setBackgroundColor(Color.LTGRAY)
            }
        }

        // 선택된 상태 표시
        if (selectedItems.contains(stamps[position])) {
            imageView.setBackgroundColor(Color.LTGRAY)
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT)
        }

        return imageView
    }

    override fun getCount(): Int = stamps.size

    override fun getItem(position: Int): Any = stamps[position]

    override fun getItemId(position: Int): Long = stamps[position].id

    // 선택된 스탬프들의 ID 반환
    fun getSelectedItems(): List<Stamp> {
        return selectedItems
    }

    // 선택 해제
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}