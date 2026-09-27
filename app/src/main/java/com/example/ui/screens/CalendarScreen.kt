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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DayEvent
import com.example.ui.theme.StudyHubTeal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    events: List<DayEvent>,
    onAddEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    val initialDate = remember { LocalDate.of(2026, 9, 23) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.of(2026, 9)) }
    var selectedDate by remember { mutableStateOf(initialDate) }
    var viewMode by remember { mutableStateOf("Month") }
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        Pair("Classes", 0xFF2563EB),
        Pair("Exams", 0xFFDC2626),
        Pair("Assignments", 0xFFEA580C),
        Pair("Projects", 0xFF7C3AED),
        Pair("Deadlines", 0xFFD97706),
        Pair("Study Sessions", 0xFF0D9488),
        Pair("Personal", 0xFF64748B)
    )

    // Filter events by selected category if any
    val filteredEvents = if (selectedCategoryFilter != null) {
        events.filter { it.type.equals(selectedCategoryFilter, ignoreCase = true) }
    } else {
        events
    }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val monthName = currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val year = currentYearMonth.year

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
            // Month / Navigation Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (viewMode == "Week") {
                            selectedDate = selectedDate.minusWeeks(1)
                            currentYearMonth = YearMonth.from(selectedDate)
                        } else if (viewMode == "Day") {
                            selectedDate = selectedDate.minusDays(1)
                            currentYearMonth = YearMonth.from(selectedDate)
                        } else {
                            currentYearMonth = currentYearMonth.minusMonths(1)
                        }
                    },
                    modifier = Modifier.size(36.dp).testTag("cal_prev_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous",
                        tint = onSurface
                    )
                }

                Text(
                    text = if (viewMode == "Day") {
                        "${selectedDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)} ${selectedDate.dayOfMonth}, ${selectedDate.year}"
                    } else if (viewMode == "Week") {
                        val startOfWeek = selectedDate.with(DayOfWeek.MONDAY)
                        val endOfWeek = selectedDate.with(DayOfWeek.SUNDAY)
                        "${startOfWeek.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)} ${startOfWeek.dayOfMonth} - ${endOfWeek.dayOfMonth}, ${startOfWeek.year}"
                    } else {
                        "$monthName $year"
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = {
                        if (viewMode == "Week") {
                            selectedDate = selectedDate.plusWeeks(1)
                            currentYearMonth = YearMonth.from(selectedDate)
                        } else if (viewMode == "Day") {
                            selectedDate = selectedDate.plusDays(1)
                            currentYearMonth = YearMonth.from(selectedDate)
                        } else {
                            currentYearMonth = currentYearMonth.plusMonths(1)
                        }
                    },
                    modifier = Modifier.size(36.dp).testTag("cal_next_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        tint = onSurface
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // "Today" Button
                OutlinedButton(
                    onClick = {
                        selectedDate = initialDate
                        currentYearMonth = YearMonth.of(2026, 9)
                    },
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor.copy(alpha = 0.6f)),
                    modifier = Modifier.height(34.dp).testTag("cal_today_btn")
                ) {
                    Text("Today", fontSize = 11.sp, color = onSurface, fontWeight = FontWeight.SemiBold)
                }
            }

            // View Switcher (Month, Week, Day) & Add Event Button
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(surfaceVariant)
                        .border(1.dp, outlineColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(2.dp)
                ) {
                    listOf("Month", "Week", "Day").forEach { mode ->
                        val isSelected = viewMode == mode
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) StudyHubTeal else Color.Transparent)
                                .clickable { viewMode = mode }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("view_mode_$mode")
                        ) {
                            Text(
                                text = mode,
                                fontSize = 11.sp,
                                color = if (isSelected) Color.White else onSurfaceVariant,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Button(
                    onClick = onAddEvent,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                    modifier = Modifier.height(36.dp).testTag("btn_add_event")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Event", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                }
            }
        }

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "All" Chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selectedCategoryFilter == null) StudyHubTeal.copy(alpha = 0.15f) else surfaceVariant)
                    .border(
                        1.dp,
                        if (selectedCategoryFilter == null) StudyHubTeal else outlineColor.copy(alpha = 0.5f),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { selectedCategoryFilter = null }
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "All Categories",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selectedCategoryFilter == null) StudyHubTeal else onSurfaceVariant
                )
            }

            categories.forEach { (catName, catColor) ->
                val isSelected = selectedCategoryFilter == catName
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) Color(catColor).copy(alpha = 0.15f) else surfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) Color(catColor) else outlineColor.copy(alpha = 0.5f),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            selectedCategoryFilter = if (isSelected) null else catName
                        }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(catColor)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = catName,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(catColor) else onSurfaceVariant
                    )
                }
            }
        }

        // View Mode Content
        when (viewMode) {
            "Month" -> MonthViewContent(
                currentYearMonth = currentYearMonth,
                selectedDate = selectedDate,
                onSelectDate = { selectedDate = it },
                events = filteredEvents,
                onAddEvent = onAddEvent
            )
            "Week" -> WeekViewContent(
                selectedDate = selectedDate,
                onSelectDate = { selectedDate = it },
                events = filteredEvents,
                onAddEvent = onAddEvent
            )
            "Day" -> DayViewContent(
                selectedDate = selectedDate,
                events = filteredEvents,
                onAddEvent = onAddEvent
            )
        }
    }
}

