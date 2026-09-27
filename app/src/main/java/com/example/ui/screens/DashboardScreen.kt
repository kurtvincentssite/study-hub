package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NavDestination
import com.example.data.ScheduleItem
import com.example.data.TaskItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenLight
import com.example.ui.theme.AccentGreenText
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentOrangeLight
import com.example.ui.theme.AccentOrangeText
import com.example.ui.theme.AccentPink
import com.example.ui.theme.AccentPinkLight
import com.example.ui.theme.AccentPinkText
import com.example.ui.theme.AccentRed
import com.example.ui.theme.AccentRedLight
import com.example.ui.theme.AccentRedText
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.AccentYellowText
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTealLight
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(
    tasks: List<TaskItem>,
    schedules: List<ScheduleItem>,
    onToggleTask: (String) -> Unit,
    onNavigate: (NavDestination) -> Unit,
    onAddSubject: () -> Unit,
    onAddTask: () -> Unit,
    onAddSchedule: () -> Unit,
    onAddGrade: () -> Unit,
    onStartFocus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Greeting Bar
        Column(modifier = Modifier.fillMaxWidth().testTag("greeting_section")) {
            Text(
                text = "Good morning, Student!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Wednesday, September 23, 2026 • 10:24 AM",
                style = MaterialTheme.typography.bodyMedium,
                color = StudyHubTextSecondary,
                fontSize = 13.sp
            )
        }

        // 2. 4 Top Stat Cards Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardStatCard(
                title = "Overall Grade",
                badgeText = "+1.4%",
                badgeBg = AccentGreenLight,
                badgeTextColor = AccentGreenText,
                mainValue = "88.5%",
                subtitle = "Up from 87.1% last week",
                modifier = Modifier.width(220.dp).testTag("stat_overall_grade")
            )
            DashboardStatCard(
                title = "Study Hours",
                badgeText = "Weekly",
                badgeBg = StudyHubTealLight,
                badgeTextColor = StudyHubTeal,
                mainValue = "24.5 hrs",
                subtitle = "Goal: 30 hrs",
                modifier = Modifier.width(220.dp).testTag("stat_study_hours")
            )
            DashboardStatCard(
                title = "Active Subjects",
                badgeText = "Current",
                badgeBg = Color(0xFFF1F5F9),
                badgeTextColor = Color(0xFF475569),
                mainValue = "5",
                subtitle = "1st Sem 2026-2027",
                modifier = Modifier.width(220.dp).testTag("stat_active_subjects")
            )
            DashboardStatCard(
                title = "Upcoming Tasks",
                badgeText = "Action Needed",
                badgeBg = AccentOrangeLight,
                badgeTextColor = AccentOrangeText,
                mainValue = "8",
                subtitle = "3 due within 48 hours",
                modifier = Modifier.width(220.dp).testTag("stat_upcoming_tasks")
            )
        }

        // 3. Middle Section: Today's Schedule & Study Focus Progress
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Today's Schedule Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_todays_schedule"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Today's Schedule",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = StudyHubTextPrimary
                        )
                        TextButton(
                            onClick = { onNavigate(NavDestination.CALENDAR) },
                            modifier = Modifier.testTag("btn_view_full_calendar")
                        ) {
                            Text(
                                text = "View Full Calendar",
                                color = StudyHubTeal,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ScheduleRowItem(
                        dotColor = AccentGreen,
                        title = "Database Management",
                        details = "8:00 AM - 9:30 AM • Room 301 • Prof. Reyes",
                        statusText = "Completed",
                        statusBg = AccentGreenLight,
                        statusTextColor = AccentGreenText
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ScheduleRowItem(
                        dotColor = Color(0xFF3B82F6),
                        title = "Advanced Web Development",
                        details = "1:00 PM - 2:30 PM • Room 301 • Prof. Garcia",
                        statusText = "In Progress",
                        statusBg = Color(0xFFDBEAFE),
                        statusTextColor = Color(0xFF2563EB)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ScheduleRowItem(
                        dotColor = AccentPink,
                        title = "Social and Ethical Computing",
                        details = "4:00 PM - 5:30 PM • Room 108 • Prof. Lim",
                        statusText = "Upcoming",
                        statusBg = AccentPinkLight,
                        statusTextColor = AccentPinkText
                    )
                }
            }

            // Study Focus Progress Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_study_focus_progress"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Study Focus Progress",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = StudyHubTextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Circular Ring
                        Box(
                            modifier = Modifier.size(90.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val strokeWidth = 8.dp.toPx()
                                drawArc(
                                    color = Color(0xFFE2E8F0),
                                    startAngle = -90f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                                drawArc(
                                    color = StudyHubTeal,
                                    startAngle = -90f,
                                    sweepAngle = 270f, // 75%
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "75%",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = StudyHubTextPrimary
                                )
                                Text(
                                    text = "Complete",
                                    fontSize = 10.sp,
                                    color = StudyHubTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(18.dp))

                        Column {
                            Text(
                                text = "Today's Focus Time",
                                fontSize = 12.sp,
                                color = StudyHubTextSecondary
                            )
                            Text(
                                text = "4.5 / 6.0 hrs",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = StudyHubTeal
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "You are on track to hitting your weekly academic goal!",
                                fontSize = 11.sp,
                                color = StudyHubTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Daily Distribution (hrs)",
                        fontSize = 11.sp,
                        color = StudyHubTextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Daily distribution bar chart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        DailyBar("Mon", 3.5f, false)
                        DailyBar("Tue", 4.2f, false)
                        DailyBar("Wed", 5.0f, true) // Active day
                        DailyBar("Thu", 0.8f, false)
                        DailyBar("Fri", 1.2f, false)
                        DailyBar("Sat", 0.5f, false)
                        DailyBar("Sun", 0.5f, false)
                    }
                }
            }
        }

        // 4. Lower Section: Upcoming Tasks & Recent Grades
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Upcoming Tasks Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_upcoming_tasks_overview"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Upcoming Tasks",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = StudyHubTextPrimary
                        )
                        TextButton(
                            onClick = { onNavigate(NavDestination.TASKS) },
                            modifier = Modifier.testTag("btn_view_all_tasks")
                        ) {
                            Text(
                                text = "View All Tasks",
                                color = StudyHubTeal,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TaskOverviewRow(
                        title = "Database Lab 3 Schema Design",
                        subtitle = "Database Management • Due: Today, 11:59 PM",
                        priority = "High",
                        priorityBg = AccentRedLight,
                        priorityTextColor = AccentRedText,
                        isDone = tasks.find { it.title.contains("Database Lab 3") }?.isCompleted ?: false,
                        onCheckedChange = {
                            val t = tasks.find { it.title.contains("Database Lab 3") }
                            if (t != null) onToggleTask(t.id)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TaskOverviewRow(
                        title = "Quiz 3 React Routing",
                        subtitle = "Advanced Web Development • Due: Sep 25, 2:00 PM",
                        priority = "Medium",
                        priorityBg = AccentYellowLight,
                        priorityTextColor = AccentYellowText,
                        isDone = tasks.find { it.title.contains("Quiz 3 React") }?.isCompleted ?: false,
                        onCheckedChange = {
                            val t = tasks.find { it.title.contains("Quiz 3 React") }
                            if (t != null) onToggleTask(t.id)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TaskOverviewRow(
                        title = "Ethics Response Paper",
                        subtitle = "Social and Ethical Computing • Due: Sep 28, 11:59 PM",
                        priority = "Low",
                        priorityBg = AccentGreenLight,
                        priorityTextColor = AccentGreenText,
                        isDone = tasks.find { it.title.contains("Ethics Response") }?.isCompleted ?: true,
                        onCheckedChange = {
                            val t = tasks.find { it.title.contains("Ethics Response") }
                            if (t != null) onToggleTask(t.id)
                        }
                    )
                }
            }

            // Recent Grades Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("card_recent_grades"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Grades",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = StudyHubTextPrimary
                        )
                        TextButton(
                            onClick = { onNavigate(NavDestination.GRADES) },
                            modifier = Modifier.testTag("btn_view_transcript")
                        ) {
                            Text(
                                text = "View Transcript",
                                color = StudyHubTeal,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    RecentGradeRow(
                        title = "Quiz 2 Routing",
                        course = "Advanced Web Development",
                        percent = "92%",
                        score = "92/100"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RecentGradeRow(
                        title = "Midterm Exam",
                        course = "Database Management",
                        percent = "88%",
                        score = "88/100"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RecentGradeRow(
                        title = "Assg 1 Layouts",
                        course = "Mobile Computing",
                        percent = "95%",
                        score = "19/20"
                    )
                }
            }
        }

        // 5. Quick Actions Row
        Card(
            modifier = Modifier.fillMaxWidth().testTag("card_quick_actions"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Quick Actions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = StudyHubTextPrimary
                )

                Spacer(modifier = Modifier.height(14.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onAddSubject,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                        modifier = Modifier.testTag("qa_add_subject")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Subject", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = onAddTask,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                        modifier = Modifier.testTag("qa_add_task")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Task", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = onAddSchedule,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                        modifier = Modifier.testTag("qa_add_schedule")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Schedule", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = onAddGrade,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                        modifier = Modifier.testTag("qa_add_grade")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Grade", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = onStartFocus,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        modifier = Modifier.testTag("qa_start_study_focus")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Study Focus", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = { onNavigate(NavDestination.CALENDAR) },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                        modifier = Modifier.testTag("qa_open_calendar")
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = StudyHubTeal, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Calendar", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardStatCard(
    title: String,
    badgeText: String,
    badgeBg: Color,
    badgeTextColor: Color,
    mainValue: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = StudyHubTextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeBg)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = badgeTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = mainValue,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = StudyHubTextSecondary
            )
        }
    }
}

@Composable
private fun ScheduleRowItem(
    dotColor: Color,
    title: String,
    details: String,
    statusText: String,
    statusBg: Color,
    statusTextColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, StudyHubBorder, RoundedCornerShape(12.dp))
            .background(Color(0xFFFAFAFA))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = StudyHubTextPrimary
                )
                Text(
                    text = details,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = statusText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = statusTextColor
                )
            }
        }
    }
}

