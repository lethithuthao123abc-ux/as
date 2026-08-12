package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NihssData
import com.example.data.viewmodel.StrokeViewModel
import com.example.ui.theme.MedicalEmergencyRed
import com.example.ui.theme.MedicalSuccessGreen
import com.example.ui.theme.MedicalWarningAmber

@Composable
fun CalculatorsScreen(
    viewModel: StrokeViewModel,
    onNavigateToRecords: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Thang điểm NIHSS", "Thang điểm ABCD2", "CHA₂DS₂-VASc", "HAS-BLED", "Thang điểm mRS")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
            }
        }

        when (selectedTab) {
            0 -> NihssCalculatorView(viewModel, onNavigateToRecords)
            1 -> Abcd2CalculatorView(viewModel)
            2 -> Cha2ds2VascCalculatorView(viewModel)
            3 -> HasBledCalculatorView(viewModel)
            4 -> MrsCalculatorView()
        }
    }
}

@Composable
fun NihssCalculatorView(
    viewModel: StrokeViewModel,
    onSaveSuccess: () -> Unit
) {
    val nihssScores by viewModel.nihssScores.collectAsState()
    val totalScore = viewModel.getNihssTotalScore()
    val (severity, advice) = viewModel.getNihssSeverity()
    val scrollState = rememberScrollState()

    var showSaveDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Sticky/Top Score Result Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = when {
                    totalScore >= 16 -> MedicalEmergencyRed
                    totalScore >= 5 -> MaterialTheme.colorScheme.primary
                    else -> MedicalSuccessGreen
                }
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TỔNG ĐIỂM NIHSS",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$totalScore / 42 Điểm",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        IconButton(onClick = { viewModel.resetNihss() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White)
                        }
                        Button(
                            onClick = { showSaveDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Lưu Bệnh Nhân", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Mức độ: $severity",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = advice,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }

        // NIHSS Items List
        NihssData.items.forEach { item ->
            val selectedOptionScore = nihssScores[item.id] ?: 0

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = item.description,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    item.options.forEach { opt ->
                        val isSelected = selectedOptionScore == opt.score
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .clickable { viewModel.updateNihssScore(item.id, opt.score) },
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = opt.label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSaveDialog) {
        SavePatientAssessmentModal(
            viewModel = viewModel,
            onDismiss = { showSaveDialog = false },
            onSaved = {
                showSaveDialog = false
                onSaveSuccess()
            }
        )
    }
}

@Composable
fun SavePatientAssessmentModal(
    viewModel: StrokeViewModel,
    onDismiss: () -> Unit,
    onSaved: () -> Unit
) {
    var patientAlias by remember { mutableStateOf("") }
    var ageStr by remember { mutableStateOf("65") }
    var gender by remember { mutableStateOf("Nam") }
    var onsetTime by remember { mutableStateOf("2 giờ trước") }
    var sbpStr by remember { mutableStateOf("160") }
    var dbpStr by remember { mutableStateOf("95") }
    var isTpa by remember { mutableStateOf(true) }
    var isEvt by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Lưu Đánh Giá Bệnh Nhân Khoa Cấp Cứu", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = patientAlias,
                    onValueChange = { patientAlias = it },
                    label = { Text("Tên/Mã Bệnh Nhân") },
                    placeholder = { Text("VD: BN Nguyễn Văn A / BN-1042") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = ageStr,
                        onValueChange = { ageStr = it },
                        label = { Text("Tuổi") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("Giới tính") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = onsetTime,
                    onValueChange = { onsetTime = it },
                    label = { Text("Thời điểm khởi phát (Onset time)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sbpStr,
                        onValueChange = { sbpStr = it },
                        label = { Text("HA Tâm Thu (SBP)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = dbpStr,
                        onValueChange = { dbpStr = it },
                        label = { Text("HA Tâm Trương (DBP)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isTpa, onCheckedChange = { isTpa = it })
                    Text("Đủ tiêu chuẩn Tiêu sợi huyết (TNK/rtPA)", fontSize = 12.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isEvt, onCheckedChange = { isEvt = it })
                    Text("Đủ tiêu chuẩn Lấy huyết khối EVT", fontSize = 12.sp)
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Ghi chú của bác sĩ") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.saveCurrentAssessmentToRecord(
                        patientAlias = patientAlias,
                        age = ageStr.toIntOrNull() ?: 65,
                        gender = gender,
                        onsetTime = onsetTime,
                        sbp = sbpStr.toIntOrNull() ?: 160,
                        dbp = dbpStr.toIntOrNull() ?: 95,
                        isTpa = isTpa,
                        isEvt = isEvt,
                        notes = notes
                    )
                    onSaved()
                }
            ) {
                Text("Lưu Hồ Sơ")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Text("Hủy", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    )
}

@Composable
fun Abcd2CalculatorView(viewModel: StrokeViewModel) {
    val criteria by viewModel.abcd2Criteria.collectAsState()
    val score = criteria.calculateScore()
    val (category, guideline) = criteria.getRiskCategory()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (score >= 4) MedicalEmergencyRed else MaterialTheme.colorScheme.primary
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("THANG ĐIỂM ABCD2 (DỰ ĐOÁN NGUY CƠ ĐỘT QUỤ SAU TIA)", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("$score / 7 Điểm", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(category, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(guideline, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp, lineHeight = 17.sp)
            }
        }

        Abcd2CheckboxItem("A - Age (Tuổi >= 60)", "Bệnh nhân từ 60 tuổi trở lên (+1 điểm)", criteria.ageGt60) {
            viewModel.updateAbcd2Criteria { c -> c.copy(ageGt60 = it) }
        }

        Abcd2CheckboxItem("B - Blood Pressure (Huyết áp cao)", "Huyết áp tâm thu >= 140 mmHg hoặc tâm trương >= 90 mmHg lúc khám (+1 điểm)", criteria.bpHigh) {
            viewModel.updateAbcd2Criteria { c -> c.copy(bpHigh = it) }
        }

        Abcd2CheckboxItem("C - Clinical: Yếu nửa người một bên", "Có triệu chứng yếu nửa người rõ (+2 điểm)", criteria.clinicalUnilateralWeakness) {
            viewModel.updateAbcd2Criteria { c ->
                c.copy(
                    clinicalUnilateralWeakness = it,
                    clinicalSpeechOnly = if (it) false else c.clinicalSpeechOnly
                )
            }
        }

        Abcd2CheckboxItem("C - Clinical: Rối loạn ngôn ngữ đơn thuần", "Chỉ nói khó, không có yếu tay chân (+1 điểm)", criteria.clinicalSpeechOnly) {
            viewModel.updateAbcd2Criteria { c ->
                c.copy(
                    clinicalSpeechOnly = it,
                    clinicalUnilateralWeakness = if (it) false else c.clinicalUnilateralWeakness
                )
            }
        }

        Abcd2CheckboxItem("D - Duration: Thời gian TIA >= 60 phút", "Thời gian kéo dài cơn TIA từ 60 phút trở lên (+2 điểm)", criteria.durationGt60min) {
            viewModel.updateAbcd2Criteria { c ->
                c.copy(
                    durationGt60min = it,
                    duration10to59min = if (it) false else c.duration10to59min
                )
            }
        }

        Abcd2CheckboxItem("D - Duration: Thời gian TIA 10 - 59 phút", "Kéo dài từ 10 đến 59 phút (+1 điểm)", criteria.duration10to59min) {
            viewModel.updateAbcd2Criteria { c ->
                c.copy(
                    duration10to59min = it,
                    durationGt60min = if (it) false else c.durationGt60min
                )
            }
        }

        Abcd2CheckboxItem("D - Diabetes (Đái tháo đường)", "Bệnh nhân có tiền sử hoặc đang điều trị Đái tháo đường (+1 điểm)", criteria.diabetes) {
            viewModel.updateAbcd2Criteria { c -> c.copy(diabetes = it) }
        }
    }
}

@Composable
fun Abcd2CheckboxItem(title: String, desc: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCheckedChange(!checked) }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checked, onCheckedChange = onCheckedChange)
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun Cha2ds2VascCalculatorView(viewModel: StrokeViewModel) {
    val criteria by viewModel.cha2ds2VascCriteria.collectAsState()
    val score = criteria.calculateScore()
    val (category, guideline) = criteria.getAnticoagulationGuideline()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("THANG ĐIỂM CHA₂DS₂-VASc (NGUY CƠ TẮC MẠCH RUNG NHĨ)", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("$score Điểm", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(category, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(guideline, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp, lineHeight = 17.sp)
            }
        }

        Abcd2CheckboxItem("C - Congestive Heart Failure", "Suy tim sung huyết hoặc LVEF <= 40% (+1 điểm)", criteria.congestiveHeartFailure) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(congestiveHeartFailure = it) }
        }
        Abcd2CheckboxItem("H - Hypertension", "Tăng huyết áp (+1 điểm)", criteria.hypertension) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(hypertension = it) }
        }
        Abcd2CheckboxItem("A2 - Age >= 75 years", "Tuổi từ 75 trở lên (+2 điểm)", criteria.age75OrOlder) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(age75OrOlder = it, age65To74 = if (it) false else c.age65To74) }
        }
        Abcd2CheckboxItem("D - Diabetes Mellitus", "Đái tháo đường (+1 điểm)", criteria.diabetes) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(diabetes = it) }
        }
        Abcd2CheckboxItem("S2 - Stroke / TIA / Thromboembolism", "Tiền sử Đột quỵ, TIA hoặc Tắc mạch hệ thống (+2 điểm)", criteria.strokeTiaThromboembolism) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(strokeTiaThromboembolism = it) }
        }
        Abcd2CheckboxItem("V - Vascular Disease", "Bệnh mạch máu (Nhồi máu cơ tim, Bệnh ĐM ngoại biên, Mảng xơ vữa ĐM chủ) (+1 điểm)", criteria.vascularDisease) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(vascularDisease = it) }
        }
        Abcd2CheckboxItem("A - Age 65 - 74 years", "Tuổi từ 65 đến 74 (+1 điểm)", criteria.age65To74) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(age65To74 = it, age75OrOlder = if (it) false else c.age75OrOlder) }
        }
        Abcd2CheckboxItem("Sc - Sex Category (Female)", "Giới tính Nữ (+1 điểm)", criteria.femaleGender) {
            viewModel.updateCha2ds2VascCriteria { c -> c.copy(femaleGender = it) }
        }
    }
}

