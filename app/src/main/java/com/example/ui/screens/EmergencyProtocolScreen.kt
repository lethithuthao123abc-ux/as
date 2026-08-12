package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.viewmodel.StrokeViewModel
import com.example.ui.components.HeaderBannerCard
import com.example.ui.theme.MedicalEmergencyRed
import com.example.ui.theme.MedicalSuccessGreen
import com.example.ui.theme.MedicalWarningAmber

@Composable
fun EmergencyProtocolScreen(
    viewModel: StrokeViewModel,
    onNavigateToFlowchart: () -> Unit,
    onNavigateToNihss: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBannerCard(
            title = "Cấp Cứu Đột Quỵ Khẩn 2026",
            subtitle = "Quy trình giờ vàng (Golden Hour), BE-FAST, Tiêu Sợi Huyết TNK & Can Thiệp Mạch EVT"
        )

        // BE-FAST Fast Triage Card
        BeFastCard()

        // Quick Action Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onNavigateToNihss,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.LocalHospital, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tính NIHSS Khẩn", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
                onClick = onNavigateToFlowchart,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MedicalEmergencyRed
                )
            ) {
                Icon(Icons.Default.FlashOn, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Lưu Đồ Cấp Cứu", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        // Golden Hour Timeline Visualizer
        GoldenHourTimelineCard()

        // Thrombolysis (TNK/Alteplase) Eligibility Criteria
        ThrombolysisEligibilityCard()

        // Blood Pressure Target Card
        BloodPressureTargetsCard()
    }
}

@Composable
fun BeFastCard() {
    var bChecked by remember { mutableStateOf(false) }
    var eChecked by remember { mutableStateOf(false) }
    var fChecked by remember { mutableStateOf(false) }
    var aChecked by remember { mutableStateOf(false) }
    var sChecked by remember { mutableStateOf(false) }

    val anyPositive = bChecked || eChecked || fChecked || aChecked || sChecked

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tầm Soát Nhanh Dấu Hiệu BE-FAST 2026",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    color = MedicalEmergencyRed.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "CODE STROKE",
                        color = MedicalEmergencyRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BeFastItem("B - Balance (Mất thăng bằng)", "Đột ngột mất thăng bằng, đi loạng thoạng, chóng mặt dữ dội.", bChecked) { bChecked = it }
            BeFastItem("E - Eyes (Mắt / Thị giác)", "Mất thị lực đột ngột 1 hoặc 2 mắt, nhìn đôi (song thị).", eChecked) { eChecked = it }
            BeFastItem("F - Face (Mặt / Méo miệng)", "Mặt bị lệch, méo miệng khi cười hoặc nhe răng.", fChecked) { fChecked = it }
            BeFastItem("A - Arms (Tay / Yếu nửa người)", "Một bên tay hoặc chân bị yếu, rơi xuống khi giơ lên.", aChecked) { aChecked = it }
            BeFastItem("S - Speech (Ngôn ngữ)", "Nói ngọng, nói khó, dùng từ sai hoặc không hiểu người khác nói.", sChecked) { sChecked = it }

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(visible = anyPositive) {
                Surface(
                    color = MedicalEmergencyRed,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "NGHI NGỜ ĐỘT QUỤ CẤP!",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Kích hoạt Code Stroke ngay! Chụp CT Sọ Không Tiêm + Đường huyết mao mạch + Chuẩn bị TSH/EVT.",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BeFastItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (checked) MedicalEmergencyRed else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun GoldenHourTimelineCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Mục Tiêu Giờ Vàng (Golden Hour Timeline 2026)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            TimelineItem("0 - 10 phút", "Tiếp nhận cấp cứu, đo Sinh hiệu, Đường huyết mao mạch, Khám BE-FAST/NIHSS.")
            TimelineItem("10 - 25 phút", "Chụp CT sọ không tiêm + CTA mạch máu não khẩn.")
            TimelineItem("< 30 - 45 phút", "DOOR-TO-NEEDLE: Tiêm Tiêu Sợi Huyết TNK (Tenecteplase) 0.25mg/kg hoặc Alteplase.")
            TimelineItem("< 60 phút", "DOOR-TO-PUNCTURE: Chọc kim đường đùi/quay vào phòng DSA lấy huyết khối EVT nếu có LVO.")
            TimelineItem("0 - 4.5 giờ", "Cửa sổ tiêu sợi huyết tĩnh mạch chuẩn (TNK/Alteplase).")
            TimelineItem("6 - 24 giờ", "Cửa sổ mở rộng lấy huyết khối EVT (dựa trên CTP/MRI Penumbra).")
        }
    }
}

