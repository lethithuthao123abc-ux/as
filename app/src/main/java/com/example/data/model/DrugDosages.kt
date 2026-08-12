package com.example.data.model

data class DrugDosage(
    val name: String,
    val brandNames: String,
    val category: String,
    val indication: String,
    val standardDose2026: String,
    val maxDose: String,
    val contraindications: List<String>,
    val clinicalPearls2026: String
)

object StrokeDrugDatabase {
    val drugs = listOf(
        DrugDosage(
            name = "Tenecteplase (TNK-tPA)",
            brandNames = "Metalyse, TNKase",
            category = "Thuốc Tiêu Sợi Huyết Thế Hệ Mới 2026",
            indication = "Tiêu sợi huyết đường tĩnh mạch cho đột quỵ thiếu máu não cấp < 4.5 giờ (Khuyến cáo Class I 2026 ưu tiên hơn Alteplase cho bệnh nhân có tắc mạch lớn LVO).",
            standardDose2026 = "0.25 mg/kg tiêm tĩnh mạch bolus trực tiếp trong 5-10 giây (Liều đơn duy nhất).",
            maxDose = "Tối đa 25 mg (cho bệnh nhân >= 100 kg).",
            contraindications = listOf(
                "Xuất huyết nội sọ trên CT/MRI",
                "Chấn thương đầu/đột quỵ trong 3 tháng qua",
                "Huyết áp > 185/110 mmHg chưa được hạ",
                "Tiểu cầu < 100,000/mm3, INR > 1.7",
                "Đang dùng NOAC liều điều trị trong 48h (trừ khi dTT/Anti-Xa bình thường)"
            ),
            clinicalPearls2026 = "2026 Update: Tenecteplase 0.25mg/kg tiêm Bolus đơn giản hơn Alteplase infusion 1 giờ, tỉ lệ tái thông mạch lớn trước EVT cao hơn hẳn."
        ),
        DrugDosage(
            name = "Alteplase (rt-PA)",
            brandNames = "Actilyse",
            category = "Thuốc Tiêu Sợi Huyết Tiêu Chuẩn",
            indication = "Tiêu sợi huyết đường tĩnh mạch trong cửa sổ 0 - 4.5 giờ tính từ lúc khởi phát triệu chứng.",
            standardDose2026 = "0.9 mg/kg Tĩnh mạch. Tiêm bolus 10% tổng liều trong 1 phút, 90% còn lại truyền IV trong 60 phút.",
            maxDose = "Tối đa 90 mg (bệnh nhân >= 100 kg).",
            contraindications = listOf(
                "Nhiều hơn 4.5 giờ tính từ lúc bình thường cuối cùng (Last Known Well)",
                "Xuất huyết nội sọ",
                "HA > 185/110 mmHg",
                "Tiền sử xuất huyết não"
            ),
            clinicalPearls2026 = "Đo HA mỗi 15 phút trong 2 giờ đầu truyền. Ngưng truyền khẩn cấp nếu bệnh nhân đau đầu dữ dội, nôn, HA tăng vọt hoặc tụt tri giác (nghi xuất huyết)."
        ),
        DrugDosage(
            name = "Aspirin + Clopidogrel (DAPT 2026)",
            brandNames = "Plavix, Aspirin 81/100, Duoplavin",
            category = "Kháng Tiểu Cầu Kép (DAPT)",
            indication = "Đột quỵ nhẹ (NIHSS <= 3) hoặc Cơn thiếu máu cục bộ thoáng qua (TIA) nguy cơ trung bình - cao (ABCD2 >= 4) trong 24h đầu.",
            standardDose2026 = "Liều nạp: Aspirin 225-300mg + Clopidogrel 300mg ngày đầu. Sau đó duy trì Aspirin 75-100mg/ngày + Clopidogrel 75mg/ngày trong ĐÚNG 21 NGÀY (theo THALES/POINT/CHANCE).",
            maxDose = "Clopidogrel 75mg/ngày + Aspirin 100mg/ngày sau liều nạp.",
            contraindications = listOf(
                "Xử trí tiêu sợi huyết TSH trong vòng 24 giờ qua (Trì hoãn kháng tiểu cầu 24h sau TSH!)",
                "Xuất huyết tiêu hóa cấp",
                "Dị ứng nặng"
            ),
            clinicalPearls2026 = "KHÔNG dùng DAPT kéo dài > 21-90 ngày trong đơn thuần đột quỵ không do tim vì tăng nguy cơ xuất huyết nội sọ mà không tăng hiệu quả."
        ),
        DrugDosage(
            name = "Nicardipine IV",
            brandNames = "Cardene",
            category = "Thuốc Hạ Huyết Áp Cấp Cứu",
            indication = "Hạ HA cấp cứu cho bệnh nhân Đột quỵ thiếu máu cấp có chỉ định TSH (Target < 185/110 mmHg) hoặc Xuất huyết não cấp ICH (Target SBP < 140 mmHg).",
            standardDose2026 = "Khởi đầu truyền IV 5 mg/giờ. Tăng liều 2.5 mg/giờ mỗi 5-15 phút cho tới khi đạt HA mục tiêu. Liều duy trì 3 - 5 mg/giờ.",
            maxDose = "15 mg/giờ.",
            contraindications = listOf("Hẹp van động mạch chủ nặng"),
            clinicalPearls2026 = "Truyền qua đường tĩnh mạch lớn/trung tâm. Theo dõi HA liên tục bằng đo HA xâm nhập hoặc tự động mỗi 5 phút."
        ),
        DrugDosage(
            name = "Apixaban / Rivaroxaban / Dabigatran (NOACs/DOACs)",
            brandNames = "Eliquis, Xarelto, Pradaxa",
            category = "Kháng Đông Đường Uống Thế Hệ Mới 2026",
            indication = "Dự phòng thứ phát đột quỵ ở bệnh nhân Rung Nhĩ phi van tim (NVAF).",
            standardDose2026 = "Apixaban 5mg x 2 lần/ngày (giảm 2.5mg x 2 nếu >= 2 tiêu chí: tuổi >=80, cân nặng <=60kg, Cr >= 133 umol/L). Rivaroxaban 20mg x 1 lần/ngày cùng thức ăn (15mg nếu CrCl 30-49 ml/min).",
            maxDose = "Theo chỉ định từng thuốc.",
            contraindications = listOf("Rung nhĩ do hẹp van 2 lá trung bình-nặng hoặc Van tim cơ học (BẮT BUỘC DÙNG WARFARIN)"),
            clinicalPearls2026 = "Quy tắc khởi động lại Kháng đông sau Đột quỵ cấp (Quy tắc 1-3-6-12 ngày): TIA (1 ngày), Đột quỵ nhẹ (3 ngày), Đột quỵ vừa (6 ngày), Đột quỵ nặng (12-14 ngày)."
        ),
        DrugDosage(
            name = "Atorvastatin / Rosuvastatin (Liều Cao 2026)",
            brandNames = "Lipitor, Crestor",
            category = "Statin Cường Độ Cao (High-Intensity Statin)",
            indication = "Mọi bệnh nhân Đột quỵ thiếu máu não do xơ vữa động mạch.",
            standardDose2026 = "Atorvastatin 40-80 mg/ngày hoặc Rosuvastatin 20-40 mg/ngày. Mục tiêu LDL-C 2026: Giảm >= 50% LDL ban đầu VÀ đạt LDL-C < 1.4 mmol/L (55 mg/dL).",
            maxDose = "Atorvastatin 80mg, Rosuvastatin 40mg.",
            contraindications = listOf("Bệnh gan cấp tính, suy gan tiến triển"),
            clinicalPearls2026 = "Nếu không đạt LDL < 1.4 mmol/L với Statin liều cao tối đa, KHUYẾN CÁO phối hợp sớm Ezetimibe 10mg hoặc thuốc ức chế PCSK9."
        )
    )
}