@Composable
fun HasBledCalculatorView(viewModel: StrokeViewModel) {
    val criteria by viewModel.hasBledCriteria.collectAsState()
    val score = criteria.calculateScore()
    val text = criteria.getInterpretation()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = if (score >= 3) MedicalWarningAmber else MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("THANG ĐIỂM HAS-BLED (ĐÁNH GIÁ NGUY CƠ XUẤT HUYẾT)", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("$score / 9 Điểm", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text, color = Color.White, fontSize = 12.sp, lineHeight = 17.sp)
            }
        }

        Abcd2CheckboxItem("H - Hypertension (Chưa kiểm soát)", "HA tâm thu > 160 mmHg (+1 điểm)", criteria.hypertensionUncontrolled) {
            viewModel.updateHasBledCriteria { c -> c.copy(hypertensionUncontrolled = it) }
        }
        Abcd2CheckboxItem("S - Stroke History", "Tiền sử Đột quỵ (+1 điểm)", criteria.strokeHistory) {
            viewModel.updateHasBledCriteria { c -> c.copy(strokeHistory = it) }
        }
        Abcd2CheckboxItem("B - Bleeding Tendency", "Tiền sử Xuất huyết hoặc Tạng xuất huyết (+1 điểm)", criteria.bleedingTendency) {
            viewModel.updateHasBledCriteria { c -> c.copy(bleedingTendency = it) }
        }
        Abcd2CheckboxItem("L - Labile INR", "INR thất thường, khó kiểm soát (+1 điểm)", criteria.labileINR) {
            viewModel.updateHasBledCriteria { c -> c.copy(labileINR = it) }
        }
        Abcd2CheckboxItem("E - Elderly (> 65)", "Tuổi > 65 (+1 điểm)", criteria.elderlyAgeGt65) {
            viewModel.updateHasBledCriteria { c -> c.copy(elderlyAgeGt65 = it) }
        }
    }
}

