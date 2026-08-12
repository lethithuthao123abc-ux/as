package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_records")
data class PatientRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val patientNameOrAlias: String,
    val age: Int,
    val gender: String,
    val timestamp: Long = System.currentTimeMillis(),
    val onsetTimeString: String,
    val nihssScore: Int,
    val nihssSeverity: String,
    val abcd2Score: Int,
    val cha2ds2VascScore: Int,
    val systolicBp: Int,
    val diastolicBp: Int,
    val isTpaCandidate: Boolean,
    val isEvtCandidate: Boolean,
    val clinicalSummaryAndAction: String,
    val physicianNotes: String = ""
)

@Entity(tableName = "bookmarked_guidelines")
data class BookmarkItem(
    @PrimaryKey val guidelineId: String,
    val title: String,
    val categoryName: String,
    val summary: String,
    val timestamp: Long = System.currentTimeMillis()
)
