package com.experts.dada

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    // 다이어리 저장
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(diary: Diary)

    // 다이어리 수정
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(diary: Diary)

    // 다이어리 삭제
    @Delete
    suspend fun delete(diary: Diary)

    // 모든 다이어리 조회 (실사용 X)
    @Query("SELECT * FROM diarys")
    fun getAllDiary(): List<Diary>

    // 특정 날짜의 다이어리 조회
    @Query("SELECT * FROM diarys WHERE date = :date")
    fun getDiary(date: String): Flow<Diary?>

    // 즐겨찾기 된 다이어리 조회
    @Query("SELECT * FROM diarys WHERE isStar = 1 ORDER BY date DESC")
    fun getStarDiary(): List<Diary>

    // 특정 월의 다이어리 조회
    @Query("SELECT * FROM diarys WHERE substr(date, 1, 7) = :month")
    fun getDiaryByMonth(month: String): Flow<List<Diary>>  // 예: "2024-12" 형식

}