@Composable
fun MrsCalculatorView() {
    val items = listOf(
        "0 - Không có triệu chứng nào" to "Bình thường hoàn toàn.",
        "1 - Không có tàn phế đáng kể" to "Thực hiện được tất cả các công việc và sinh hoạt bình thường dù có ít triệu chứng.",
        "2 - Tàn phế nhẹ" to "Không thực hiện được tất cả các hoạt động trước đây nhưng tự chăm sóc bản thân không cần giúp đỡ.",
        "3 - Tàn phế trung bình" to "Cần giúp đỡ một số việc nhưng tự đi lại được không cần hỗ trợ.",
        "4 - Tàn phế tương đối nặng" to "Không tự đi lại được và không tự chăm sóc sinh hoạt bản thân nếu không có giúp đỡ.",
        "5 - Tàn phế rất nặng" to "Nằm giường hoàn toàn, đại tiểu tiện không tự chủ, cần chăm sóc liên tục.",
        "6 - Tử vong" to "Bệnh nhân tử vong."
    )

    var selectedScore by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("THANG ĐIỂM mRS (MODIFIED RANKIN SCALE)", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("mRS $selectedScore", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(items[selectedScore].first, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(items[selectedScore].second, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
            }
        }

        items.forEachIndexed { idx, (title, desc) ->
            val isSel = selectedScore == idx
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedScore = idx },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    if (isSel) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
