package com.example.data.model

enum class GuidelineCategory(val displayName: String, val iconName: String) {
    EMERGENCY("Cấp cứu Khẩn cấp (BE-FAST & Golden Hour)", "Emergency"),
    ISCHEMIC("Đột quỵ Thiếu máu não cấp (Ischemic)", "Brain"),
    THROMBOLYSIS_EVT("Tái thông Mạch máu (TNK/Alteplase & EVT)", "Flash"),
    HEMORRHAGIC("Xuất huyết Não (ICH & SAH)", "Blood"),
    SECONDARY_PREVENTION("Dự phòng Thứ phát 2026 (DAPT, Lipid, HA)", "Shield"),
    CARDIAC_AF("Rung nhĩ & Đột quỵ do tim (AF & NOACs)", "Heart"),
    REHABILITATION("Phục hồi Chức năng & Chăm sóc", "Activity")
}

enum class EvidenceClass(val tag: String, val description: String) {
    CLASS_1("Class I", "Khuyến cáo mạnh (Khuyên dùng/Có chỉ định) - Lợi ích >>> Nguy cơ"),
    CLASS_2A("Class IIa", "Khuyến cáo trung bình (Hợp lý/Nên xem xét) - Lợi ích >> Nguy cơ"),
    CLASS_2B("Class IIb", "Khuyến cáo yếu (Có thể cân nhắc) - Lợi ích >= Nguy cơ"),
    CLASS_3_HARM("Class III (Harm)", "Chống chỉ định / Có hại - Nguy cơ > Lợi ích"),
    CLASS_3_NO_BENEFIT("Class III (No Benefit)", "Không có lợi ích - Không khuyến cáo")
}

enum class EvidenceLevel(val tag: String, val label: String) {
    LEVEL_A("Level A", "Bằng chứng chất lượng cao từ nhiều thử nghiệm lâm sàng ngẫu nhiên (RCT)"),
    LEVEL_B_R("Level B-R", "Bằng chứng chất lượng trung bình từ các thử nghiệm RCT"),
    LEVEL_B_NR("Level B-NR", "Bằng chứng từ các nghiên cứu phi ngẫu nhiên"),
    LEVEL_C_LD("Level C-LD", "Dữ liệu giới hạn (Observational/Registry)"),
    LEVEL_C_EO("Level C-EO", "Ý kiến chuyên gia (Expert Opinion)")
}

data class StrokeGuideline(
    val id: String,
    val title: String,
    val category: GuidelineCategory,
    val summary: String,
    val evidenceClass: EvidenceClass,
    val evidenceLevel: EvidenceLevel,
    val keyPoints: List<String>,
    val practicalTip: String,
    val sourceYear: String = "2026 AHA/ASA & ESO Update",
    val isKeyHeadline: Boolean = false
)