@Composable
fun TimelineItem(timeLabel: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(90.dp)
        ) {
            Text(
                text = timeLabel,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 17.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ThrombolysisEligibilityCard() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tiêu Chuẩn Tiêu Sợi Huyết (TNK/Alteplase)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Chỉ định & Chống chỉ định quan trọng",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "CHỈ ĐỊNH 2026:",
                        fontWeight = FontWeight.Bold,
                        color = MedicalSuccessGreen,
                        fontSize = 12.sp
                    )
                    Text("• Chẩn đoán Đột quỵ thiếu máu cục bộ gây khuyết tật chức năng (Disabling deficit).", fontSize = 12.sp)
                    Text("• Khởi phát triệu chứng < 4.5 giờ kể từ thời điểm LKW (Last Known Well).", fontSize = 12.sp)
                    Text("• Bệnh nhân >= 18 tuổi.", fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "CHỐNG CHỈ ĐỊNH TUYỆT ĐỐI:",
                        fontWeight = FontWeight.Bold,
                        color = MedicalEmergencyRed,
                        fontSize = 12.sp
                    )
                    Text("• CT sọ cho thấy Xuất huyết nội sọ (ICH) hoặc nhồi máu diện quá rộng (ASPECT < 3-5).", fontSize = 12.sp)
                    Text("• Huyết áp SBP > 185 mmHg hoặc DBP > 110 mmHg chưa được hạ bằng thuốc.", fontSize = 12.sp)
                    Text("• Đường huyết < 2.8 mmol/L (50 mg/dL) hoặc > 22.2 mmol/L.", fontSize = 12.sp)
                    Text("• Số lượng tiểu cầu < 100,000 / mm3.", fontSize = 12.sp)
                    Text("• Đang dùng thuốc chống đông đường uống mới NOAC trong vòng 48h.", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BloodPressureTargetsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Mục Tiêu Kiểm Soát Huyết Áp 2026",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            BpTargetItem(
                label = "Trước khi dùng Tiêu Sợi Huyết (TSH)",
                target = "< 185 / 110 mmHg",
                desc = "Bắt buộc hạ HA xuống dưới ngưỡng này bằng Nicardipine IV hoặc Labetalol IV trước khi tiêm TNK/Alteplase."
            )

            BpTargetItem(
                label = "24h đầu Sau Tiêu Sợi Huyết",
                target = "< 180 / 105 mmHg",
                desc = "Theo dõi HA mỗi 15 phút trong 2 giờ đầu, mỗi 30 phút trong 6 giờ tiếp theo."
            )

            BpTargetItem(
                label = "Xuất Huyết Brain (ICH Cấp)",
                target = "SBP 130 - 140 mmHg",
                desc = "Hạ HA nhanh chóng bằng Nicardipine IV để hạn chế khối máu tụ lan rộng."
            )

            BpTargetItem(
                label = "Không dùng TSH / Không EVT",
                target = "< 220 / 120 mmHg",
                desc = "Không hạ HA vội trừ khi > 220/120 mmHg để duy trì tưới máu não."
            )
        }
    }
}

@Composable
fun BpTargetItem(label: String, target: String, desc: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(target, fontWeight = FontWeight.Bold, color = MedicalEmergencyRed, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
