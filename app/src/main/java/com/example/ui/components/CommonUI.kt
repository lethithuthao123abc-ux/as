package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EvidenceClass
import com.example.data.model.EvidenceLevel
import com.example.data.model.StrokeGuideline
import com.example.data.viewmodel.StrokeViewModel
import com.example.ui.theme.MedicalEmergencyRed
import com.example.ui.theme.MedicalPrimaryLight
import com.example.ui.theme.MedicalSuccessGreen
import com.example.ui.theme.MedicalWarningAmber

@Composable
fun EvidenceBadge(
    evidenceClass: EvidenceClass,
    evidenceLevel: EvidenceLevel,
    modifier: Modifier = Modifier
) {
    val (classBg, classFg) = when (evidenceClass) {
        EvidenceClass.CLASS_1 -> MedicalSuccessGreen.copy(alpha = 0.15f) to MedicalSuccessGreen
        EvidenceClass.CLASS_2A -> MedicalPrimaryLight.copy(alpha = 0.15f) to MedicalPrimaryLight
        EvidenceClass.CLASS_2B -> MedicalWarningAmber.copy(alpha = 0.15f) to MedicalWarningAmber
        else -> MedicalEmergencyRed.copy(alpha = 0.15f) to MedicalEmergencyRed
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = classBg,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = evidenceClass.tag,
                color = classFg,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }

        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = evidenceLevel.tag,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
fun HeaderBannerCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF0369A1),
                        Color(0xFF0284C7)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = Color.Red.copy(alpha = 0.2f),
                    shape = CircleShape,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    text = "KHUYẾN CÁO ĐỘT QUỤ 2026",
                    color = Color(0xFF38BDF8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = Color(0xFFE2E8F0),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GuidelineCard(
    guideline: StrokeGuideline,
    viewModel: StrokeViewModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isBookmarked by viewModel.isBookmarked(guideline.id).collectAsState(initial = false)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = guideline.category.displayName,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = guideline.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleBookmark(guideline, isBookmarked) }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            EvidenceBadge(
                evidenceClass = guideline.evidenceClass,
                evidenceLevel = guideline.evidenceLevel
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = guideline.summary,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Practical Tip box
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Thực hành: ${guideline.practicalTip}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
