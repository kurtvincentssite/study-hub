package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SubjectItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenLight
import com.example.ui.theme.AccentGreenText
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProgressScreen(
    subjects: List<SubjectItem>,
    modifier: Modifier = Modifier
) {
    var selectedTimeframe by remember { mutableStateOf("This Semester") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Timeframe Selector Tabs
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF1F5F9))
                .padding(4.dp)
        ) {
            listOf("Today", "This Week", "This Month", "This Semester").forEach { timeframe ->
                val isSelected = selectedTimeframe == timeframe
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) StudyHubTeal else Color.Transparent)
                        .clickable { selectedTimeframe = timeframe }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = timeframe,
                        color = if (isSelected) Color.White else StudyHubTextSecondary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // 4 Summary Metrics Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard("Overall Grade", "88.5%", "+1.4% vs last term", Modifier.width(210.dp))
            MetricCard("GPA Equivalent", "3.45", "Dean's List Standing", Modifier.width(210.dp))
            MetricCard("Active Subjects", "5 / 0", "In Progress / Completed", Modifier.width(210.dp))
            MetricCard("Study Streak", "12 Days", "Personal Best Record", Modifier.width(210.dp))
        }

        // Charts Section
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Chart 1: Weekly Study Hours
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("chart_weekly_hours"),
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
                        Text("Weekly Study Hours", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = StudyHubTextPrimary)
                        Text("Target: 30 hrs", fontSize = 11.sp, color = StudyHubTextSecondary)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        listOf(
                            Triple("Mon", 3.5f, false),
                            Triple("Tue", 4.2f, false),
                            Triple("Wed", 5.0f, true),
                            Triple("Thu", 0.8f, false),
                            Triple("Fri", 1.2f, false),
                            Triple("Sat", 0.5f, false),
                            Triple("Sun", 0.5f, false)
                        ).forEach { (day, hrs, isActive) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${hrs}h", fontSize = 9.sp, color = StudyHubTextSecondary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(((hrs / 6f) * 90).dp.coerceIn(12.dp, 90.dp))
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isActive) StudyHubTeal else Color(0xFF94A3B8))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    day,
                                    fontSize = 11.sp,
                                    color = if (isActive) StudyHubTeal else StudyHubTextSecondary,
                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // Chart 2: Subject Grades vs Target
            Card(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 320.dp)
                    .testTag("chart_subject_grades"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Subject Grades vs Target", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = StudyHubTextPrimary)

                    Spacer(modifier = Modifier.height(16.dp))

                    subjects.forEach { subj ->
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(subj.title, fontSize = 12.sp, color = StudyHubTextPrimary, fontWeight = FontWeight.Medium)
                                Text("${subj.averageGrade}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(subj.colorHex))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { (subj.averageGrade / 100.0).toFloat() },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                color = Color(subj.colorHex),
                                trackColor = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }
        }

        // Table: Subject Performance Directory
        Card(
            modifier = Modifier.fillMaxWidth().testTag("performance_directory_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Subject Performance Directory", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = StudyHubTextPrimary)

                Spacer(modifier = Modifier.height(14.dp))

                subjects.forEach { subj ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, StudyHubBorder, RoundedCornerShape(8.dp))
                            .background(Color(0xFFFAFAFA))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(subj.code, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(subj.colorHex), modifier = Modifier.width(70.dp))
                            Text(subj.title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = StudyHubTextPrimary, modifier = Modifier.weight(2f))
                            Text("${subj.units} Units", fontSize = 11.sp, color = StudyHubTextSecondary, modifier = Modifier.weight(1f))
                            Text("${subj.averageGrade}%", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = StudyHubTextPrimary, modifier = Modifier.weight(1f))
                            Text("${subj.studyTimeHours} hrs", fontSize = 11.sp, color = StudyHubTeal, modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AccentGreenLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("On Track", fontSize = 10.sp, color = AccentGreenText, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 11.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = StudyHubTextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, fontSize = 10.sp, color = StudyHubTeal)
        }
    }
}
