package com.example.data.model

// NIHSS Item definition
data class NihssItem(
    val id: Int,
    val title: String,
    val description: String,
    val options: List<NihssOption>
)

data class NihssOption(
    val score: Int,
    val label: String,
    val detail: String = ""
)

object NihssData {
    val items = listOf(
        NihssItem(
            id = 1,
            title = "1a. Mức độ tri giác (Level of Consciousness)",
            description = "Đánh giá khả năng đáp ứng và nhận thức chung của bệnh nhân.",
            options = listOf(
                NihssOption(0, "0 - Tỉnh táo hoàn toàn", "Phản ứng nhanh nhạy, đúng đắn."),
                NihssOption(1, "1 - Lơ mơ (Somnolent)", "Cần kích thích nhẹ để tỉnh, nhưng đáp ứng đúng."),
                NihssOption(2, "2 - Sững sờ (Stupor)", "Cần kích thích lặp lại/mạnh để đáp ứng hoặc không tự phát."),
                NihssOption(3, "3 - Hôn mê (Coma)", "Chỉ phản ứng đáp ứng vận động phản xạ hoặc không phản ứng.")
            )
        ),
        NihssItem(
            id = 2,
            title = "1b. Tri giác: Trả lời câu hỏi (LOC Questions)",
            description = "Hỏi tuổi bệnh nhân và tháng hiện tại. (Bệnh nhân không nói được do nội khí quản, chấn thương hàm... cho 2 điểm).",
            options = listOf(
                NihssOption(0, "0 - Trả lời đúng cả 2 câu", "Đúng cả tuổi và tháng."),
                NihssOption(1, "1 - Trả lời đúng 1 câu", "Đúng tuổi hoặc tháng."),
                NihssOption(2, "2 - Không trả lời đúng câu nào", "Sai cả 2 câu hoặc do thất ngôn/hôn mê.")
            )
        ),
        NihssItem(
            id = 3,
            title = "1c. Tri giác: Thực hiện mệnh lệnh (LOC Commands)",
            description = "Yêu cầu nhắm/mở mắt và nắm/mở bàn tay bên không liệt.",
            options = listOf(
                NihssOption(0, "0 - Làm đúng cả 2 mệnh lệnh", "Thực hiện chính xác nhắm mắt và nắm tay."),
                NihssOption(1, "1 - Làm đúng 1 mệnh lệnh", "Chỉ làm đúng 1 trong 2 yêu cầu."),
                NihssOption(2, "2 - Không làm đúng mệnh lệnh nào", "Không thực hiện được cả 2.")
            )
        ),
        NihssItem(
            id = 4,
            title = "2. Vận động nhãn cầu (Gaze)",
            description = "Chỉ đánh giá di chuyển mắt ngang theo ý muốn hoặc phản xạ đầu - mắt.",
            options = listOf(
                NihssOption(0, "0 - BÌnh thường", "Cơ nhãn cầu hoạt động bình thường."),
                NihssOption(1, "1 - Liệt liếc mắt một phần", "Bị hạn chế liếc về một bên."),
                NihssOption(2, "2 - Liệt liếc mắt hoàn toàn", "Mắt lệch hẳn về một bên, không qua đường giữa.")
            )
        ),
        NihssItem(
            id = 5,
            title = "3. Thị trường (Visual Fields)",
            description = "Kiểm tra thị trường 4 phần tư bằng đối chiếu hoặc phản xạ chớp mắt.",
            options = listOf(
                NihssOption(0, "0 - Không khuyết thị trường", "Thị trường bình thường."),
                NihssOption(1, "1 - Bán manh một phần", "Mất thị trường 1 phần tư."),
                NihssOption(2, "2 - Bán manh hoàn toàn", "Mất bán manh đồng danh một bên."),
                NihssOption(3, "3 - Mù hoàn toàn (Bilateral Hemianopia)", "Mù cả hai bên hoặc hôn mê.")
            )
        ),
        NihssItem(
            id = 6,
            title = "4. Liệt mặt (Facial Palsy)",
            description = "Bệnh nhân nhe răng, nhướng mày, nhắm chặt mắt.",
            options = listOf(
                NihssOption(0, "0 - Liệt mặt bình thường", "Cơ mặt cân đối."),
                NihssOption(1, "1 - Liệt nhẹ", "Mờ rãnh mũi má, nụ cười hơi lệch nhẹ."),
                NihssOption(2, "2 - Liệt rõ (Mặt liệt phần dưới)", "Mất hẳn rãnh mũi má lệch rõ khi nhe răng."),
                NihssOption(3, "3 - Liệt mặt hoàn toàn", "Mất vận động hoàn toàn nửa mặt trên và dưới.")
            )
        ),
        NihssItem(
            id = 7,
            title = "5a. Vận động Tay Tái (Left Arm Drift)",
            description = "Giơ tay trái 90 độ (ngồi) hoặc 45 độ (nằm) trong 10 giây.",
            options = listOf(
                NihssOption(0, "0 - Không rơi", "Giơ giữ vững đủ 10 giây."),
                NihssOption(1, "1 - Rơi từ từ", "Tay từ từ hạ xuống trước 10 giây nhưng không chạm giường."),
                NihssOption(2, "2 - Tay rơi chạm giường", "Không giữ được 10 giây, tay rơi chạm giường."),
                NihssOption(3, "3 - Không giơ tay được", "Không giơ được tay lên, chỉ nhúc nhích ngón tay."),
                NihssOption(4, "4 - Liệt hoàn toàn", "Không có cử động vận động nào.")
            )
        ),
        NihssItem(
            id = 8,
            title = "5b. Vận động Tay Phải (Right Arm Drift)",
            description = "Giơ tay phải 90 độ (ngồi) hoặc 45 độ (nằm) trong 10 giây.",
            options = listOf(
                NihssOption(0, "0 - Không rơi", "Giơ giữ vững đủ 10 giây."),
                NihssOption(1, "1 - Rơi từ từ", "Tay từ từ hạ xuống trước 10 giây nhưng không chạm giường."),
                NihssOption(2, "2 - Tay rơi chạm giường", "Không giữ được 10 giây, tay rơi chạm giường."),
                NihssOption(3, "3 - Không giơ tay được", "Không giơ được tay lên, chỉ nhúc nhích ngón tay."),
                NihssOption(4, "4 - Liệt hoàn toàn", "Không có cử động vận động nào.")
            )
        ),
        NihssItem(
            id = 9,
            title = "6a. Vận động Chân Trái (Left Leg Drift)",
            description = "Giơ chân trái 30 độ (nằm ngửa) trong 5 giây.",
            options = listOf(
                NihssOption(0, "0 - Không rơi", "Giữ vững chân đủ 5 giây."),
                NihssOption(1, "1 - Rơi từ từ", "Chân rơi xuống trước 5 giây nhưng không chạm giường."),
                NihssOption(2, "2 - Chân rơi chạm giường", "Chân rơi nhanh chạm giường trước 5 giây."),
                NihssOption(3, "3 - Không giơ chân được", "Không nhấc chân lên khỏi giường được."),
                NihssOption(4, "4 - Liệt hoàn toàn", "Không cử động.")
            )
        ),
        NihssItem(
            id = 10,
            title = "6b. Vận động Chân Phải (Right Leg Drift)",
            description = "Giơ chân phải 30 độ (nằm ngửa) trong 5 giây.",
            options = listOf(
                NihssOption(0, "0 - Không rơi", "Giữ vững chân đủ 5 giây."),
                NihssOption(1, "1 - Rơi từ từ", "Chân rơi xuống trước 5 giây nhưng không chạm giường."),
                NihssOption(2, "2 - Chân rơi chạm giường", "Chân rơi nhanh chạm giường trước 5 giây."),
                NihssOption(3, "3 - Không giơ chân được", "Không nhấc chân lên khỏi giường được."),
                NihssOption(4, "4 - Liệt hoàn toàn", "Không cử động.")
            )
        ),
        NihssItem(
            id = 11,
            title = "7. Thất điều vận động (Limb Ataxia)",
            description = "Thử nghiệm Ngón tay - Mũi và Gót chân - Cẳng chân 2 bên.",
            options = listOf(
                NihssOption(0, "0 - Không thất điều", "Động tác chính xác cả hai bên."),
                NihssOption(1, "1 - Thất điều 1 chi", "Có rối loạn tọa độ ở 1 tay hoặc 1 chân."),
                NihssOption(2, "2 - Thất điều 2 chi trở lên", "Có thất điều ở tay và chân.")
            )
        ),
        NihssItem(
            id = 12,
            title = "8. Cảm giác (Sensory)",
            description = "Thử cảm giác kim thấu ở mặt, tay, chân hai bên.",
            options = listOf(
                NihssOption(0, "0 - Bình thường", "Cảm giác đối xứng 2 bên."),
                NihssOption(1, "1 - Giảm cảm giác nhẹ/vừa", "Bệnh nhân thấy giảm cảm giác nhưng vẫn nhận biết đau."),
                NihssOption(2, "2 - Mất cảm giác nặng/hoàn toàn", "Không nhận biết kim châm một bên.")
            )
        ),
        NihssItem(
            id = 13,
            title = "9. Ngôn ngữ / Thất ngôn (Best Language)",
            description = "Mô tả bức tranh tiêu chuẩn, đặt tên đồ vật, đọc câu.",
            options = listOf(
                NihssOption(0, "0 - Ngôn ngữ bình thường", "Trôi chảy, hiểu và diễn đạt tốt."),
                NihssOption(1, "1 - Thất ngôn nhẹ - vừa", "Nói hơi trắc trở hoặc gọi tên nhầm đồ vật."),
                NihssOption(2, "2 - Thất ngôn nặng", "Diễn đạt cực kỳ khó khăn, không hiểu rõ mệnh lệnh."),
                NihssOption(3, "3 - Mất ngôn ngữ hoàn toàn / Hôn mê", "Không thể giao tiếp hoặc hiểu được ngôn ngữ.")
            )
        ),
        NihssItem(
            id = 14,
            title = "10. Phát âm / Nói ngọng (Dysarthria)",
            description = "Đọc danh sách từ tiêu chuẩn (VD: Mama, 50-50, Huckleberry).",
            options = listOf(
                NihssOption(0, "0 - Phát âm bình thường", "Nói tròn vàõ rõ tiếng."),
                NihssOption(1, "1 - Nói ngọng nhẹ/trung bình", "Phát âm không rõ câu chữ nhưng vẫn hiểu được."),
                NihssOption(2, "2 - Nói ngọng nặng / Không thể phát âm", "Nói hoàn toàn không thể nghe hiểu được hoặc câm.")
            )
        ),
        NihssItem(
            id = 15,
            title = "11. Mất chú ý / Bỏ qua (Extinction and Inattention)",
            description = "Kích thích đồng thời 2 bên thị giác/thị giác/cảm giác.",
            options = listOf(
                NihssOption(0, "0 - Không mất chú ý", "Nhận biết tốt kích thích 2 bên."),
                NihssOption(1, "1 - Mất chú ý 1 giác quan", "Bỏ qua kích thích một bên khi kích thích đồng thời."),
                NihssOption(2, "2 - Mất chú ý nửa người hoàn toàn (Profound neglect)", "Bỏ qua hoàn toàn nửa người bên liệt.")
            )
        )
    )

