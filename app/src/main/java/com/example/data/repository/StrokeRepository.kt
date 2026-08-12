package com.example.data.repository

import com.example.data.db.BookmarkItem
import com.example.data.db.PatientRecord
import com.example.data.db.StrokeDao
import com.example.data.model.EvidenceClass
import com.example.data.model.EvidenceLevel
import com.example.data.model.GuidelineCategory
import com.example.data.model.StrokeGuideline
import kotlinx.coroutines.flow.Flow

class StrokeRepository(private val strokeDao: StrokeDao) {

    val allPatientRecords: Flow<List<PatientRecord>> = strokeDao.getAllPatientRecords()
    val bookmarkedGuidelines: Flow<List<BookmarkItem>> = strokeDao.getAllBookmarks()

    suspend fun savePatientRecord(record: PatientRecord): Long = strokeDao.insertPatientRecord(record)
    suspend fun deletePatientRecord(id: Long) = strokeDao.deletePatientRecordById(id)

    suspend fun toggleBookmark(guideline: StrokeGuideline, isCurrentlyBookmarked: Boolean) {
        if (isCurrentlyBookmarked) {
            strokeDao.removeBookmark(guideline.id)
        } else {
            strokeDao.addBookmark(
                BookmarkItem(
                    guidelineId = guideline.id,
                    title = guideline.title,
                    categoryName = guideline.category.displayName,
                    summary = guideline.summary
                )
            )
        }
    }

    fun isBookmarked(guidelineId: String): Flow<Boolean> = strokeDao.isBookmarked(guidelineId)

