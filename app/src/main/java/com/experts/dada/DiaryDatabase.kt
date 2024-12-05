package com.experts.dada

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 스키마를 변경할 때 마다 version을 올려야함 주의!
@Database(entities = [Diary::class], version = 3, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        // 싱글턴 패턴을 사용하여 데이터베이스 인스턴스를 생성
        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                )
                    .fallbackToDestructiveMigration() // 데이터베이스 구조 변경 시 기존 데이터를 삭제하고 새로 생성
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}