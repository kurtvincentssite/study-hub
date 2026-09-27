package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudentProfile
import com.example.data.SubjectItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenLight
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun ProfileScreen(
    profile: StudentProfile,
    subjects: List<SubjectItem>,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Overview") }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // ID Badge Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_student_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val initials = profile.displayAccountName
                        .split(" ")
                        .filter { it.isNotBlank() }
                        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                        .take(2)
                        .joinToString("")
                        .ifEmpty { "SU" }

                    // Avatar in rounded square with teal background
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFCCFBF1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(initials, color = StudyHubTeal, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.displayAccountName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = onSurface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AccentGreenLight)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("Active", fontSize = 10.sp, color = AccentGreen, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))
                        Text("ID: ${profile.studentId}", fontSize = 11.sp, color = onSurfaceVariant, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("${profile.degree} • ${profile.yearLevel}", fontSize = 11.sp, color = onSurfaceVariant)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(profile.email, fontSize = 11.sp, color = onSurfaceVariant)
                    }
                }
            }
        }

        // 4 Profile Stat Metrics - strictly 2x2 grid so cards never cut off horizontally
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatTile(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    iconBg = Color(0xFFEFF6FF),
                    iconTint = Color(0xFF3B82F6),
                    value = "5",
                    label = "Subjects Enrolled",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatTile(
                    icon = Icons.Default.AccessTime,
                    iconBg = Color(0xFFDCFCE7),
                    iconTint = AccentGreen,
                    value = "3.82",
                    label = "Cumulative GPA",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatTile(
                    icon = Icons.Default.HourglassTop,
                    iconBg = Color(0xFFF3E8FF),
                    iconTint = Color(0xFF9333EA),
                    value = "158.5 hrs",
                    label = "Total Study",
                    modifier = Modifier.weight(1f)
                )
                ProfileStatTile(
                    icon = Icons.Default.EmojiEvents,
                    iconBg = Color(0xFFFFEDD5),
                    iconTint = Color(0xFFF97316),
                    value = "Top 5%",
                    label = "Major Rank",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Tabs: Overview & Timeline
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedTab == "Overview") StudyHubTeal else surfaceVariant)
                    .clickable { selectedTab = "Overview" }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Overview",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab == "Overview") Color.White else onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedTab == "Timeline") StudyHubTeal else surfaceVariant)
                    .clickable { selectedTab = "Timeline" }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Timeline",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab == "Timeline") Color.White else onSurfaceVariant
                )
            }
        }

        // Section: Current Enrolled Subjects
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Current Enrolled Subjects",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = onSurface
            )

            val subjectsList = listOf(
                Pair("Advanced Web Development", "CS-401 • Room 301" to 0xFF2563EB),
                Pair("Database Management", "CS-402 • Room 302" to 0xFF0D9488),
                Pair("Mobile Computing", "CS-405 • Room 205" to 0xFF8B5CF6),
                Pair("Information Assurance", "CS-409 • Lab 2" to 0xFFF97316),
                Pair("Social & Ethical Computing", "CS-411 • Room 104" to 0xFFEC4899)
            )

            subjectsList.forEach { (title, meta) ->
                val (details, colorHex) = meta
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(32.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(colorHex))
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = details,
                                fontSize = 11.sp,
                                color = onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Section: Recent Activity & Alerts Timeline
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Recent Activity & Alerts",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = onSurface
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Item 1: Critical Overdue
                    TimelineNode(
                        dotColor = Color(0xFFEF4444),
                        showConnectingLine = true
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFFEF2F2))
                                .border(1.dp, Color(0xFFFCA5A5), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFEF4444))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("CRITICAL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Overdue Task — Ethics Research Paper",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF991B1B)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Submission deadline was yesterday. Submit via academic portal.",
                                    fontSize = 11.sp,
                                    color = Color(0xFF7F1D1D)
                                )
                            }
                        }
                    }

                    // Item 2: Completed study session
                    TimelineNode(
                        dotColor = Color(0xFF3B82F6),
                        showConnectingLine = true
                    ) {
                        Column {
                            Text("Completed study session", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = onSurface)
                            Text("Advanced Web Development (50 min focus)", fontSize = 11.sp, color = onSurfaceVariant)
                        }
                    }

                    // Item 3: Upcoming Class
                    TimelineNode(
                        dotColor = AccentGreen,
                        showConnectingLine = true
                    ) {
                        Column {
                            Text("Upcoming Class — Database Management", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = onSurface)
                            Text("Lecture begins at Room 301 with Prof. Reyes", fontSize = 11.sp, color = onSurfaceVariant)
                        }
                    }

                    // Item 4: Grade Posted
                    TimelineNode(
                        dotColor = StudyHubTeal,
                        showConnectingLine = true
                    ) {
                        Column {
                            Text("Grade Posted — Database Management", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = onSurface)
                            Text("Quiz 2 score released: 92% (High Pass)", fontSize = 11.sp, color = onSurfaceVariant)
                        }
                    }

                    // Item 5: Study Streak
                    TimelineNode(
                        dotColor = Color(0xFFF97316),
                        showConnectingLine = false
                    ) {
                        Column {
                            Text("Study Streak — 12 Days 🔥", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = onSurface)
                            Text("Incredible effort! You've maintained your daily focus streak.", fontSize = 11.sp, color = onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun ProfileStatTile(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    value: String,
    label: String,
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
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = onSurface
                )
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TimelineNode(
    dotColor: Color,
    showConnectingLine: Boolean,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            if (showConnectingLine) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(54.dp)
                        .background(Color(0xFFE2E8F0))
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(modifier = Modifier.weight(1f).padding(bottom = if (showConnectingLine) 16.dp else 4.dp)) {
            content()
        }
    }
}