    fun getSeverity(score: Int): Pair<String, String> {
        return when {
            score == 0 -> "Bình thường" to "Không có dấu hiệu thần kinh định vị rõ."
            score in 1..4 -> "Đột quỵ nhẹ (Mild)" to "Cân nhắc tiêu sợi huyết nếu khuyết tật ảnh hưởng chức năng (disabling)."
            score in 5..15 -> "Đột quỵ trung bình (Moderate)" to "Chỉ định mạnh cho tiêu sợi huyết TSH (Alteplase/TNK) & tầm soát tắc mạch lớn EVT."
            score in 16..20 -> "Đột quỵ nặng (Moderate to Severe)" to "Nguy cơ cao tắc mạch lớn (LVO). Chỉ định khẩn EVT + TSH."
            else -> "Đột quỵ cực kỳ nặng (Severe >20)" to "Cần chụp CTA/CTP khẩn cấp đánh giá EVT, theo dõi nguy cơ chuyển dạng xuất huyết."
        }
    }
}

// ABCD2 score model
data class Abcd2Criteria(
    val ageGt60: Boolean = false, // 1
    val bpHigh: Boolean = false, // SBP >= 140 or DBP >= 90 (1)
    val clinicalUnilateralWeakness: Boolean = false, // 2 points
    val clinicalSpeechOnly: Boolean = false, // 1 point
    val durationGt60min: Boolean = false, // 2 points
    val duration10to59min: Boolean = false, // 1 point
    val diabetes: Boolean = false // 1 point
) {
    fun calculateScore(): Int {
        var score = 0
        if (ageGt60) score += 1
        if (bpHigh) score += 1
        if (clinicalUnilateralWeakness) score += 2
        else if (clinicalSpeechOnly) score += 1
        if (durationGt60min) score += 2
        else if (duration10to59min) score += 1
        if (diabetes) score += 1
        return score
    }

    fun getRiskCategory(): Pair<String, String> {
        val s = calculateScore()
        return when {
            s <= 3 -> "Nguy cơ thấp (0-3 điểm)" to "Nguy cơ đột quỵ 2 ngày: ~1.0%. Chỉ định dùng Aspirin đơn độc hoặc khảo sát ngoại trú khẩn."
            s in 4..5 -> "Nguy cơ trung bình (4-5 điểm)" to "Nguy cơ đột quỵ 2 ngày: ~4.1%. KHUYẾN CÁO 2026: Dùng DAPT (Aspirin + Clopidogrel) trong 21 ngày đầu!"
            else -> "Nguy cơ cao (6-7 điểm)" to "Nguy cơ đột quỵ 2 ngày: ~8.1%. KHUYẾN CÁO 2026: Nhập viện khẩn, dùng DAPT 21 ngày + tầm soát hẹp mạch cảnh/Rung nhĩ."
        }
    }
}

