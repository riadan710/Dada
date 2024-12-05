package com.experts.dada

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diarys")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val content: String = "",
    val stampId: Int,
    val weight: String = "",
    val isStar: Boolean = false,
    val bodyImg: String = ""
)
