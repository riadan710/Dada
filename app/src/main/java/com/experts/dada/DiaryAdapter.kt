package com.experts.dada

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryAdapter(private val diaries: List<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainImageView: ImageView = itemView.findViewById(R.id.item_star_main_iv)
        val starImageView: ImageView = itemView.findViewById(R.id.item_star_star_iv)
        val dateTextView: TextView = itemView.findViewById(R.id.item_star_date_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_star, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = diaries[position]
        // Assuming `diary.bodyImg` is a drawable resource ID

        if(diary.bodyImg == 0)  holder.mainImageView.setImageResource(R.drawable.nophoto)
        else    holder.mainImageView.setImageResource(diary.bodyImg)

        //holder.mainImageView.setImageResource(R.drawable.eyebody_example)
        holder.dateTextView.text = diary.date
        // Here you can set the star icon visibility or any other properties if needed
        holder.starImageView.visibility = if (diary.isStar) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = diaries.size
}