// CHA2DS2-VASc score model
data class Cha2ds2VascCriteria(
    val congestiveHeartFailure: Boolean = false, // 1
    val hypertension: Boolean = false, // 1
    val age75OrOlder: Boolean = false, // 2
    val diabetes: Boolean = false, // 1
    val strokeTiaThromboembolism: Boolean = false, // 2
    val vascularDisease: Boolean = false, // 1
    val age65To74: Boolean = false, // 1
    val femaleGender: Boolean = false // 1
) {
    fun calculateScore(): Int {
        var score = 0
        if (congestiveHeartFailure) score += 1
        if (hypertension) score += 1
        if (age75OrOlder) score += 2
        else if (age65To74) score += 1
        if (diabetes) score += 1
        if (strokeTiaThromboembolism) score += 2
        if (vascularDisease) score += 1
        if (femaleGender) score += 1
        return score
    }

    fun getAnticoagulationGuideline(): Pair<String, String> {
        val s = calculateScore()
        return when {
            s == 0 -> "Nguy cơ rất thấp (0 điểm)" to "Class III: Không cần dùng thuốc chống đông (NOAC/VKA)."
            s == 1 -> "Nguy cơ trung bình (1 điểm)" to "Class IIb: Có thể cân nhắc dùng Kháng đông đường uống thế hệ mới (NOAC: Apixaban, Rivaroxaban, Dabigatran, Edoxaban)."
            else -> "Nguy cơ cao (>= 2 điểm)" to "Class I (Khuyến cáo mạnh 2026): BẮT BUỘC dùng NOAC/DOAC trừ khi có chống chỉ định (Ưu tiên NOAC hơn Warfarin)."
        }
    }
}

