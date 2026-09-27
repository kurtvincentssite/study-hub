package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NavDestination
import com.example.data.StudentProfile
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.StudyHubNavyCard
import com.example.ui.theme.StudyHubNavyDark
import com.example.ui.theme.StudyHubNavySelected
import com.example.ui.theme.StudyHubNavActive
import com.example.ui.theme.StudyHubNavText
import com.example.ui.theme.StudyHubTeal

@Composable
fun StudyHubSidebar(
    currentDestination: NavDestination,
    pendingTasksCount: Int,
    unreadNotificationsCount: Int,
    profile: StudentProfile,
    onNavigate: (NavDestination) -> Unit,
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(StudyHubNavyDark)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        // App Brand Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 4.dp)
                .clickable { onNavigate(NavDestination.DASHBOARD) }
                .testTag("brand_header")
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(StudyHubTeal),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "StudyHub",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "ACADEMIC SUITE",
                    color = StudyHubNavText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // Navigation Menu (Scrollable if needed)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SidebarNavItem(
                icon = Icons.Default.Dashboard,
                label = NavDestination.DASHBOARD.label,
                isSelected = currentDestination == NavDestination.DASHBOARD,
                badge = null,
                onClick = { onNavigate(NavDestination.DASHBOARD) },
                testTag = "nav_dashboard"
            )
            SidebarNavItem(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                label = NavDestination.SUBJECTS.label,
                isSelected = currentDestination == NavDestination.SUBJECTS,
                badge = null,
                onClick = { onNavigate(NavDestination.SUBJECTS) },
                testTag = "nav_subjects"
            )
            SidebarNavItem(
                icon = Icons.Default.CalendarMonth,
                label = NavDestination.CALENDAR.label,
                isSelected = currentDestination == NavDestination.CALENDAR,
                badge = null,
                onClick = { onNavigate(NavDestination.CALENDAR) },
                testTag = "nav_calendar"
            )
            SidebarNavItem(
                icon = Icons.Default.Schedule,
                label = NavDestination.SCHEDULE.label,
                isSelected = currentDestination == NavDestination.SCHEDULE,
                badge = null,
                onClick = { onNavigate(NavDestination.SCHEDULE) },
                testTag = "nav_schedule"
            )
            SidebarNavItem(
                icon = Icons.Default.TableChart,
                label = NavDestination.TIMETABLE.label,
                isSelected = currentDestination == NavDestination.TIMETABLE,
                badge = null,
                onClick = { onNavigate(NavDestination.TIMETABLE) },
                testTag = "nav_timetable"
            )
            SidebarNavItem(
                icon = Icons.Default.CheckCircle,
                label = NavDestination.TASKS.label,
                isSelected = currentDestination == NavDestination.TASKS,
                badge = if (pendingTasksCount > 0) pendingTasksCount.toString() else null,
                badgeColor = AccentOrange,
                onClick = { onNavigate(NavDestination.TASKS) },
                testTag = "nav_tasks"
            )
            SidebarNavItem(
                icon = Icons.Default.School,
                label = NavDestination.GRADES.label,
                isSelected = currentDestination == NavDestination.GRADES,
                badge = null,
                onClick = { onNavigate(NavDestination.GRADES) },
                testTag = "nav_grades"
            )
            SidebarNavItem(
                icon = Icons.Default.TrackChanges,
                label = NavDestination.STUDY_FOCUS.label,
                isSelected = currentDestination == NavDestination.STUDY_FOCUS,
                badge = null,
                onClick = { onNavigate(NavDestination.STUDY_FOCUS) },
                testTag = "nav_study_focus"
            )
            SidebarNavItem(
                icon = Icons.Default.Timer,
                label = NavDestination.TIMER.label,
                isSelected = currentDestination == NavDestination.TIMER,
                badge = null,
                onClick = { onNavigate(NavDestination.TIMER) },
                testTag = "nav_timer"
            )
            SidebarNavItem(
                icon = Icons.Default.BarChart,
                label = NavDestination.PROGRESS.label,
                isSelected = currentDestination == NavDestination.PROGRESS,
                badge = null,
                onClick = { onNavigate(NavDestination.PROGRESS) },
                testTag = "nav_progress"
            )
            SidebarNavItem(
                icon = Icons.Default.Notifications,
                label = NavDestination.NOTIFICATIONS.label,
                isSelected = currentDestination == NavDestination.NOTIFICATIONS,
                badge = if (unreadNotificationsCount > 0) unreadNotificationsCount.toString() else null,
                badgeColor = Color(0xFF3B82F6),
                onClick = { onNavigate(NavDestination.NOTIFICATIONS) },
                testTag = "nav_notifications"
            )
            SidebarNavItem(
                icon = Icons.Default.Person,
                label = NavDestination.PROFILE.label,
                isSelected = currentDestination == NavDestination.PROFILE,
                badge = null,
                onClick = { onNavigate(NavDestination.PROFILE) },
                testTag = "nav_profile"
            )
            SidebarNavItem(
                icon = Icons.Default.Settings,
                label = NavDestination.SETTINGS.label,
                isSelected = currentDestination == NavDestination.SETTINGS,
                badge = null,
                onClick = { onNavigate(NavDestination.SETTINGS) },
                testTag = "nav_settings"
            )
            SidebarNavItem(
                icon = Icons.Default.Code,
                label = "Ionic (HTML/JS)",
                isSelected = currentDestination == NavDestination.IONIC_HUB,
                badge = "WEB",
                badgeColor = StudyHubTeal,
                onClick = { onNavigate(NavDestination.IONIC_HUB) },
                testTag = "nav_ionic_hub"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Dark / Light Mode Switcher
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1E293B))
                .clickable(onClick = onToggleTheme)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .testTag("sidebar_theme_toggle")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = null,
                    tint = if (isDarkMode) Color(0xFFFBBF24) else Color(0xFF94A3B8),
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (isDarkMode) "Light Mode" else "Dark Mode",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = "Toggle",
                color = StudyHubTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottom User Card
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(StudyHubNavyCard)
                .clickable { onNavigate(NavDestination.PROFILE) }
                .padding(10.dp)
                .testTag("user_profile_card")
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(StudyHubTeal),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "AM",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.displayAccountName,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 1
                )
                Text(
                    text = profile.email,
                    color = StudyHubNavText,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun SidebarNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    badge: String?,
    badgeColor: Color = AccentOrange,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) StudyHubNavySelected else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 11.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) StudyHubNavActive else StudyHubNavText,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = label,
            color = if (isSelected) StudyHubNavActive else StudyHubNavText,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        if (badge != null) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(badgeColor)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }
    }
}