    // Complete 2026 Stroke Guidelines Repository Dataset
    fun getGuidelines(): List<StrokeGuideline> {
        return listOf(
            StrokeGuideline(
                id = "g_2026_01",
                title = "Khuyến Cáo Chuỗi Cấp Cứu & Đơn Vị Đột Quỵ (Stroke Unit 2026)",
                category = GuidelineCategory.EMERGENCY,
                summary = "Bắt buộc chuyển bệnh nhân nghi đột quỵ đến trung tâm có Đơn vị Đột quỵ (Stroke Unit) hoặc cấp cứu đột quỵ chuyên sâu.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Đạt chỉ số thời gian vàng: Door-to-Needle (từ lúc đến cửa viện đến tiêm thuốc tiêu sợi huyết) < 30-45 phút.",
                    "Door-to-Puncture (từ lúc đến cửa viện đến chọc kim can thiệp EVT) < 60 phút.",
                    "Đánh giá BE-FAST (Balance, Eyes, Face, Arms, Speech, Time) ngay tại tuyến cấp cứu ngoại viện (EMS).",
                    "Đường huyết mao mạch khẩn bắt buộc làm ngay để loại trừ hạ đường huyết mô phỏng đột quỵ."
                ),
                practicalTip = "Mọi bệnh nhân đột quỵ cấp cần nằm đầu bằng (hoặc đầu cao 15-30 độ nếu có nguy cơ sặc hoặc tăng áp lực nội sọ) và duy trì SpO2 > 94%.",
                isKeyHeadline = true
            ),
            StrokeGuideline(
                id = "g_2026_02",
                title = "Ưu Tiên Tenecteplase (TNK-tPA) Cho Tiêu Sợi Huyết Tĩnh Mạch 2026",
                category = GuidelineCategory.THROMBOLYSIS_EVT,
                summary = "Khuyến cáo 2026 chính thức xếp Tenecteplase (TNK) 0.25mg/kg tiêm Bolus là lựa chọn thay thế hợp lý hoặc ưu tiên hơn Alteplase 0.9mg/kg.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Cửa sổ thời gian: 0 - 4.5 giờ kể từ thời điểm Last Known Well (LKW).",
                    "Liều dùng TNK: 0.25 mg/kg tiêm bolus tĩnh mạch duy nhất (Tối đa 25 mg), không cần truyền kéo dài 1 giờ.",
                    "TNK có độ gắn kết Fibrin cao hơn, nửa đời thải trừ dài hơn và tỉ lệ tái thông động mạch bị tắc lớn (LVO) cao hơn so với Alteplase.",
                    "Nếu không có TNK, tiếp tục dùng Alteplase 0.9 mg/kg (10% bolus, 90% truyền trong 60 phút)."
                ),
                practicalTip = "Tiêm TNK nhanh gọn giúp tiết kiệm 'thời gian vàng' quý giá trước khi chuyển bệnh nhân sang phòng can thiệp EVT.",
                isKeyHeadline = true
            ),
            StrokeGuideline(
                id = "g_2026_03",
                title = "Lấy Huyết Khối Bằng Dụng Cụ Cơ Học (EVT / Thrombectomy) Cửa Sổ 0-6 Giờ",
                category = GuidelineCategory.THROMBOLYSIS_EVT,
                summary = "EVT được chỉ định bắt buộc (Class I, Level A) cho bệnh nhân tắc động mạch lớn tuần hoàn não trước (ICA, MCA M1) trong cửa sổ 6 giờ.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Tiêu chuẩn: Tuổi >= 18, NIHSS >= 6, ASPECT >= 6 trên CT không tiêm thuốc.",
                    "Tắc đoạn trong sọ động mạch cảnh trong (ICA) hoặc đoạn M1 động mạch não giữa (MCA).",
                    "Thực hiện EVT NGAY LẬP TỨC song song hoặc sau tiêm tiêu sợi huyết tĩnh mạch (KHÔNG chờ theo dõi đáp ứng TSH).",
                    "Tái thông thành công đạt TICI 2b/3."
                ),
                practicalTip = "Door-to-Puncture mục tiêu < 60 phút. Kích hoạt 'Code Stroke' liên khoa Cấp cứu - Chẩn đoán hình ảnh - Can thiệp mạch máu.",
                isKeyHeadline = true
            ),
            StrokeGuideline(
                id = "g_2026_04",
                title = "EVT Trong Cửa Sổ Mở Rộng 6 - 24 Giờ (DAWN & DEFUSE-3 Criteria)",
                category = GuidelineCategory.THROMBOLYSIS_EVT,
                summary = "Khuyến cáo EVT cho bệnh nhân tắc mạch lớn LVO đến muộn (6 - 24h) hoặc đột quỵ lúc ngủ dậy (Wake-up stroke) khi có Mismatch trên CTP/MRI.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Sử dụng cắt lớp vi tính tưới máu (CT Perfusion) hoặc MRI (DWI-FLAIR / PWI) để đánh giá lõi nhồi máu (Ischemic Core) và vùng nhu mô có thể cứu sống (Penumbra).",
                    "Tiêu chuẩn DAWN (6-24h): Sự bất tống giữa độ nặng thần kinh (NIHSS) và thể tích lõi nhồi máu trên CTP/DWI.",
                    "Tiêu chuẩn DEFUSE-3 (6-16h): Thể tích lõi nhồi máu < 70ml, tỉ lệ Mismatch > 1.8.",
                    "EVT giúp giảm đáng kể tỉ lệ tàn phế mRS 0-2 ở nhóm bệnh nhân đến muộn."
                ),
                practicalTip = "Đừng từ bỏ bệnh nhân chỉ vì thời gian > 6 giờ! Hãy chụp CTP/MRI khẩn để tìm vùng nhu mô tranh tối tranh sáng."
            ),
            StrokeGuideline(
                id = "g_2026_05",
                title = "Kháng Tiểu Cầu Kép (DAPT) Ngắn Hạn Trong 21 Ngày Đầu 2026",
                category = GuidelineCategory.SECONDARY_PREVENTION,
                summary = "DAPT (Aspirin + Clopidogrel) được chỉ định trong 21 ngày đầu cho Đột quỵ nhẹ (NIHSS <= 3) hoặc TIA nguy cơ cao (ABCD2 >= 4).",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Khởi đầu DAPT càng sớm càng tốt (trong vòng 24 giờ kể từ khi khởi phát).",
                    "Liều nạp ngày 1: Aspirin 225-300mg + Clopidogrel 300mg.",
                    "Liều duy trì ngày 2-21: Aspirin 75-100mg + Clopidogrel 75mg hàng ngày.",
                    "Sau 21 ngày: Chuyển sang kháng tiểu cầu đơn độc (SAPT: Clopidogrel 75mg hoặc Aspirin 81-100mg) để tránh nguy cơ xuất huyết."
                ),
                practicalTip = "TRÌ HOÃN dùng kháng tiểu cầu 24 giờ nếu bệnh nhân vừa được tiêm tiêu sợi huyết TSH."
            ),
            StrokeGuideline(
                id = "g_2026_06",
                title = "Mục Tiêu Lipid Máu 2026: LDL-C < 1.4 mmol/L (55 mg/dL)",
                category = GuidelineCategory.SECONDARY_PREVENTION,
                summary = "Khuyến cáo kiểm sát Lipid máu tích cực bằng Statin liều cao + Ezetimibe cho mọi bệnh nhân nhồi máu não do xơ vữa.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Mục tiêu LDL-C 2026: Đạt LDL-C < 1.4 mmol/L (55 mg/dL) VÀ giảm >= 50% so với trị số ban đầu.",
                    "Lựa chọn hàng đầu: Statin cường độ cao (Atorvastatin 40-80mg hoặc Rosuvastatin 20-40mg).",
                    "Nếu sau 4-12 tuần Statin liều tối đa chưa đạt mục tiêu: Phối hợp ngay Ezetimibe 10mg/ngày.",
                    "Nếu vẫn chưa đạt ở bệnh nhân nguy cơ rất cao: Cân nhắc phối hợp Thuốc ức chế PCSK9 (Evolocumab / Alirocumab)."
                ),
                practicalTip = "Khởi động Statin ngay trong thời gian nằm viện, không chờ kết quả bilan mỡ máu xuất viện."
            ),
            StrokeGuideline(
                id = "g_2026_07",
                title = "Chống Đông NOAC Trong Rung Nhĩ Phi Van Tim (NVAF) 2026",
                category = GuidelineCategory.CARDIAC_AF,
                summary = "Thuốc kháng đông đường uống thế hệ mới (NOAC/DOAC) là lựa chọn ưu tiên hàng đầu so với Warfarin cho bệnh nhân Rung nhĩ có đột quỵ.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Các thuốc NOAC được phê duyệt: Apixaban, Dabigatran, Edoxaban, Rivaroxaban.",
                    "Dùng Warfarin (mục tiêu INR 2.0-3.0) CHỈ KHI bệnh nhân có Van tim cơ học hoặc Hẹp van 2 lá do thấp tim từ trung bình đến nặng.",
                    "Quy tắc khởi động lại chống đông sau đột quỵ (Rule 1-3-6-12 days): TIA (1 ngày), Đột quỵ nhẹ (3 ngày), Đột quỵ vừa (6-8 ngày), Đột quỵ nặng (12-14 ngày dựa trên CT kiểm tra trừ xuất huyết)."
                ),
                practicalTip = "Tính điểm CHA2DS2-VASc và HAS-BLED. Nguy cơ xuất huyết cao (HAS-BLED >=3) KHÔNG PHẢI là chống chỉ định dùng NOAC mà là lý do cần kiểm soát các yếu tố nguy cơ thay đổi được."
            ),
            StrokeGuideline(
                id = "g_2026_08",
                title = "Xử Trí Huyết Áp Cấp Trong Đột Quỵ Thiếu Máu Não 2026",
                category = GuidelineCategory.EMERGENCY,
                summary = "Quản lý Huyết áp nghiêm ngặt tùy thuộc vào việc bệnh nhân có chỉ định TSH/EVT hay không.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_B_R,
                keyPoints = listOf(
                    "Bệnh nhân CÓ chỉ định Tiêu sợi huyết TSH: Hạ HA khẩn cấp xuống < 185/110 mmHg trước tiêm TSH. Duy trì HA < 180/105 mmHg trong 24 giờ đầu.",
                    "Bệnh nhân KHÔNG dùng TSH/EVT: Chỉ hạ HA nếu HA cực cao > 220/120 mmHg (giảm không quá 15% trong 24h đầu).",
                    "Bệnh nhân thực hiện EVT tái thông hoàn toàn: Duy trì HA tâm thu 120 - 140 mmHg để tránh nguy cơ phù não tái tưới máu.",
                    "Thuốc ưu tiên: Nicardipine IV truyền tĩnh mạch liên tục hoặc Labetalol IV."
                ),
                practicalTip = "Tránh hạ HA quá nhanh quá sâu ở bệnh nhân nhồi máu chưa được tái thông để bảo vệ vùng tưới máu tranh tối tranh sáng!"
            ),
            StrokeGuideline(
                id = "g_2026_09",
                title = "Khuyến Cáo Cấp Cứu Xuất Huyết Trong Não (ICH 2026 Update)",
                category = GuidelineCategory.HEMORRHAGIC,
                summary = "Xuất huyết trong não là cấp cứu y khoa hoả tốc. Cần hạ HA cấp và đảo ngược trạng thái chống đông ngay lập tức.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_A,
                keyPoints = listOf(
                    "Hạ Huyết áp cấp cứu: Bệnh nhân ICH cấp có HA tâm thu 150 - 220 mmHg -> Hạ nhanh SBP xuống mục tiêu 130 - 140 mmHg trong vòng vài giờ đầu bằng Nicardipine IV.",
                    "Ngưng ngay các thuốc chống đông/kháng tiểu cầu đang dùng.",
                    "Đảo ngược chống đông: Nếu dùng Dabigatran -> Idarucizumab 5g IV. Nếu dùng Rivaroxaban/Apixaban -> Andexanet Alfa hoặc Phức hợp Prothrombin Đông đặc (PCC 4 yếu tố). Nếu dùng Warfarin -> Vitamin K1 IV + PCC.",
                    "Phẫu thuật/Dẫn lưu: Chỉ định phẫu thuật lấy máu tụ hố sau (Tiểu não > 3cm) chèn ép thân não hoặc dẫn lưu não thất nếu có tắc nghẽn dịch não tủy."
                ),
                practicalTip = "Chụp CTA khẩn cấp để tìm dấu hiệu 'Spot Sign' - tiên đoán sự tiến triển mở rộng của khối máu tụ trong 24h đầu."
            ),
            StrokeGuideline(
                id = "g_2026_10",
                title = "Phục Hồi Chức Năng & Sàng Lọc Nuốt Sau Đột Quỵ 2026",
                category = GuidelineCategory.REHABILITATION,
                summary = "Khuyến cáo sàng lọc rối loạn nuốt bắt buộc trước khi cho ăn uống và tập phục hồi chức năng sớm trong 24-48 giờ.",
                evidenceClass = EvidenceClass.CLASS_1,
                evidenceLevel = EvidenceLevel.LEVEL_B_NR,
                keyPoints = listOf(
                    "TẤT CẢ bệnh nhân phải được test rối loạn nuốt (VD: GUSS test / Water Swallow Test) trước khi uống nước hay dùng thuốc đường uống.",
                    "Bắt đầu vận động phục hồi chức năng nhẹ nhàng từ 24 - 48 giờ đầu nếu tình trạng lâm sàng ổn định.",
                    "TRÁNH tập vận động cường độ cao quá sớm (< 24h) vì có thể làm giảm tưới máu não.",
                    "Tầm soát & điều trị sớm Trầm cảm sau đột quỵ (Post-stroke depression) bằng SSRI nếu có chỉ định."
                ),
                practicalTip = "Nếu bệnh nhân thất bại test nuốt, đặt ống thông dạ dày (NGT) sớm để đảm bảo dinh dưỡng và dùng thuốc an toàn."
            )
        )
    }
}
