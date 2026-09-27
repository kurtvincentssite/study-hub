package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.AssessmentItem
import com.example.data.SubjectItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenLight
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTealLight
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun GradesScreen(
    subjects: List<SubjectItem>,
    assessments: List<AssessmentItem>,
    onAddAssessment: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubjectFilter by remember { mutableStateOf("All") }
    var selectedTypeFilter by remember { mutableStateOf("All") }
    var expandedSubjectId by remember { mutableStateOf<String?>(null) }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header: Title + Subtitle and Add Button (Responsive)
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isCompact = maxWidth < 480.dp

                if (isCompact) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "Academic Grades",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Fall Semester 2026 • 5 Enrolled Courses",
                                fontSize = 12.sp,
                                color = onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = onAddAssessment,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("btn_add_assessment")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("+ Add Assessment", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Academic Grades",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Fall Semester 2026 • 5 Enrolled Courses",
                                fontSize = 12.sp,
                                color = onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = onAddAssessment,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier.testTag("btn_add_assessment")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Assessment", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            // Filter Pills Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Active semester chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(StudyHubTeal)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Fall 2026 (Active)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // Subject filter with DropdownMenu
                var subjectMenuExpanded by remember { mutableStateOf(false) }
                val subjectFilterOptions = listOf("All", "Web Dev", "Database", "Mobile", "Info Assurance", "Ethical Computing")
                Box {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.dp, outlineColor, RoundedCornerShape(20.dp))
                            .background(surfaceColor)
                            .clickable { subjectMenuExpanded = true }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                            .testTag("dropdown_grade_subject")
                    ) {
                        Text("Subject: $selectedSubjectFilter ▾", fontSize = 11.sp, color = onSurface, fontWeight = FontWeight.Medium)
                    }

                    androidx.compose.material3.DropdownMenu(
                        expanded = subjectMenuExpanded,
                        onDismissRequest = { subjectMenuExpanded = false },
                        modifier = Modifier.background(surfaceColor)
                    ) {
                        subjectFilterOptions.forEach { opt ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text(
                                        text = opt,
                                        fontSize = 12.sp,
                                        fontWeight = if (opt == selectedSubjectFilter) FontWeight.Bold else FontWeight.Normal,
                                        color = if (opt == selectedSubjectFilter) StudyHubTeal else onSurface
                                    )
                                },
                                onClick = {
                                    selectedSubjectFilter = opt
                                    subjectMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Type filter with DropdownMenu
                var typeMenuExpanded by remember { mutableStateOf(false) }
                val typeFilterOptions = listOf("All", "Quiz", "Lab", "Project", "Exam")
                Box {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.dp, outlineColor, RoundedCornerShape(20.dp))
                            .background(surfaceColor)
                            .clickable { typeMenuExpanded = true }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                            .testTag("dropdown_grade_type")
                    ) {
                        Text("Type: $selectedTypeFilter ▾", fontSize = 11.sp, color = onSurface, fontWeight = FontWeight.Medium)
                    }

                    androidx.compose.material3.DropdownMenu(
                        expanded = typeMenuExpanded,
                        onDismissRequest = { typeMenuExpanded = false },
                        modifier = Modifier.background(surfaceColor)
                    ) {
                        typeFilterOptions.forEach { opt ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text(
                                        text = opt,
                                        fontSize = 12.sp,
                                        fontWeight = if (opt == selectedTypeFilter) FontWeight.Bold else FontWeight.Normal,
                                        color = if (opt == selectedTypeFilter) StudyHubTeal else onSurface
                                    )
                                },
                                onClick = {
                                    selectedTypeFilter = opt
                                    typeMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 4 Summary Stat Cards in 2x2 Grid (Exact Match to Design)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AcademicStatTile(
                        icon = Icons.Default.Security,
                        iconBg = Color(0xFFCCFBF1),
                        iconTint = StudyHubTeal,
                        title = "OVERALL AVG",
                        value = "88.5%",
                        subtext = "+1.4% MoM",
                        subtextColor = AccentGreen,
                        modifier = Modifier.weight(1f)
                    )
                    AcademicStatTile(
                        icon = Icons.Default.Info,
                        iconBg = Color(0xFFE0F2FE),
                        iconTint = Color(0xFF0284C7),
                        title = "ACADEMIC GPA",
                        value = "3.45",
                        subtext = "Scale of 4.0",
                        subtextColor = onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AcademicStatTile(
                        icon = Icons.Default.CheckCircle,
                        iconBg = Color(0xFFF3E8FF),
                        iconTint = Color(0xFF9333EA),
                        title = "COMPLETED",
                        value = "23",
                        subtext = "Across 5 Subjects",
                        subtextColor = onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    AcademicStatTile(
                        icon = Icons.AutoMirrored.Filled.TrendingUp,
                        iconBg = Color(0xFFDCFCE7),
                        iconTint = AccentGreen,
                        title = "HIGHEST GRADE",
                        value = "96%",
                        subtext = "Database Mgmt",
                        subtextColor = onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Weekly Study Hours Bar Chart Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_weekly_study_hours"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Weekly Study Hours", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = onSurface)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Total: 24.5 hrs • Goal: 30 hrs", fontSize = 11.sp, color = onSurfaceVariant)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFE6FFFA))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("81% Goal Met", fontSize = 10.sp, color = StudyHubTeal, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // 7-day Bar Chart
                    val dailyHours = listOf(
                        Triple("Mon", 0.5f, false),
                        Triple("Tue", 4.5f, false),
                        Triple("Wed", 6.0f, true), // Active/Highlighted
                        Triple("Thu", 7.0f, false),
                        Triple("Fri", 4.0f, false),
                        Triple("Sat", 1.5f, false),
                        Triple("Sun", 1.0f, false)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        dailyHours.forEach { (day, hours, isHighlighted) ->
                            val maxHours = 8.0f
                            val barHeightRatio = (hours / maxHours).coerceIn(0.08f, 1f)

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (hours > 0) "${hours}h" else "",
                                    fontSize = 9.sp,
                                    color = if (isHighlighted) StudyHubTeal else onSurfaceVariant,
                                    fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height((60 * barHeightRatio).dp)
                                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                        .background(if (isHighlighted) StudyHubTeal else Color(0xFFCBD5E1))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day,
                                    fontSize = 10.sp,
                                    color = if (isHighlighted) StudyHubTeal else onSurfaceVariant,
                                    fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Section Header: COURSE PERFORMANCE & BREAKDOWN
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "COURSE PERFORMANCE & BREAKDOWN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = "${subjects.size} Subjects Total",
                    fontSize = 11.sp,
                    color = onSurfaceVariant
                )
            }

            // Course Performance Cards
            val detailedCourses = listOf(
                DetailedCourseData(
                    code = "CS-401",
                    instructor = "Prof. Garcia",
                    title = "Advanced Web Development",
                    runningAvg = "91.2%",
                    colorHex = 0xFF2563EB,
                    assessments = listOf(
                        AssessmentEntry("Quiz 1: Basic DOM", "45/50", "90%", "Quiz • Weight 10% • Sep 05, 2026", "9.0% out"),
                        AssessmentEntry("Laboratory 1: CSS Layouts", "88/100", "88%", "Lab • Weight 15% • Sep 11, 2026", "13.2% out"),
                        AssessmentEntry("Midterm Project: React App", "96/100", "96%", "Project • Weight 25% • Sep 20, 2026", "24.0% out")
                    )
                ),
                DetailedCourseData(
                    code = "CS-402",
                    instructor = "Prof. Reyes",
                    title = "Database Management",
                    runningAvg = "88.0%",
                    colorHex = 0xFF0D9488,
                    assessments = listOf(
                        AssessmentEntry("Quiz 1: Relational Algebra", "20/20", "100%", "Quiz • Weight 10% • Sep 06, 2026", "10.0% out"),
                        AssessmentEntry("Database Design: Phase 1", "85/100", "85%", "Project • Weight 20% • Sep 17, 2026", "17.0% out")
                    )
                ),
                DetailedCourseData(
                    code = "CS-405",
                    instructor = "Prof. Santos",
                    title = "Mobile Computing",
                    runningAvg = "87.5%",
                    colorHex = 0xFF8B5CF6,
                    assessments = emptyList(),
                    extraCountText = "2 Assessments logged"
                ),
                DetailedCourseData(
                    code = "CS-409",
                    instructor = "Prof. Cruz",
                    title = "Information Assurance",
                    runningAvg = "85.4%",
                    colorHex = 0xFFF97316,
                    assessments = emptyList(),
                    extraCountText = "1 Assessment logged"
                ),
                DetailedCourseData(
                    code = "CS-411",
                    instructor = "Prof. Lim",
                    title = "Social & Ethical Computing",
                    runningAvg = "90.0%",
                    colorHex = 0xFFEC4899,
                    assessments = emptyList(),
                    extraCountText = "1 Assessment logged"
                )
            )

            detailedCourses.forEach { course ->
                CourseGradeCard(
                    course = course,
                    isExpanded = expandedSubjectId == course.code,
                    onToggleExpand = {
                        expandedSubjectId = if (expandedSubjectId == course.code) null else course.code
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onAddAssessment,
            containerColor = StudyHubTeal,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("fab_add_assessment")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Assessment", modifier = Modifier.size(24.dp))
        }
    }
}

private data class DetailedCourseData(
    val code: String,
    val instructor: String,
    val title: String,
    val runningAvg: String,
    val colorHex: Long,
    val assessments: List<AssessmentEntry>,
    val extraCountText: String? = null
)

private data class AssessmentEntry(
    val title: String,
    val scoreText: String,
    val percentText: String,
    val subtitle: String,
    val weightText: String
)

@Composable
private fun AcademicStatTile(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    value: String,
    subtext: String,
    subtextColor: Color,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.5.sp
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(13.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtext,
                fontSize = 10.sp,
                color = subtextColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CourseGradeCard(
    course: DetailedCourseData,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val accentColor = Color(course.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("course_grade_card_${course.code}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header: Code Chip + Instructor + Running Avg
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = course.code,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    }
                    Text(
                        text = course.instructor,
                        fontSize = 11.sp,
                        color = onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "RUNNING AVG: ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = onSurfaceVariant
                    )
                    Text(
                        text = course.runningAvg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Course Title
            Text(
                text = course.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Accent color progress line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFE2E8F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(accentColor)
                )
            }

            // Recorded assessments or summary row
            if (course.assessments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "RECORDED ASSESSMENTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurfaceVariant,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    course.assessments.forEach { assess ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .border(0.5.dp, outlineColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = assess.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = onSurface
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = assess.scoreText,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = onSurface
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "(${assess.percentText})",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = accentColor
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = assess.subtitle,
                                        fontSize = 10.sp,
                                        color = onSurfaceVariant
                                    )
                                    Text(
                                        text = assess.weightText,
                                        fontSize = 10.sp,
                                        color = onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            } else if (course.extraCountText != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onToggleExpand),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = course.extraCountText,
                        fontSize = 11.sp,
                        color = onSurfaceVariant
                    )
                    Text(
                        text = "View breakdown →",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = StudyHubTeal
                    )
                }
            }
        }
    }
}
