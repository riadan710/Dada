package com.experts.dada

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class StoreAdapter(private val context : Context) : BaseAdapter() {

    // 이미지 배열
    private val images = arrayOf(
        R.drawable.stamp1,
        R.drawable.stamp2,
        R.drawable.stamp3,
        R.drawable.stamp4,
        R.drawable.stamp5,
        R.drawable.stamp6,
        R.drawable.stamp7,
        R.drawable.stamp8,
        R.drawable.stamp9,
        R.drawable.stamp10,
        R.drawable.stamp11,
        R.drawable.stamp12,
        R.drawable.stamp13,
        R.drawable.stamp14,
        R.drawable.stamp15,
        R.drawable.stamp16,
        R.drawable.stamp17,
        R.drawable.stamp18,
        R.drawable.stamp19,
        R.drawable.stamp20,
        R.drawable.stamp21,
        R.drawable.stamp22,
        R.drawable.stamp23,
        R.drawable.stamp24
    )

    private val selectedItems = mutableListOf<Int>()

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

        imageView.setImageResource(images[position])

        // 클릭 이벤트 처리
        imageView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
                imageView.setBackgroundColor(Color.TRANSPARENT) // 선택 해제
            } else {
                selectedItems.add(position)
                imageView.setBackgroundColor(Color.LTGRAY) // 선택됨을 나타내기 위해 배경색 변경
            }
        }

        // 선택된 상태를 유지하기 위해 배경색 설정
        if (selectedItems.contains(position)) {
            imageView.setBackgroundColor(Color.LTGRAY)
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT)
        }

        return imageView
    }

    override fun getCount(): Int = images.size

    override fun getItem(position: Int): Any = images[position]

    override fun getItemId(position: Int): Long = 0

    // 선택된 아이템들의 ID를 반환하는 메서드
    fun getSelectedItems(): List<Int> {
        return selectedItems
    }

    // 선택된 아이템들을 모두 해제하는 메서드
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}