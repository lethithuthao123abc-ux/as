package com.example.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.BookmarkItem
import com.example.data.db.PatientRecord
import com.example.data.db.StrokeDatabase
import com.example.data.model.Abcd2Criteria
import com.example.data.model.Cha2ds2VascCriteria
import com.example.data.model.FlowchartDatabase
import com.example.data.model.FlowchartNode
import com.example.data.model.GuidelineCategory
import com.example.data.model.HasBledCriteria
import com.example.data.model.NihssData
import com.example.data.model.StrokeDrugDatabase
import com.example.data.model.StrokeGuideline
import com.example.data.repository.StrokeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StrokeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = StrokeDatabase.getDatabase(application)
    private val repository = StrokeRepository(db.strokeDao())

    // Search and Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<GuidelineCategory?>(null)
    val selectedCategory: StateFlow<GuidelineCategory?> = _selectedCategory.asStateFlow()

    private val _showOnlyBookmarks = MutableStateFlow(false)
    val showOnlyBookmarks: StateFlow<Boolean> = _showOnlyBookmarks.asStateFlow()

    // Bookmarks Flow
    val bookmarkedGuidelines: StateFlow<List<BookmarkItem>> = repository.bookmarkedGuidelines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Guidelines Flow
    val filteredGuidelines: StateFlow<List<StrokeGuideline>> = combine(
        _searchQuery,
        _selectedCategory,
        _showOnlyBookmarks,
        bookmarkedGuidelines
    ) { query, category, onlyBookmarks, bookmarks ->
        val all = repository.getGuidelines()
        all.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.summary.contains(query, ignoreCase = true) ||
                    item.keyPoints.any { it.contains(query, ignoreCase = true) }
            val matchesCategory = category == null || item.category == category
            val matchesBookmark = !onlyBookmarks || bookmarks.any { it.guidelineId == item.id }

            matchesQuery && matchesCategory && matchesBookmark
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getGuidelines())

    // NIHSS Calculator State (map item index 1..15 to chosen option score)
    private val _nihssScores = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val nihssScores: StateFlow<Map<Int, Int>> = _nihssScores.asStateFlow()

    fun updateNihssScore(itemId: Int, score: Int) {
        val updated = _nihssScores.value.toMutableMap()
        updated[itemId] = score
        _nihssScores.value = updated
    }

    fun resetNihss() {
        _nihssScores.value = emptyMap()
    }

    fun getNihssTotalScore(): Int = _nihssScores.value.values.sum()

    fun getNihssSeverity(): Pair<String, String> = NihssData.getSeverity(getNihssTotalScore())

    // ABCD2 Criteria State
    private val _abcd2Criteria = MutableStateFlow(Abcd2Criteria())
    val abcd2Criteria: StateFlow<Abcd2Criteria> = _abcd2Criteria.asStateFlow()

    fun updateAbcd2Criteria(transform: (Abcd2Criteria) -> Abcd2Criteria) {
        _abcd2Criteria.value = transform(_abcd2Criteria.value)
    }

    // CHA2DS2-VASc State
    private val _cha2ds2VascCriteria = MutableStateFlow(Cha2ds2VascCriteria())
    val cha2ds2VascCriteria: StateFlow<Cha2ds2VascCriteria> = _cha2ds2VascCriteria.asStateFlow()

    fun updateCha2ds2VascCriteria(transform: (Cha2ds2VascCriteria) -> Cha2ds2VascCriteria) {
        _cha2ds2VascCriteria.value = transform(_cha2ds2VascCriteria.value)
    }

    // HAS-BLED State
    private val _hasBledCriteria = MutableStateFlow(HasBledCriteria())
    val hasBledCriteria: StateFlow<HasBledCriteria> = _hasBledCriteria.asStateFlow()

    fun updateHasBledCriteria(transform: (HasBledCriteria) -> HasBledCriteria) {
        _hasBledCriteria.value = transform(_hasBledCriteria.value)
    }

    // Interactive Flowchart State
    private val _currentFlowchartNodeId = MutableStateFlow("start")
    val currentFlowchartNodeId: StateFlow<String> = _currentFlowchartNodeId.asStateFlow()

    val currentFlowchartNode: FlowchartNode
        get() = FlowchartDatabase.ischemicFlowchart.nodes[_currentFlowchartNodeId.value]
            ?: FlowchartDatabase.ischemicFlowchart.nodes["start"]!!

    fun navigateFlowchartNode(nodeId: String) {
        _currentFlowchartNodeId.value = nodeId
    }

    fun resetFlowchartNode() {
        _currentFlowchartNodeId.value = "start"
    }

    // Patient Records Flow
    val allPatientRecords: StateFlow<List<PatientRecord>> = repository.allPatientRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveCurrentAssessmentToRecord(
        patientAlias: String,
        age: Int,
        gender: String,
        onsetTime: String,
        sbp: Int,
        dbp: Int,
        isTpa: Boolean,
        isEvt: Boolean,
        notes: String
    ) {
        val nihss = getNihssTotalScore()
        val (severity, advice) = getNihssSeverity()
        val abcd2 = _abcd2Criteria.value.calculateScore()
        val cha2ds2 = _cha2ds2VascCriteria.value.calculateScore()

        val summaryText = "NIHSS: $nihss ($severity). ABCD2: $abcd2. CHA2DS2-VASc: $cha2ds2. HA: $sbp/$dbp mmHg. Onset: $onsetTime. TSH: ${if (isTpa) "Có" else "Không"}, EVT: ${if (isEvt) "Có" else "Không"}. Khuyên dùng: $advice"

        val record = PatientRecord(
            patientNameOrAlias = if (patientAlias.isBlank()) "Bệnh nhân - ${System.currentTimeMillis() % 10000}" else patientAlias,
            age = age,
            gender = gender,
            onsetTimeString = onsetTime,
            nihssScore = nihss,
            nihssSeverity = severity,
            abcd2Score = abcd2,
            cha2ds2VascScore = cha2ds2,
            systolicBp = sbp,
            diastolicBp = dbp,
            isTpaCandidate = isTpa,
            isEvtCandidate = isEvt,
            clinicalSummaryAndAction = summaryText,
            physicianNotes = notes
        )

        viewModelScope.launch {
            repository.savePatientRecord(record)
        }
    }

    fun deletePatientRecord(id: Long) {
        viewModelScope.launch {
            repository.deletePatientRecord(id)
        }
    }

    fun toggleBookmark(guideline: StrokeGuideline, isBookmarked: Boolean) {
        viewModelScope.launch {
            repository.toggleBookmark(guideline, isBookmarked)
        }
    }

    fun isBookmarked(guidelineId: String): Flow<Boolean> = repository.isBookmarked(guidelineId)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: GuidelineCategory?) {
        _selectedCategory.value = category
    }

    fun toggleShowOnlyBookmarks() {
        _showOnlyBookmarks.value = !_showOnlyBookmarks.value
    }

    // Drug dataset
    val drugList = StrokeDrugDatabase.drugs
}
