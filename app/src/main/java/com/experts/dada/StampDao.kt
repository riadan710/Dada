package com.experts.dada

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StampDao {

    @Insert
    suspend fun insertStamp(stamp: Stamp)

    @Query("SELECT * FROM stamps WHERE isPurchase = 0")
    suspend fun getAllAvailableStamps(): List<Stamp>  // 구매하지 않은 스탬프들만 조회

    @Query("SELECT * FROM stamps WHERE isPurchase = 1")
    suspend fun getAllPurchasedStamps(): List<Stamp>  // 구매한 스탬프들만 조회

    @Query("SELECT * FROM stamps WHERE id = :stampId")
    suspend fun getStampById(stampId: Long): Stamp?  // id로 스탬프 조회

    @Query("UPDATE stamps SET isPurchase = 1 WHERE id = :stampId")
    suspend fun purchaseStamp(stampId: Long)  // 구매 시 구매 여부 업데이트
}