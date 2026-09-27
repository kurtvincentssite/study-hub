package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FocusTimerState
import com.example.data.NavDestination
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun StudyFocusScreen(
    timerState: FocusTimerState,
    onNavigate: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMode by remember { mutableStateOf("Pomodoro (25/5)") }
    var isTimerRunning by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableStateOf(25 * 60) }
    var selectedSubject by remember { mutableStateOf("Advanced Web Development") }
    var studyGoal by remember { mutableStateOf("Review PHP OOP concepts and MVC architectures") }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    val progress = (remainingSeconds.toFloat() / (25 * 60)).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Title & Subtitle
        Column {
            Text(
                text = "Study Focus",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Pomodoro & Deep Work Sessions",
                fontSize = 12.sp,
                color = onSurfaceVariant
            )
        }

        // Mode Pills (Horizontal Scroll)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Pomodoro (25/5)", "Short Study (50/10)", "Deep Focus (90/20)").forEach { mode ->
                val isSelected = selectedMode == mode
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) StudyHubTeal else surfaceColor)
                        .border(1.dp, if (isSelected) StudyHubTeal else outlineColor, RoundedCornerShape(20.dp))
                        .clickable { selectedMode = mode }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = mode,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else onSurface
                    )
                }
            }
        }

        // Subject Selector
        Column {
            Text(
                text = "SELECT SUBJECT",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                    .background(surfaceColor)
                    .clickable {
                        selectedSubject = if (selectedSubject == "Advanced Web Development") "Database Management" else "Advanced Web Development"
                    }
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(selectedSubject, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = onSurface)
                    Text("▾", fontSize = 12.sp, color = onSurfaceVariant)
                }
            }
        }

        // Study Goal Input
        Column {
            Text(
                text = "STUDY GOAL",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                    .background(surfaceColor)
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Text(studyGoal, fontSize = 12.sp, color = onSurface)
            }
        }

        // Main Timer Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("card_study_timer"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Subject Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE6FFFA))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = selectedSubject.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = StudyHubTeal,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Circular Timer Ring
                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 14.dp.toPx()
                        // Track ring
                        drawArc(
                            color = Color(0xFFE2E8F0),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        // Progress ring
                        drawArc(
                            color = StudyHubTeal,
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = timeFormatted,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "REMAINING",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceVariant,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Pomodoro Mode — Session 3 of 4",
                    fontSize = 12.sp,
                    color = onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { isTimerRunning = !isTimerRunning },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("btn_timer_toggle")
                    ) {
                        Icon(
                            imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isTimerRunning) "Pause Session" else "Start Session",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            isTimerRunning = false
                            remainingSeconds = 25 * 60
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("btn_finish_early")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Finish Early", color = onSurface, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }
            }
        }

        // 3 Stat Tiles in a row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StudyStatPill(
                title = "Study Time",
                value = "2.5 hrs",
                subtext = "Target: 4.0h",
                modifier = Modifier.weight(1f)
            )
            StudyStatPill(
                title = "Sessions",
                value = "3 / 4",
                subtext = "75% Done",
                modifier = Modifier.weight(1f)
            )
            StudyStatPill(
                title = "Streak",
                value = "12 Days",
                subtext = "🔥 on fire",
                modifier = Modifier.weight(1f)
            )
        }

        // Section: Recent Session History
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Session History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = onSurface
                )
                Text(
                    text = "View All",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = StudyHubTeal
                )
            }

            val sessionHistory = listOf(
                SessionHistoryItem("WEB DEVELOPMENT", "Review Database Joins", "Yesterday", "24 min focused", 0xFF0D9488),
                SessionHistoryItem("INFORMATION ASSURANCE", "Case Study Drafting", "Yesterday", "52 min focused", 0xFFF97316),
                SessionHistoryItem("MOBILE COMPUTING", "Compose Layout Optimization", "2 days ago", "45 min focused", 0xFF8B5CF6)
            )

            sessionHistory.forEach { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(item.colorHex).copy(alpha = 0.12f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(item.tag, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(item.colorHex))
                            }
                            Text(item.date, fontSize = 10.sp, color = onSurfaceVariant)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(item.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = onSurface)

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(item.durationText, fontSize = 11.sp, color = onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

private data class SessionHistoryItem(
    val tag: String,
    val title: String,
    val date: String,
    val durationText: String,
    val colorHex: Long
)

@Composable
private fun StudyStatPill(
    title: String,
    value: String,
    subtext: String,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontSize = 10.sp, color = onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = onSurface)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtext, fontSize = 9.sp, color = onSurfaceVariant)
        }
    }
}
