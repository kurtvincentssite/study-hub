package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SubjectItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubjectsScreen(
    subjects: List<SubjectItem>,
    onAddSubject: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStatus by remember { mutableStateOf("Active") }
    var selectedSemester by remember { mutableStateOf("All") }

    val statusOptions = listOf("All", "Active", "Completed", "Archived")
    val semesterOptions = listOf("All", "1st Sem 2026-2027", "2nd Sem 2025-2026", "Summer 2026")

    // Real filtering based on dropdown selections
    val filteredSubjects = subjects.filter { subject ->
        val matchesStatus = if (selectedStatus == "All") true else subject.status.equals(selectedStatus, ignoreCase = true)
        val matchesSemester = if (selectedSemester == "All") true else subject.semester.equals(selectedSemester, ignoreCase = true)
        matchesStatus && matchesSemester
    }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Top Toolbar with Working Dropdowns & Add Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("subjects_toolbar"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                val isCompact = maxWidth < 520.dp

                if (isCompact) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Dropdown Filters Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilterDropdownButton(
                                label = "Status",
                                currentSelection = selectedStatus,
                                options = statusOptions,
                                onSelect = { selectedStatus = it },
                                testTag = "dropdown_status_filter"
                            )

                            FilterDropdownButton(
                                label = "Semester",
                                currentSelection = selectedSemester,
                                options = semesterOptions,
                                onSelect = { selectedSemester = it },
                                testTag = "dropdown_semester_filter"
                            )
                        }

                        // Add Subject Button
                        Button(
                            onClick = onAddSubject,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("btn_add_subject")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Subject", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilterDropdownButton(
                                label = "Status",
                                currentSelection = selectedStatus,
                                options = statusOptions,
                                onSelect = { selectedStatus = it },
                                testTag = "dropdown_status_filter"
                            )

                            FilterDropdownButton(
                                label = "Semester",
                                currentSelection = selectedSemester,
                                options = semesterOptions,
                                onSelect = { selectedSemester = it },
                                testTag = "dropdown_semester_filter"
                            )
                        }

                        Button(
                            onClick = onAddSubject,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier
                                .height(44.dp)
                                .testTag("btn_add_subject")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Subject", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Summary Bar: Shows active filter status & subject count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Showing ${filteredSubjects.size} of ${subjects.size} subjects",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = onSurfaceVariant
            )

            if (selectedStatus != "All" || selectedSemester != "All") {
                Text(
                    text = "Reset filters",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = StudyHubTeal,
                    modifier = Modifier
                        .clickable {
                            selectedStatus = "All"
                            selectedSemester = "All"
                        }
                        .padding(4.dp)
                        .testTag("btn_reset_filters")
                )
            }
        }

        // Subjects Grid or Empty State
        if (filteredSubjects.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .testTag("subjects_empty_state"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF1F5F9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.FilterListOff,
                            contentDescription = null,
                            tint = onSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "No subjects match your filter",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Status: \"$selectedStatus\" • Semester: \"$selectedSemester\"",
                        fontSize = 12.sp,
                        color = onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {
                            selectedStatus = "All"
                            selectedSemester = "All"
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal)
                    ) {
                        Text("Show All Subjects", color = StudyHubTeal, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }
                }
            }
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val isCompact = maxWidth < 600.dp

                if (isCompact) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        filteredSubjects.forEach { subject ->
                            SubjectCard(
                                subject = subject,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                } else {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        maxItemsInEachRow = 3,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        filteredSubjects.forEach { subject ->
                            SubjectCard(
                                subject = subject,
                                modifier = Modifier
                                    .weight(1f)
                                    .widthIn(min = 260.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

/**
 * Interactive filter button with Material 3 DropdownMenu
 */
@Composable
private fun FilterDropdownButton(
    label: String,
    currentSelection: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                .background(surfaceColor)
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 7.dp)
                .testTag(testTag)
        ) {
            Text(
                text = "$label: $currentSelection",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = onSurface
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "$label dropdown",
                tint = onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(surfaceColor)
                .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
        ) {
            options.forEach { option ->
                val isSelected = option == currentSelection
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = option,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) StudyHubTeal else onSurface
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = StudyHubTeal,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    modifier = Modifier.testTag("${testTag}_option_$option")
                )
            }
        }
    }
}

@Composable
private fun SubjectCard(
    subject: SubjectItem,
    modifier: Modifier = Modifier
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val statusBadgeBg = when (subject.status.lowercase()) {
        "active" -> Color(0xFFEFF6FF)
        "completed" -> Color(0xFFDCFCE7)
        else -> Color(0xFFF1F5F9)
    }
    val statusBadgeColor = when (subject.status.lowercase()) {
        "active" -> Color(0xFF2563EB)
        "completed" -> AccentGreen
        else -> Color(0xFF64748B)
    }

    Card(
        modifier = modifier.testTag("subject_card_${subject.code}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column {
            // Color accent bar on top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(Color(subject.colorHex))
            )

            Column(modifier = Modifier.padding(18.dp)) {
                // Code and Status Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(subject.colorHex).copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = subject.code,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color(subject.colorHex)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusBadgeBg)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = subject.status,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = statusBadgeColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = subject.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = onSurface,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subject.instructor,
                    fontSize = 12.sp,
                    color = onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Place, contentDescription = null, tint = onSurfaceVariant, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(subject.room, fontSize = 11.sp, color = onSurfaceVariant)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Class, contentDescription = null, tint = onSurfaceVariant, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${subject.units} Units", fontSize = 11.sp, color = onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(outlineColor.copy(alpha = 0.5f))
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Stats footer: Average, Study Time, Classes/Wk
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Average", fontSize = 10.sp, color = onSurfaceVariant)
                        Text("${subject.averageGrade}%", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = onSurface)
                    }
                    Column {
                        Text("Study Time", fontSize = 10.sp, color = onSurfaceVariant)
                        Text("${subject.studyTimeHours} hrs", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = StudyHubTeal)
                    }
                    Column {
                        Text("Classes / Wk", fontSize = 10.sp, color = onSurfaceVariant)
                        Text("${subject.classesPerWeek}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = onSurface)
                    }
                }
            }
        }
    }
}