// HAS-BLED score model
data class HasBledCriteria(
    val hypertensionUncontrolled: Boolean = false, // SBP > 160 (1)
    val abnormalRenalOrLiver: Int = 0, // 0, 1 or 2 points
    val strokeHistory: Boolean = false, // 1
    val bleedingTendency: Boolean = false, // 1
    val labileINR: Boolean = false, // 1
    val elderlyAgeGt65: Boolean = false, // 1
    val drugsOrAlcohol: Int = 0 // 0, 1 or 2 points (NSAIDs/antiplatelets + alcohol)
) {
    fun calculateScore(): Int {
        var s = 0
        if (hypertensionUncontrolled) s += 1
        s += abnormalRenalOrLiver
        if (strokeHistory) s += 1
        if (bleedingTendency) s += 1
        if (labileINR) s += 1
        if (elderlyAgeGt65) s += 1
        s += drugsOrAlcohol
        return s
    }

    fun getInterpretation(): String {
        val score = calculateScore()
        return if (score >= 3) {
            "Điểm HAS-BLED = $score (Nguy cơ xuất huyết CAO >=3). Cần kiểm soát chặt các yếu tố nguy cơ có thể thay đổi (HA, INR, NSAID) và theo dõi sát bệnh nhân, KHÔNG TỰ Ý ngưng chống đông!"
        } else {
            "Điểm HAS-BLED = $score (Nguy cơ xuất huyết Thấp - Trung bình). An toàn khởi đầu hoặc duy trì điều trị chống đông."
        }
    }
}
