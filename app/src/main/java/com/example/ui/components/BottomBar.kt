package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import com.example.ui.theme.StudyHubBorder
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextSecondary

data class BottomNavItem(
    val destination: NavDestination,
    val label: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)

@Composable
fun StudyHubBottomNavBar(
    currentDestination: NavDestination,
    pendingTasksCount: Int,
    onNavigate: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(NavDestination.DASHBOARD, "Dashboard", Icons.Default.Dashboard),
        BottomNavItem(NavDestination.SUBJECTS, "Subjects", Icons.AutoMirrored.Filled.MenuBook),
        BottomNavItem(NavDestination.STUDY_FOCUS, "Focus", Icons.Default.Timer),
        BottomNavItem(NavDestination.SCHEDULE, "Schedule", Icons.Default.CalendarMonth),
        BottomNavItem(
            if (currentDestination == NavDestination.SETTINGS) NavDestination.SETTINGS else NavDestination.PROFILE,
            if (currentDestination == NavDestination.SETTINGS) "Settings" else "Profile",
            if (currentDestination == NavDestination.SETTINGS) Icons.Default.Settings else Icons.Default.Person
        )
    )

    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = StudyHubBorder)
            .testTag("studyhub_bottom_nav"),
        containerColor = surfaceColor,
        tonalElevation = 6.dp
    ) {
        items.forEach { item ->
            val isSelected = currentDestination == item.destination ||
                    (item.destination == NavDestination.SUBJECTS && currentDestination == NavDestination.GRADES) ||
                    (item.destination == NavDestination.STUDY_FOCUS && currentDestination == NavDestination.TIMER) ||
                    (item.destination == NavDestination.SCHEDULE && (currentDestination == NavDestination.CALENDAR || currentDestination == NavDestination.TIMETABLE)) ||
                    (item.destination == NavDestination.PROFILE && (currentDestination == NavDestination.PROFILE || currentDestination == NavDestination.NOTIFICATIONS))

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.destination) },
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color(0xFFF59E0B),
                                    contentColor = Color.White
                                ) {
                                    Text(item.badgeCount.toString(), fontSize = 9.sp)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = StudyHubTeal,
                    selectedTextColor = StudyHubTeal,
                    unselectedIconColor = onSurfaceVariant,
                    unselectedTextColor = onSurfaceVariant,
                    indicatorColor = StudyHubTeal.copy(alpha = 0.12f)
                ),
                modifier = Modifier.testTag("nav_item_${item.label.lowercase()}")
            )
        }
    }
}
