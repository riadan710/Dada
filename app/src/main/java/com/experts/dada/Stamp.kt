package com.experts.dada

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stamps")
data class Stamp (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val stampImg: Int,
    val isPurchase: Boolean = false
)