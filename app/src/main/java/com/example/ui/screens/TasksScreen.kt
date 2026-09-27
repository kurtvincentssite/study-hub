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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.TaskItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenText
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentRed
import com.example.ui.theme.AccentRedLight
import com.example.ui.theme.AccentRedText
import com.example.ui.theme.AccentYellowLight
import com.example.ui.theme.AccentYellowText
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TasksScreen(
    tasks: List<TaskItem>,
    onAddTask: () -> Unit,
    onToggleTask: (String) -> Unit,
    onDeleteTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val completedCount = tasks.count { it.isCompleted }
    val overdueCount = tasks.count { it.priority == "Overdue" }
    val inProgressCount = tasks.count { !it.isCompleted && it.priority != "Overdue" && it.progress > 0 }
    val totalCount = tasks.size

    val filtered = tasks.filter { task ->
        when (selectedFilter) {
            "Completed" -> task.isCompleted
            "In Progress" -> !task.isCompleted && task.progress > 0
            "Not Started" -> !task.isCompleted && task.progress == 0
            else -> true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 4 Stat Counters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskStatBox("Total Tasks", totalCount.toString(), StudyHubTextPrimary, Modifier.width(200.dp))
            TaskStatBox("Completed", completedCount.toString(), AccentGreen, Modifier.width(200.dp))
            TaskStatBox("In Progress", inProgressCount.toString(), Color(0xFF2563EB), Modifier.width(200.dp))
            TaskStatBox("Overdue", overdueCount.toString(), AccentRed, Modifier.width(200.dp))
        }

        // Toolbar: Filter pills + Add Task
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 560.dp

            if (isCompact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Filter pills (horizontal scroll)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Filters:", fontSize = 12.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
                        listOf("All ($totalCount)", "Not Started", "In Progress", "Completed").forEach { filterText ->
                            val filterKey = filterText.split(" ").first()
                            val isSelected = selectedFilter == filterKey
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) StudyHubTeal else Color(0xFFF1F5F9))
                                    .clickable { selectedFilter = filterKey }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    filterText,
                                    fontSize = 11.sp,
                                    color = if (isSelected) Color.White else StudyHubTextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Sort & Add Button Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, StudyHubBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text("Sort: Due Date ▾", fontSize = 11.sp, color = StudyHubTextPrimary)
                        }

                        Button(
                            onClick = onAddTask,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .testTag("btn_add_task")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Task", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("Filters:", fontSize = 12.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
                        listOf("All ($totalCount)", "Not Started", "In Progress", "Completed").forEach { filterText ->
                            val filterKey = filterText.split(" ").first()
                            val isSelected = selectedFilter == filterKey
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) StudyHubTeal else Color(0xFFF1F5F9))
                                    .clickable { selectedFilter = filterKey }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    filterText,
                                    fontSize = 11.sp,
                                    color = if (isSelected) Color.White else StudyHubTextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, StudyHubBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text("Sort: Due Date ▾", fontSize = 11.sp, color = StudyHubTextPrimary)
                        }

                        Button(
                            onClick = onAddTask,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                            modifier = Modifier.testTag("btn_add_task")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Task", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Task Cards Grid
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 600.dp

            if (isCompact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    filtered.forEach { task ->
                        TaskCard(
                            task = task,
                            onToggle = { onToggleTask(task.id) },
                            onDelete = { onDeleteTask(task.id) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    filtered.forEach { task ->
                        TaskCard(
                            task = task,
                            onToggle = { onToggleTask(task.id) },
                            onDelete = { onDeleteTask(task.id) },
                            modifier = Modifier
                                .weight(1f)
                                .widthIn(min = 280.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun TaskStatBox(title: String, count: String, countColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 12.sp, color = StudyHubTextSecondary, fontWeight = FontWeight.Medium)
            Text(count, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = countColor)
        }
    }
}

@Composable
private fun TaskCard(
    task: TaskItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOverdue = task.priority == "Overdue"
    val isCompleted = task.isCompleted

    Card(
        modifier = modifier.testTag("task_item_card_${task.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isOverdue) AccentRed else StudyHubBorder
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Subject tag & Priority Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(task.colorHex)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = task.subject.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(task.colorHex)
                    )
                }

                val (badgeBg, badgeColor) = when {
                    isCompleted -> Pair(Color(0xFFDCFCE7), AccentGreenText)
                    isOverdue -> Pair(AccentRed, Color.White)
                    task.priority == "High" -> Pair(AccentRedLight, AccentRedText)
                    task.priority == "Medium" -> Pair(AccentYellowLight, AccentYellowText)
                    else -> Pair(Color(0xFFF1F5F9), StudyHubTextSecondary)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeBg)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isCompleted) "COMPLETED" else task.priority.uppercase(),
                        color = badgeColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = task.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = StudyHubTextPrimary
            )

            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isCompleted) "Success" else if (task.progress > 0) "In Progress" else "Not Started",
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary
                )
                Text("${task.progress}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = StudyHubTextPrimary)
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { (task.progress / 100f).toFloat() },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = if (isCompleted) AccentGreen else if (isOverdue) AccentRed else Color(task.colorHex),
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (task.dueDate.startsWith("Due")) task.dueDate else "Due ${task.dueDate}",
                    fontSize = 11.sp,
                    color = if (isOverdue) AccentRed else StudyHubTextSecondary,
                    fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Normal
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onToggle, modifier = Modifier.size(28.dp)) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Toggle Complete",
                            tint = if (isCompleted) AccentGreen else StudyHubTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(onClick = {}, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = StudyHubTextSecondary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = AccentRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