@Composable
private fun MonthViewContent(
    currentYearMonth: YearMonth,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    events: List<DayEvent>,
    onAddEvent: () -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val isWide = maxWidth >= 760.dp

        if (isWide) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Calendar Grid Card
                Card(
                    modifier = Modifier
                        .weight(1.6f)
                        .testTag("calendar_month_grid_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    CalendarGrid(
                        currentYearMonth = currentYearMonth,
                        selectedDate = selectedDate,
                        onSelectDate = onSelectDate,
                        events = events
                    )
                }

                // Selected Day Detail Panel
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("card_selected_day_events"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    SelectedDayDetailSection(
                        selectedDate = selectedDate,
                        events = events,
                        onAddEvent = onAddEvent
                    )
                }
            }
        } else {
            // Mobile Stack: Month Grid on top, Selected day below
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth().testTag("calendar_month_grid_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    CalendarGrid(
                        currentYearMonth = currentYearMonth,
                        selectedDate = selectedDate,
                        onSelectDate = onSelectDate,
                        events = events
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth().testTag("card_selected_day_events"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
                ) {
                    SelectedDayDetailSection(
                        selectedDate = selectedDate,
                        events = events,
                        onAddEvent = onAddEvent
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentYearMonth: YearMonth,
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    events: List<DayEvent>
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Column(modifier = Modifier.padding(16.dp)) {
        // Day of week headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { d ->
                Text(
                    text = d,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dynamic days computation for currentYearMonth
        val firstDayOfMonth = currentYearMonth.atDay(1)
        val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value - 1 // 0 for Monday, 6 for Sunday
        val daysInCurrentMonth = currentYearMonth.lengthOfMonth()
        val prevMonth = currentYearMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()

        // Build list of 35 or 42 calendar grid cells
        data class CalendarCell(val date: LocalDate, val isCurrentMonth: Boolean)
        val cells = mutableListOf<CalendarCell>()

        // 1. Previous month trailing days
        for (i in (dayOfWeekOffset - 1) downTo 0) {
            val day = daysInPrevMonth - i
            cells.add(CalendarCell(prevMonth.atDay(day), false))
        }

        // 2. Current month days
        for (day in 1..daysInCurrentMonth) {
            cells.add(CalendarCell(currentYearMonth.atDay(day), true))
        }

        // 3. Next month leading days
        val totalCells = if (cells.size > 35) 42 else 35
        val nextMonth = currentYearMonth.plusMonths(1)
        var nextDay = 1
        while (cells.size < totalCells) {
            cells.add(CalendarCell(nextMonth.atDay(nextDay++), false))
        }

        // Render rows of 7 days
        val rows = cells.chunked(7)
        rows.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { cell ->
                    val isSelected = cell.date == selectedDate
                    val dayEvents = if (cell.isCurrentMonth) {
                        events.filter { it.dayOfMonth == cell.date.dayOfMonth }
                    } else emptyList()

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(72.dp)
                            .border(0.5.dp, outlineColor.copy(alpha = 0.35f))
                            .background(
                                if (isSelected) StudyHubTeal.copy(alpha = 0.12f)
                                else if (!cell.isCurrentMonth) surfaceVariant.copy(alpha = 0.4f)
                                else Color.Transparent
                            )
                            .clickable {
                                onSelectDate(cell.date)
                            }
                            .padding(4.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = cell.date.dayOfMonth.toString(),
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (!cell.isCurrentMonth) onSurfaceVariant.copy(alpha = 0.4f)
                                    else if (isSelected) StudyHubTeal
                                    else onSurface
                                )
                                if (isSelected) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(StudyHubTeal))
                                }
                            }

                            dayEvents.take(2).forEach { ev ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 2.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(Color(ev.colorHex).copy(alpha = 0.2f))
                                        .padding(horizontal = 2.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = ev.title,
                                        fontSize = 8.sp,
                                        maxLines = 1,
                                        color = Color(ev.colorHex),
                                        fontWeight = FontWeight.Bold
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
private fun SelectedDayDetailSection(
    selectedDate: LocalDate,
    events: List<DayEvent>,
    onAddEvent: () -> Unit
) {
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    val dayOfWeekName = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val monthName = selectedDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

    val selectedEvents = events.filter { it.dayOfMonth == selectedDate.dayOfMonth }

    Column(modifier = Modifier.padding(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("SELECTED DAY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = StudyHubTeal, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text("$dayOfWeekName, $monthName ${selectedDate.dayOfMonth}", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = onSurface)
            }
            IconButton(onClick = onAddEvent, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add event for this day", tint = StudyHubTeal)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedEvents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No events scheduled for this day.", fontSize = 12.sp, color = onSurfaceVariant)
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                selectedEvents.forEach { ev ->
                    DayEventDetailCard(ev)
                }
            }
        }
    }
}

@Composable
private fun WeekViewContent(
    selectedDate: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
    events: List<DayEvent>,
    onAddEvent: () -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val monday = selectedDate.with(DayOfWeek.MONDAY)
    val weekDays = (0..6).map { monday.plusDays(it.toLong()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Day selector tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                weekDays.forEach { date ->
                    val isSelected = date == selectedDate
                    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                    val dayEventsCount = events.count { it.dayOfMonth == date.dayOfMonth }

                    Box(
                        modifier = Modifier
                            .widthIn(min = 72.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) StudyHubTeal else surfaceVariant)
                            .clickable { onSelectDate(date) }
                            .padding(vertical = 10.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dayName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White.copy(alpha = 0.8f) else onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = date.dayOfMonth.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else onSurface
                            )
                            if (dayEventsCount > 0) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.White else StudyHubTeal)
                                        .padding(horizontal = 6.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "$dayEventsCount",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) StudyHubTeal else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Schedule for the selected day in week
            val dayEvents = events.filter { it.dayOfMonth == selectedDate.dayOfMonth }
            Text(
                text = "Schedule for ${selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)}, ${selectedDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)} ${selectedDate.dayOfMonth}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (dayEvents.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No scheduled items for this date.", fontSize = 12.sp, color = onSurfaceVariant)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    dayEvents.forEach { ev ->
                        DayEventDetailCard(ev)
                    }
                }
            }
        }
    }
}

@Composable
private fun DayViewContent(
    selectedDate: LocalDate,
    events: List<DayEvent>,
    onAddEvent: () -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val dayEvents = events.filter { it.dayOfMonth == selectedDate.dayOfMonth }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)}, ${selectedDate.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)} ${selectedDate.dayOfMonth}, ${selectedDate.year}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = onSurface
                    )
                    Text(
                        text = "${dayEvents.size} Scheduled Events / Deadlines",
                        fontSize = 12.sp,
                        color = onSurfaceVariant
                    )
                }

                Button(
                    onClick = onAddEvent,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Entry", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Hourly Agenda Slots (8:00 AM to 6:00 PM)
            val hours = listOf(
                "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM",
                "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM",
                "4:00 PM", "5:00 PM", "6:00 PM"
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                hours.forEach { hour ->
                    val matchingEvent = dayEvents.find { it.time.contains(hour.substringBefore(" ")) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = hour,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = onSurfaceVariant,
                            modifier = Modifier.width(65.dp)
                        )

                        if (matchingEvent != null) {
                            Box(modifier = Modifier.weight(1f)) {
                                DayEventDetailCard(matchingEvent)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(0.5.dp, outlineColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text("Available slot", fontSize = 11.sp, color = onSurfaceVariant.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayEventDetailCard(ev: DayEvent) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(ev.colorHex).copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .background(Color(ev.colorHex).copy(alpha = 0.08f))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(ev.colorHex))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = ev.type.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = onSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = ev.time, fontSize = 11.sp, color = onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = ev.title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = ev.description,
                fontSize = 11.sp,
                color = onSurfaceVariant
            )
        }
    }
}
