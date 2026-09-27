package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.example.data.AssessmentItem
import com.example.data.ScheduleItem
import com.example.data.SubjectItem
import com.example.data.TaskItem
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary
import java.util.UUID

@Composable
fun AddSubjectDialog(
    onDismiss: () -> Unit,
    onConfirm: (SubjectItem) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var instructor by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("Room 301") }
    var units by remember { mutableIntStateOf(3) }
    var selectedColor by remember { mutableLongStateOf(0xFF2563EB) }

    val colors = listOf(0xFF2563EB, 0xFF7C3AED, 0xFF0D9488, 0xFFEA580C, 0xFFDB2777)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Subject",
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Course Code (e.g. CS-401)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_subject_code")
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Subject Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_subject_title")
                )
                OutlinedTextField(
                    value = instructor,
                    onValueChange = { instructor = it },
                    label = { Text("Instructor (e.g. Prof. Garcia)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_subject_instructor")
                )
                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text("Room / Location") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Theme Accent Color", fontSize = 12.sp, color = StudyHubTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    colors.forEach { col ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(col))
                                .clickable { selectedColor = col }
                                .padding(2.dp)
                        ) {
                            if (selectedColor == col) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .align(Alignment.Center)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            SubjectItem(
                                id = UUID.randomUUID().toString(),
                                code = if (code.isBlank()) "CS-101" else code,
                                title = title,
                                instructor = if (instructor.isBlank()) "Prof. Staff" else instructor,
                                room = room,
                                units = units,
                                averageGrade = 90.0,
                                studyTimeHours = 0.0,
                                classesPerWeek = 2,
                                colorHex = selectedColor
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                modifier = Modifier.testTag("btn_confirm_add_subject")
            ) {
                Text("Save Subject")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddTaskDialog(
    subjects: List<SubjectItem>,
    onDismiss: () -> Unit,
    onConfirm: (TaskItem) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf(subjects.firstOrNull()?.title ?: "Database Management") }
    var dueDate by remember { mutableStateOf("Today, 11:59 PM") }
    var priority by remember { mutableStateOf("Medium") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Task",
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_task_title")
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Date (e.g. Sep 28, 2026)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Priority Level", fontSize = 12.sp, color = StudyHubTextSecondary)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    listOf("High", "Medium", "Low").forEach { p ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = priority == p, onClick = { priority = p })
                            Text(p, fontSize = 13.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            TaskItem(
                                id = UUID.randomUUID().toString(),
                                title = title,
                                subject = selectedSubject,
                                description = description,
                                dueDate = dueDate,
                                priority = priority,
                                isCompleted = false,
                                progress = 0,
                                colorHex = subjects.find { it.title == selectedSubject }?.colorHex ?: 0xFF0D9488
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                modifier = Modifier.testTag("btn_confirm_add_task")
            ) {
                Text("Add Task")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddScheduleDialog(
    subjects: List<SubjectItem>,
    onDismiss: () -> Unit,
    onConfirm: (ScheduleItem) -> Unit
) {
    var selectedSubject by remember { mutableStateOf(subjects.firstOrNull()?.title ?: "Database Management") }
    var day by remember { mutableStateOf("Monday") }
    var time by remember { mutableStateOf("1:00 PM - 2:30 PM") }
    var room by remember { mutableStateOf("Room 301") }
    var instructor by remember { mutableStateOf("Prof. Garcia") }
    var type by remember { mutableStateOf("Lecture") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Class Schedule",
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = selectedSubject,
                    onValueChange = { selectedSubject = it },
                    label = { Text("Subject Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_schedule_subject")
                )
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text("Day of Week (e.g. Monday)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Class Time (e.g. 8:00 AM - 9:30 AM)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text("Room / Laboratory") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = instructor,
                    onValueChange = { instructor = it },
                    label = { Text("Instructor") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedSubject.isNotBlank()) {
                        onConfirm(
                            ScheduleItem(
                                id = UUID.randomUUID().toString(),
                                subject = selectedSubject,
                                day = day,
                                time = time,
                                room = room,
                                instructor = instructor,
                                type = type,
                                status = "Upcoming",
                                colorHex = subjects.find { it.title == selectedSubject }?.colorHex ?: 0xFF0D9488
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal)
            ) {
                Text("Add Schedule")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddGradeDialog(
    subjects: List<SubjectItem>,
    onDismiss: () -> Unit,
    onConfirm: (AssessmentItem) -> Unit
) {
    var subject by remember { mutableStateOf(subjects.firstOrNull()?.title ?: "Advanced Web Development") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Quiz") }
    var score by remember { mutableDoubleStateOf(45.0) }
    var maxScore by remember { mutableDoubleStateOf(50.0) }
    var weight by remember { mutableDoubleStateOf(10.0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Assessment Grade",
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Assessment Name (e.g. Quiz 3)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = score.toString(),
                        onValueChange = { score = it.toDoubleOrNull() ?: score },
                        label = { Text("Score") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = maxScore.toString(),
                        onValueChange = { maxScore = it.toDoubleOrNull() ?: maxScore },
                        label = { Text("Max") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && maxScore > 0) {
                        val pct = (score / maxScore) * 100.0
                        val weighted = (pct * weight) / 100.0
                        onConfirm(
                            AssessmentItem(
                                id = UUID.randomUUID().toString(),
                                subject = subject,
                                name = name,
                                type = type,
                                score = score,
                                maxScore = maxScore,
                                percentage = pct,
                                weight = weight,
                                weightedScore = weighted,
                                date = "Today"
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal)
            ) {
                Text("Record Grade")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun HelpGuideDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "StudyHub Academic Suite Guide",
                fontWeight = FontWeight.Bold,
                color = StudyHubTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Welcome to StudyHub! This app reproduces the exact unified academic dashboard layout from the web experience:",
                    fontSize = 13.sp,
                    color = StudyHubTextSecondary
                )
                Text(
                    text = "• Navigation: Access all 12 suite modules (Dashboard, Subjects, Calendar, Schedule, Timetable, Tasks, Grades, Study Focus, Timer, Progress, Notifications, Profile, Settings) via the left sidebar or top menu.",
                    fontSize = 12.sp,
                    color = StudyHubTextPrimary
                )
                Text(
                    text = "• Quick Actions: Use the action buttons at the bottom of the dashboard to quickly add subjects, assignments, class schedules, or launch a focus timer session.",
                    fontSize = 12.sp,
                    color = StudyHubTextPrimary
                )
                Text(
                    text = "• Focus Timer: Supports Pomodoro (25/5), Deep Focus (90/15), and active study tracking with live countdown.",
                    fontSize = 12.sp,
                    color = StudyHubTextPrimary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal)
            ) {
                Text("Got It")
            }
        }
    )
}
