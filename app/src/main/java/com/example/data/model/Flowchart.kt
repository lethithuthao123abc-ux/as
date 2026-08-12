package com.example.data.model

data class FlowchartNode(
    val id: String,
    val title: String,
    val stepNumber: Int,
    val description: String,
    val actionAlert: String = "",
    val isCriticalWarning: Boolean = false,
    val options: List<FlowchartOption> = emptyList()
)

data class FlowchartOption(
    val label: String,
    val targetNodeId: String,
    val isPrimary: Boolean = true
)

data class InteractiveFlowchart(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val nodes: Map<String, FlowchartNode>
)

object FlowchartDatabase {
    val ischemicFlowchart = InteractiveFlowchart(
        id = "flowchart_acute_ischemic",
        title = "Lưu Đồ Cấp Cứu Đột Quỵ Thiếu Máu Não Cấp 2026",
        category = "Cấp Cứu Bệnh Viện",
        description = "Lưu đồ quyết định xử trí trong cửa sổ giờ vàng (Door-to-Needle < 30 phút, Door-to-Puncture < 60 phút).",
        nodes = mapOf(
            "start" to FlowchartNode(
                id = "start",
                title = "Bước 1: Tiếp nhận Khẩn & BE-FAST",
                stepNumber = 1,
                description = "Bệnh nhân đột quỵ nghi ngờ (B: Thăng bằng, E: Mắt, F: Mặt, A: Tay, S: Ngôn ngữ, T: Thời gian). Xác định thời điểm LKW (Last Known Well - Lần cuối bình thường). Lấy SpO2, Đường huyết mao mạch khẩn.",
                actionAlert = "Loại trừ Tụt đường huyết (Đường huyết < 3.0 mmol/L) trước khi chẩn đoán Đột quỵ!",
                options = listOf(
                    FlowchartOption("Đường huyết OK -> Chụp CT Sọ Không Tiêm Khẩn", "step_ct_scan")
                )
            ),
            "step_ct_scan" to FlowchartNode(
                id = "step_ct_scan",
                title = "Bước 2: CT Sọ Não Không Tiêm & CTA Mạch Máu",
                stepNumber = 2,
                description = "Đánh giá loại trừ Xuất huyết não (ICH). Tính điểm ASPECT (0-10). Làm CTA từ quai động mạch chủ đến vòm sọ để tìm Tắc mạch lớn (LVO: ICA, MCA M1/M2, Basilar).",
                options = listOf(
                    FlowchartOption("CT Không Xuất Huyết & Onset < 4.5h", "step_tpa_decision"),
                    FlowchartOption("Có Xuất Huyết Não (ICH)", "step_ich_protocol"),
                    FlowchartOption("Onset 4.5h - 24h & Có nghi ngờ LVO", "step_evt_window")
                )
            ),
            "step_tpa_decision" to FlowchartNode(
                id = "step_tpa_decision",
                title = "Bước 3: Chỉ định Tiêu Sợi Huyết TSH (TNK/Alteplase)",
                stepNumber = 3,
                description = "Kiểm tra Chống chỉ định (HA < 185/110 mmHg, Tiểu cầu > 100k, Không dùng NOAC 48h). 2026 UPDATE: Ưu tiên TENECTEPLASE (TNK) 0.25mg/kg Bolus đơn độc nếu có LVO hoặc cần chuyển tuyến EVT.",
                actionAlert = "Hạ HA bằng Nicardipine IV nếu SBP > 185 hoặc DBP > 110 mmHg trước khi tiêm TNK/Alteplase!",
                options = listOf(
                    FlowchartOption("Đủ tiêu chuẩn TSH -> Tiêm TNK 0.25mg/kg ngay!", "step_tpa_given"),
                    FlowchartOption("Có chống chỉ định TSH hoặc Onset > 4.5h", "step_evt_window")
                )
            ),
            "step_tpa_given" to FlowchartNode(
                id = "step_tpa_given",
                title = "Bước 4: Theo Dõi & Kết Hợp EVT",
                stepNumber = 4,
                description = "Nếu CTA phát hiện Tắc mạch lớn LVO (ICA, MCA M1, Thân nền): Chuyển thẳng Phòng DSA Can Thiệp Mạch Máu (Door-to-Puncture < 60 phút). KHÔNG CHỜ xem TSH có hồi phục hay không!",
                options = listOf(
                    FlowchartOption("Có LVO -> Lấy Huyết Khối Cơ Học EVT", "step_evt_procedure"),
                    FlowchartOption("Không LVO -> Nhập Stroke Unit & Dùng Aspirin sau 24h", "step_stroke_unit")
                )
            ),
            "step_evt_window" to FlowchartNode(
                id = "step_evt_window",
                title = "Đột quỵ Cửa Sổ Mở Rộng (6h - 24h) hoặc Tắc Mạch Lớn",
                stepNumber = 5,
                description = "Chụp CTP (CT Perfusion) hoặc MRI DWI-FLAIR. Đánh giá Mismatch lõi nhồi máu / vùng tranh tối tranh sáng (Mismatched Core/Penumbra) theo tiêu chuẩn DAWN/DEFUSE-3.",
                options = listOf(
                    FlowchartOption("Có Mismatch -> Can thiệp Lấy huyết khối EVT khẩn", "step_evt_procedure"),
                    FlowchartOption("Lõi tổn thương quá rộng (No Mismatch) -> Kháng tiểu cầu + Điều trị nội khoa", "step_stroke_unit")
                )
            ),
            "step_evt_procedure" to FlowchartNode(
                id = "step_evt_procedure",
                title = "Lấy Huyết Khối Bằng Dụng Cụ Cơ Học (EVT / Thrombectomy)",
                stepNumber = 6,
                description = "Thực hiện can thiệp lấy huyết khối bằng stent retriever / catheter hút âm áp. Mục tiêu tái thông TICI 2b/3.",
                actionAlert = "Mục tiêu HA trong và sau EVT: SBP 120 - 140 mmHg nếu tái thông hoàn toàn!",
                options = listOf(
                    FlowchartOption("Hoàn tất can thiệp -> Theo dõi sát tại Stroke ICU", "step_stroke_unit")
                )
            ),
            "step_ich_protocol" to FlowchartNode(
                id = "step_ich_protocol",
                title = "Xử Trí Xuất Huyết Não Cấp (ICH Protocol)",
                stepNumber = 7,
                description = "1. Hạ HA khẩn cấp SBP < 140 mmHg bằng Nicardipine IV.\n2. Đảo ngược chống đông (Idarucizumab cho Dabigatran, Andexanet alfa / PCC cho Xa inhibitors/Warfarin).\n3. Hội chẩn Ngoại thần kinh nếu khối máu tụ > 30ml hoặc chèn ép não thất.",
                isCriticalWarning = true,
                options = listOf(
                    FlowchartOption("Quay lại Đầu trang", "start")
                )
            ),
            "step_stroke_unit" to FlowchartNode(
                id = "step_stroke_unit",
                title = "Chăm Sóc Tại Đơn Vị Đột Quỵ (Stroke Unit)",
                stepNumber = 8,
                description = "Chăm sóc toàn diện: Kiểm soát Đường huyết (8-10 mmol/L), Thân nhiệt (< 37.5 C), Đánh giá Rối loạn nuốt (GUSS/Water swallow test trước khi cho ăn/uống), Phục hồi chức năng sớm trong 24-48h.",
                options = listOf(
                    FlowchartOption("Hoàn tất Lưu đồ - Quay lại", "start")
                )
            )
        )
    )
}