@Composable
private fun DailyBar(day: String, hours: Float, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val maxHours = 6f
        val barHeight = ((hours / maxHours) * 54).dp.coerceIn(12.dp, 54.dp)

        Box(
            modifier = Modifier
                .width(32.dp)
                .height(barHeight)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isActive) StudyHubTeal else Color(0xFF94A3B8))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = day,
            fontSize = 10.sp,
            color = if (isActive) StudyHubTeal else StudyHubTextSecondary,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun TaskOverviewRow(
    title: String,
    subtitle: String,
    priority: String,
    priorityBg: Color,
    priorityTextColor: Color,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, StudyHubBorder, RoundedCornerShape(12.dp))
            .background(Color(0xFFFAFAFA))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = StudyHubTeal,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = if (isDone) StudyHubTextSecondary else StudyHubTextPrimary,
                    textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(priorityBg)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = priority,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = priorityTextColor
                )
            }
        }
    }
}

@Composable
private fun RecentGradeRow(
    title: String,
    course: String,
    percent: String,
    score: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, StudyHubBorder, RoundedCornerShape(12.dp))
            .background(Color(0xFFFAFAFA))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = StudyHubTextPrimary
                )
                Text(
                    text = course,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = percent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = StudyHubTextPrimary
                )
                Text(
                    text = score,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary
                )
            }
        }
    }
}
