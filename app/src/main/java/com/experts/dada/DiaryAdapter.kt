package com.experts.dada

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DiaryAdapter(private val diaries: List<Diary>,private val itemClickListener: OnItemClickListener)
     : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(diary: Diary)
    }

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainImageView: ImageView = itemView.findViewById(R.id.item_star_main_iv)
        val dateTextView: TextView = itemView.findViewById(R.id.item_star_date_tv)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(diaries[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_star, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = diaries[position]

        if (diary.bodyImg.isEmpty()) {
            holder.mainImageView.setImageResource(R.drawable.nophoto)
        } else {
            Glide.with(holder.itemView.context)
                .load(diary.bodyImg)
                .into(holder.mainImageView)
        }

        holder.dateTextView.text = diary.date
    }

    override fun getItemCount() = diaries.size
}