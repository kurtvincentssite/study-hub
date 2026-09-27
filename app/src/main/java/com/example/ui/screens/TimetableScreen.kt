package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentRed
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun TimetableScreen(
    onAddEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Navigation Bar
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 540.dp

            if (isCompact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous week", tint = StudyHubTextPrimary)
                        }
                        Text(
                            text = "September 21 - 27, 2026",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = StudyHubTextPrimary
                        )
                        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next week", tint = StudyHubTextPrimary)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {},
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Text("Export PDF", color = StudyHubTextPrimary, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onAddEvent,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Event", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous week", tint = StudyHubTextPrimary)
                        }
                        Text(
                            text = "September 21 - 27, 2026",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = StudyHubTextPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next week", tint = StudyHubTextPrimary)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
                        ) {
                            Text("Export PDF", color = StudyHubTextPrimary, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onAddEvent,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Event", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Timetable Grid Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("timetable_grid_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.width(860.dp).padding(16.dp)) {
                    // Header Row of Days
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, StudyHubBorder, RoundedCornerShape(8.dp))
                            .background(Color(0xFFFAFAFA))
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TIME", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = StudyHubTextSecondary, modifier = Modifier.width(70.dp).padding(start = 12.dp))
                        DayHeaderCell("MONDAY", "21", false, Modifier.weight(1f))
                        DayHeaderCell("TUESDAY", "22", false, Modifier.weight(1f))
                        DayHeaderCell("WEDNESDAY", "23", true, Modifier.weight(1f))
                        DayHeaderCell("THURSDAY", "24", false, Modifier.weight(1f))
                        DayHeaderCell("FRIDAY", "25", false, Modifier.weight(1f))
                        DayHeaderCell("SATURDAY", "26", false, Modifier.weight(1f))
                        DayHeaderCell("SUNDAY", "27", false, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Time rows
                    val hours = listOf("7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM")

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            hours.forEach { timeStr ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .border(0.5.dp, Color(0xFFF1F5F9)),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = timeStr,
                                        fontSize = 10.sp,
                                        color = StudyHubTextSecondary,
                                        modifier = Modifier.width(70.dp).padding(start = 8.dp, top = 4.dp)
                                    )
                                    repeat(7) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxSize()
                                                .border(0.5.dp, Color(0xFFF8FAFC))
                                        )
                                    }
                                }
                            }
                        }

                        // Event Blocks Placed on Grid
                        // Mon 8:00 AM Database Management
                        TimetableEventBlock(
                            subject = "Database Management",
                            room = "Room 301 • Prof. Reyes",
                            colorHex = 0xFF0D9488,
                            offsetX = 70.dp + (112.dp * 0),
                            offsetY = 52.dp * 1,
                            height = 70.dp
                        )
                        // Wed 8:00 AM Database Management
                        TimetableEventBlock(
                            subject = "Database Management",
                            room = "Room 301 • Prof. Reyes",
                            colorHex = 0xFF0D9488,
                            offsetX = 70.dp + (112.dp * 2),
                            offsetY = 52.dp * 1,
                            height = 70.dp
                        )
                        // Tue 10:00 AM Mobile Computing
                        TimetableEventBlock(
                            subject = "Mobile Computing",
                            room = "Room 205 • Prof. Santos",
                            colorHex = 0xFF7C3AED,
                            offsetX = 70.dp + (112.dp * 1),
                            offsetY = 52.dp * 3,
                            height = 70.dp
                        )
                        // Thu 10:00 AM Mobile Computing
                        TimetableEventBlock(
                            subject = "Mobile Computing",
                            room = "Room 205 • Prof. Santos",
                            colorHex = 0xFF7C3AED,
                            offsetX = 70.dp + (112.dp * 3),
                            offsetY = 52.dp * 3,
                            height = 70.dp
                        )
                        // Mon 1:00 PM Web Dev
                        TimetableEventBlock(
                            subject = "Advanced Web Dev",
                            room = "Room 301 • Prof. Garcia",
                            colorHex = 0xFF2563EB,
                            offsetX = 70.dp + (112.dp * 0),
                            offsetY = 52.dp * 6,
                            height = 70.dp
                        )
                        // Wed 1:00 PM Web Dev
                        TimetableEventBlock(
                            subject = "Advanced Web Dev",
                            room = "Room 301 • Prof. Garcia",
                            colorHex = 0xFF2563EB,
                            offsetX = 70.dp + (112.dp * 2),
                            offsetY = 52.dp * 6,
                            height = 70.dp
                        )
                        // Fri 9:00 AM Social & Ethical
                        TimetableEventBlock(
                            subject = "Social & Ethical Computing",
                            room = "Room 108 • Prof. Lim",
                            colorHex = 0xFFDB2777,
                            offsetX = 70.dp + (112.dp * 4),
                            offsetY = 52.dp * 2,
                            height = 70.dp
                        )
                        // Thu 3:00 PM Info Assurance
                        TimetableEventBlock(
                            subject = "Information Assurance",
                            room = "Lab 2 • Prof. Cruz",
                            colorHex = 0xFFEA580C,
                            offsetX = 70.dp + (112.dp * 3),
                            offsetY = 52.dp * 8,
                            height = 70.dp
                        )

                        // Red horizontal time indicator line (at 10:00 AM, offsetY ~ 156.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = 60.dp, y = 160.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(AccentRed)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(AccentRed)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayHeaderCell(day: String, date: String, isToday: Boolean, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = day,
            fontSize = 10.sp,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
            color = if (isToday) StudyHubTeal else StudyHubTextSecondary
        )
        Text(
            text = date,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isToday) StudyHubTeal else StudyHubTextPrimary
        )
    }
}

@Composable
private fun TimetableEventBlock(
    subject: String,
    room: String,
    colorHex: Long,
    offsetX: androidx.compose.ui.unit.Dp,
    offsetY: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp
) {
    Box(
        modifier = Modifier
            .offset(x = offsetX + 4.dp, y = offsetY + 2.dp)
            .width(104.dp)
            .height(height)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(colorHex).copy(alpha = 0.12f))
            .border(1.dp, Color(colorHex).copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .padding(6.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(14.dp)
                    .background(Color(colorHex))
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subject,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary,
                maxLines = 2
            )
            Text(
                text = room,
                fontSize = 8.sp,
                color = StudyHubTextSecondary,
                maxLines = 1
            )
        }
    }
}
