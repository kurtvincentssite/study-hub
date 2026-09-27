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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScheduleItem
import com.example.ui.theme.AccentRed
import com.example.ui.theme.StudyHubTeal

@Composable
fun ScheduleScreen(
    schedules: List<ScheduleItem>,
    onAddSchedule: () -> Unit,
    onDeleteSchedule: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchFilter by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }

    val types = listOf("All", "Lecture", "Laboratory", "Online Class", "Consultation", "Exam")

    val filtered = schedules.filter { item ->
        val matchesSearch = searchFilter.isEmpty() ||
                item.subject.contains(searchFilter, ignoreCase = true) ||
                item.instructor.contains(searchFilter, ignoreCase = true) ||
                item.room.contains(searchFilter, ignoreCase = true) ||
                item.day.contains(searchFilter, ignoreCase = true)
        val matchesType = selectedType == "All" || item.type.equals(selectedType, ignoreCase = true)
        matchesSearch && matchesType
    }

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
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Responsive Controls Bar: Never squishes on mobile
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 520.dp

            if (isCompact) {
                // Stacked layout for compact / mobile screens
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Search Input (Full Width)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, outlineColor.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                            .background(surfaceColor)
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = onSurfaceVariant, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            BasicTextField(
                                value = searchFilter,
                                onValueChange = { searchFilter = it },
                                singleLine = true,
                                textStyle = TextStyle(fontSize = 13.sp, color = onSurface),
                                cursorBrush = SolidColor(StudyHubTeal),
                                modifier = Modifier.fillMaxWidth(),
                                decorationBox = { innerTextField ->
                                    if (searchFilter.isEmpty()) {
                                        Text("Filter schedules...", color = onSurfaceVariant.copy(alpha = 0.7f), fontSize = 13.sp)
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }

                    // Add Schedule Button (Full Width, never squished)
                    Button(
                        onClick = onAddSchedule,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("btn_add_schedule")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add Schedule",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                // Wide / Tablet layout: Side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(min = 260.dp, max = 380.dp)
                            .height(42.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, outlineColor.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                            .background(surfaceColor)
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = onSurfaceVariant, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            BasicTextField(
                                value = searchFilter,
                                onValueChange = { searchFilter = it },
                                singleLine = true,
                                textStyle = TextStyle(fontSize = 13.sp, color = onSurface),
                                cursorBrush = SolidColor(StudyHubTeal),
                                modifier = Modifier.fillMaxWidth(),
                                decorationBox = { innerTextField ->
                                    if (searchFilter.isEmpty()) {
                                        Text("Filter schedules...", color = onSurfaceVariant.copy(alpha = 0.7f), fontSize = 13.sp)
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }

                    Button(
                        onClick = onAddSchedule,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        modifier = Modifier.height(42.dp).testTag("btn_add_schedule")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Schedule", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Filter Pills: Horizontally scrollable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            types.forEach { type ->
                val isSelected = selectedType == type
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) StudyHubTeal else surfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) StudyHubTeal else outlineColor.copy(alpha = 0.5f),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { selectedType = type }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = type,
                        color = if (isSelected) Color.White else onSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        // Schedule Content (Responsive: Cards on Mobile, Table on Wide Screen)
        Card(
            modifier = Modifier.fillMaxWidth().testTag("schedule_table_card"),
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
                    Text(
                        text = "Weekly Schedule",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = onSurface
                    )
                    Text(
                        text = "${filtered.size} items",
                        fontSize = 12.sp,
                        color = onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No schedules found matching your filter.",
                            color = onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        val isWide = maxWidth >= 680.dp

                        if (isWide) {
                            // Desktop / Wide Screen: Full Data Table
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Table Header
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(surfaceVariant, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("SUBJECT", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(2f))
                                    Text("DAY", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(1f))
                                    Text("TIME", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(1.5f))
                                    Text("ROOM", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(1f))
                                    Text("INSTRUCTOR", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(1.3f))
                                    Text("TYPE", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.weight(1f))
                                    Text("ACTIONS", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = onSurfaceVariant, modifier = Modifier.width(76.dp))
                                }

                                filtered.forEach { item ->
                                    ScheduleTableRow(
                                        item = item,
                                        onDelete = { onDeleteSchedule(item.id) }
                                    )
                                }
                            }
                        } else {
                            // Mobile / Compact Screen: Clean, Beautiful Schedule Cards (No Crushed Columns!)
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                filtered.forEach { item ->
                                    ScheduleMobileCard(
                                        item = item,
                                        onDelete = { onDeleteSchedule(item.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleTableRow(
    item: ScheduleItem,
    onDelete: () -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, outlineColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .background(surfaceColor)
            .padding(vertical = 12.dp, horizontal = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Subject with colored accent bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(2f)
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(item.colorHex))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = item.subject,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = onSurface
                )
            }

            Text(item.day, fontSize = 12.sp, color = onSurfaceVariant, modifier = Modifier.weight(1f))
            Text(item.time, fontSize = 12.sp, color = onSurfaceVariant, modifier = Modifier.weight(1.5f))
            Text(item.room, fontSize = 12.sp, color = onSurfaceVariant, modifier = Modifier.weight(1f))
            Text(item.instructor, fontSize = 12.sp, color = onSurfaceVariant, modifier = Modifier.weight(1.3f))

            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(item.type, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = onSurface)
                }
            }

            Row(modifier = Modifier.width(76.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = onSurfaceVariant, modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = AccentRed, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ScheduleMobileCard(
    item: ScheduleItem,
    onDelete: () -> Unit
) {
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Color bar + Subject Title + Type Chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(item.colorHex))
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = item.subject,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = onSurface,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(item.colorHex).copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = item.type,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(item.colorHex)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Time & Day Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = StudyHubTeal,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${item.day} • ${item.time}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = onSurface
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Room & Instructor Row + Action Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.room,
                            fontSize = 12.sp,
                            color = onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.instructor,
                            fontSize = 12.sp,
                            color = onSurfaceVariant
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = onSurfaceVariant,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = AccentRed,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }
        }
    }
}
