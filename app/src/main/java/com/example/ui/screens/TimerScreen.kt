package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.data.SubjectItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimerScreen(
    timerState: FocusTimerState,
    subjects: List<SubjectItem>,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onResetTimer: () -> Unit,
    onSetMode: (String, Int) -> Unit,
    onUpdateGoal: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var goalText by remember { mutableStateOf<String>(timerState.focusGoal) }
    var selectedSubject by remember { mutableStateOf<String>(timerState.currentSubject) }
    var autoStartBreaks by remember { mutableStateOf(true) }
    var playAmbient by remember { mutableStateOf(false) }

    val modes = listOf(
        Pair("Pomodoro", 25),
        Pair("Short Study", 50),
        Pair("Deep Focus", 90),
        Pair("Custom Mode", 15)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Mode Selector Pills
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF1F5F9))
                .padding(4.dp)
        ) {
            modes.forEach { (modeName, mins) ->
                val isSelected = timerState.modeName == modeName
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) StudyHubTeal else Color.Transparent)
                        .clickable { onSetMode(modeName, mins) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "$modeName (${mins}m)",
                        color = if (isSelected) Color.White else StudyHubTextSecondary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Two main panels
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Configuration Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_timer_config"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Focus Session Configuration", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = StudyHubTextPrimary)

                    Text("Active Subject", fontSize = 12.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, StudyHubBorder, RoundedCornerShape(8.dp))
                            .background(Color(0xFFFAFAFA))
                            .padding(12.dp)
                    ) {
                        Text(selectedSubject, fontSize = 13.sp, color = StudyHubTextPrimary)
                    }

                    Text("Specific Focus Goal", fontSize = 12.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = goalText,
                        onValueChange = {
                            goalText = it
                            onUpdateGoal(selectedSubject, it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp),
                        maxLines = 3
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = autoStartBreaks,
                            onCheckedChange = { autoStartBreaks = it },
                            colors = CheckboxDefaults.colors(checkedColor = StudyHubTeal)
                        )
                        Text("Auto-start breaks after focus", fontSize = 12.sp, color = StudyHubTextPrimary)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = playAmbient,
                            onCheckedChange = { playAmbient = it },
                            colors = CheckboxDefaults.colors(checkedColor = StudyHubTeal)
                        )
                        Text("Play ambient white noise / lo-fi audio", fontSize = 12.sp, color = StudyHubTextPrimary)
                    }

                    Button(
                        onClick = {
                            if (timerState.isRunning) onPauseTimer() else onStartTimer()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("btn_toggle_session")
                    ) {
                        Text(if (timerState.isRunning) "Pause Session" else "Start Session", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Circular Timer Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_timer_display"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FOCUS MODE • ${timerState.modeName.uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = StudyHubTeal,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Large Circular Progress Ring
                    val progressRatio = timerState.remainingSeconds.toFloat() / timerState.totalSeconds.toFloat()
                    val minutes = timerState.remainingSeconds / 60
                    val seconds = timerState.remainingSeconds % 60
                    val timeString = String.format("%02d:%02d", minutes, seconds)

                    Box(
                        modifier = Modifier.size(190.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val stroke = 12.dp.toPx()
                            drawArc(
                                color = Color(0xFFE2E8F0),
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = stroke, cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = StudyHubTeal,
                                startAngle = -90f,
                                sweepAngle = 360f * progressRatio,
                                useCenter = false,
                                style = Stroke(width = stroke, cap = StrokeCap.Round)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = timeString,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudyHubTextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (timerState.isRunning) "In Progress" else "Paused",
                                fontSize = 12.sp,
                                color = if (timerState.isRunning) AccentGreen else StudyHubTextSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Current Subject: ${timerState.currentSubject}",
                        fontSize = 12.sp,
                        color = StudyHubTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Timer Action Buttons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            onClick = onResetTimer,
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .border(1.dp, StudyHubBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = StudyHubTextSecondary)
                        }

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(StudyHubTeal)
                                .clickable {
                                    if (timerState.isRunning) onPauseTimer() else onStartTimer()
                                }
                                .testTag("btn_timer_play_pause"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (timerState.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (timerState.isRunning) "Pause" else "Play",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        IconButton(
                            onClick = onResetTimer,
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .border(1.dp, StudyHubBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.SkipNext, contentDescription = "Skip", tint = StudyHubTextSecondary)
                        }
                    }
                }
            }
        }

        // Today's Focus Stats Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("card_focus_stats"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Today's Focus Status", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = StudyHubTextPrimary)

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("2.5 hrs", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = StudyHubTeal)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Focus Time", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("3 / 4", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Sessions", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("12 Days", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Streak", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}
