package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PatientRecord::class, BookmarkItem::class],
    version = 1,
    exportSchema = false
)
abstract class StrokeDatabase : RoomDatabase() {
    abstract fun strokeDao(): StrokeDao

    companion object {
        @Volatile
        private var INSTANCE: StrokeDatabase? = null

        fun getDatabase(context: Context): StrokeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StrokeDatabase::class.java,
                    "stroke_guidelines_2026.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
