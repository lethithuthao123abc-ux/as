package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StrokeDao {
    // Patient Records Queries
    @Query("SELECT * FROM patient_records ORDER BY timestamp DESC")
    fun getAllPatientRecords(): Flow<List<PatientRecord>>

    @Query("SELECT * FROM patient_records WHERE id = :recordId")
    suspend fun getPatientRecordById(recordId: Long): PatientRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatientRecord(record: PatientRecord): Long

    @Query("DELETE FROM patient_records WHERE id = :recordId")
    suspend fun deletePatientRecordById(recordId: Long)

    // Bookmark Queries
    @Query("SELECT * FROM bookmarked_guidelines ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkItem)

    @Query("DELETE FROM bookmarked_guidelines WHERE guidelineId = :guidelineId")
    suspend fun removeBookmark(guidelineId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_guidelines WHERE guidelineId = :guidelineId)")
    fun isBookmarked(guidelineId: String): Flow<Boolean>
}
