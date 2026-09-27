package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
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
import com.example.data.NotificationItem
import com.example.ui.theme.AccentRed
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    onMarkAllRead: () -> Unit,
    onDismissNotification: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val unreadCount = notifications.count { it.isUnread }
    val filtered = notifications.filter { item ->
        when (selectedFilter) {
            "Unread" -> item.isUnread
            "Classes" -> item.category == "Class"
            "Assignments" -> item.category == "Task"
            "Exams" -> item.category == "Exam"
            "Study" -> item.category == "Focus"
            else -> true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Controls Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Filter Pills
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "All (${notifications.size})",
                    "Unread ($unreadCount)",
                    "Classes",
                    "Assignments",
                    "Exams",
                    "Study"
                ).forEach { filterText ->
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
                            text = filterText,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else StudyHubTextSecondary
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = onMarkAllRead,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubTeal),
                modifier = Modifier.testTag("btn_mark_all_read")
            ) {
                Text("Mark all read", color = StudyHubTeal, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        // Notification List Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("notifications_list_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, StudyHubBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No notifications in this filter.", color = StudyHubTextSecondary, fontSize = 13.sp)
                    }
                } else {
                    filtered.forEach { item ->
                        NotificationListItem(
                            item = item,
                            onDismiss = { onDismissNotification(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationListItem(
    item: NotificationItem,
    onDismiss: () -> Unit
) {
    val icon = when (item.category) {
        "Class" -> Icons.Default.Schedule
        "Task" -> Icons.Default.Assignment
        "Exam" -> Icons.Default.Warning
        "Focus" -> Icons.AutoMirrored.Filled.MenuBook
        else -> Icons.Default.Notifications
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (item.isUnread) StudyHubTeal.copy(alpha = 0.4f) else StudyHubBorder,
                RoundedCornerShape(12.dp)
            )
            .background(if (item.isUnread) Color(0xFFF0FDFA) else Color(0xFFFAFAFA))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(item.colorHex).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(item.colorHex),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.title,
                        fontWeight = if (item.isUnread) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = StudyHubTextPrimary
                    )
                    if (item.isUnread) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(StudyHubTeal)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.message,
                    fontSize = 11.sp,
                    color = StudyHubTextSecondary,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.timestamp,
                    fontSize = 10.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = StudyHubTextSecondary, modifier = Modifier.size(16.dp))
            }
        }
    }
}
