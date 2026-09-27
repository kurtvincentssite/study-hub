package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.NavDestination
import com.example.ui.components.StudyHubBottomNavBar
import com.example.ui.components.StudyHubSidebar
import com.example.ui.components.StudyHubTopHeader
import com.example.ui.dialogs.AddGradeDialog
import com.example.ui.dialogs.AddScheduleDialog
import com.example.ui.dialogs.AddSubjectDialog
import com.example.ui.dialogs.AddTaskDialog
import com.example.ui.dialogs.HelpGuideDialog
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GradesScreen
import com.example.ui.screens.IonicWebViewScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.ScheduleScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudyFocusScreen
import com.example.ui.screens.SubjectsScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.screens.TimerScreen
import com.example.ui.screens.TimetableScreen
import com.example.ui.theme.StudyHubTheme
import com.example.ui.viewmodel.StudyHubViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyHubApp()
        }
    }
}

@Composable
fun StudyHubApp(
    viewModel: StudyHubViewModel = viewModel()
) {
    val currentDestination by viewModel.currentDestination.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val subjects by viewModel.subjects.collectAsStateWithLifecycle()
    val schedules by viewModel.schedules.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val assessments by viewModel.assessments.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val calendarEvents by viewModel.calendarEvents.collectAsStateWithLifecycle()
    val studentProfile by viewModel.studentProfile.collectAsStateWithLifecycle()
    val focusTimerState by viewModel.focusTimerState.collectAsStateWithLifecycle()
    val appSettings by viewModel.appSettings.collectAsStateWithLifecycle()
    val unreadNotificationsCount by viewModel.unreadNotificationsCount.collectAsStateWithLifecycle()
    val pendingTasksCount by viewModel.pendingTasksCount.collectAsStateWithLifecycle()

    val showAddSubjectDialog by viewModel.showAddSubjectDialog.collectAsStateWithLifecycle()
    val showAddTaskDialog by viewModel.showAddTaskDialog.collectAsStateWithLifecycle()
    val showAddScheduleDialog by viewModel.showAddScheduleDialog.collectAsStateWithLifecycle()
    val showAddGradeDialog by viewModel.showAddGradeDialog.collectAsStateWithLifecycle()
    val showHelpDialog by viewModel.showHelpDialog.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val isSystemDark = isSystemInDarkTheme()
    val isDarkMode = when (appSettings.themeMode) {
        "Dark" -> true
        "Light" -> false
        else -> isSystemDark
    }

    StudyHubTheme(darkTheme = isDarkMode) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val isWideScreen = maxWidth >= 760.dp

            if (isWideScreen) {
                // Web Dashboard Layout: Fixed Sidebar on the left, Header & Content on the right
                Row(modifier = Modifier.fillMaxSize()) {
                    StudyHubSidebar(
                        currentDestination = currentDestination,
                        pendingTasksCount = pendingTasksCount,
                        unreadNotificationsCount = unreadNotificationsCount,
                        profile = studentProfile,
                        isDarkMode = isDarkMode,
                        onToggleTheme = { viewModel.toggleThemeMode() },
                        onNavigate = { dest -> viewModel.navigateTo(dest) }
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        StudyHubTopHeader(
                            title = currentDestination.label,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                            unreadNotificationsCount = unreadNotificationsCount,
                            onNotificationClick = { viewModel.navigateTo(NavDestination.NOTIFICATIONS) },
                            onHelpClick = { viewModel.showHelpDialog.value = true },
                            isDarkMode = isDarkMode,
                            onToggleTheme = { viewModel.toggleThemeMode() },
                            showMenuButton = false
                        )

                        ScreenContent(
                            currentDestination = currentDestination,
                            viewModel = viewModel,
                            subjects = subjects,
                            schedules = schedules,
                            tasks = tasks,
                            assessments = assessments,
                            notifications = notifications,
                            calendarEvents = calendarEvents,
                            studentProfile = studentProfile,
                            focusTimerState = focusTimerState,
                            appSettings = appSettings,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            } else {
                // Mobile Adaptive: Drawer Navigation
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = Color.Transparent,
                            modifier = Modifier.fillMaxWidth(0.82f)
                        ) {
                            StudyHubSidebar(
                                currentDestination = currentDestination,
                                pendingTasksCount = pendingTasksCount,
                                unreadNotificationsCount = unreadNotificationsCount,
                                profile = studentProfile,
                                isDarkMode = isDarkMode,
                                onToggleTheme = { viewModel.toggleThemeMode() },
                                onNavigate = { dest ->
                                    viewModel.navigateTo(dest)
                                    coroutineScope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            StudyHubTopHeader(
                                title = currentDestination.label,
                                searchQuery = searchQuery,
                                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                                unreadNotificationsCount = unreadNotificationsCount,
                                onNotificationClick = { viewModel.navigateTo(NavDestination.NOTIFICATIONS) },
                                onHelpClick = { viewModel.showHelpDialog.value = true },
                                isDarkMode = isDarkMode,
                                onToggleTheme = { viewModel.toggleThemeMode() },
                                showMenuButton = true,
                                onMenuClick = { coroutineScope.launch { drawerState.open() } }
                            )
                        },
                        bottomBar = {
                            StudyHubBottomNavBar(
                                currentDestination = currentDestination,
                                pendingTasksCount = pendingTasksCount,
                                onNavigate = { dest -> viewModel.navigateTo(dest) }
                            )
                        }
                    ) { innerPadding ->
                        ScreenContent(
                            currentDestination = currentDestination,
                            viewModel = viewModel,
                            subjects = subjects,
                            schedules = schedules,
                            tasks = tasks,
                            assessments = assessments,
                            notifications = notifications,
                            calendarEvents = calendarEvents,
                            studentProfile = studentProfile,
                            focusTimerState = focusTimerState,
                            appSettings = appSettings,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }

        // Dialogs
        if (showAddSubjectDialog) {
            AddSubjectDialog(
                onDismiss = { viewModel.showAddSubjectDialog.value = false },
                onConfirm = { subject ->
                    viewModel.addSubject(subject)
                    viewModel.showAddSubjectDialog.value = false
                }
            )
        }

        if (showAddTaskDialog) {
            AddTaskDialog(
                subjects = subjects,
                onDismiss = { viewModel.showAddTaskDialog.value = false },
                onConfirm = { task ->
                    viewModel.addTask(task)
                    viewModel.showAddTaskDialog.value = false
                }
            )
        }

        if (showAddScheduleDialog) {
            AddScheduleDialog(
                subjects = subjects,
                onDismiss = { viewModel.showAddScheduleDialog.value = false },
                onConfirm = { schedule ->
                    viewModel.addSchedule(schedule)
                    viewModel.showAddScheduleDialog.value = false
                }
            )
        }

        if (showAddGradeDialog) {
            AddGradeDialog(
                subjects = subjects,
                onDismiss = { viewModel.showAddGradeDialog.value = false },
                onConfirm = { assessment ->
                    viewModel.addAssessment(assessment)
                    viewModel.showAddGradeDialog.value = false
                }
            )
        }

        if (showHelpDialog) {
            HelpGuideDialog(
                onDismiss = { viewModel.showHelpDialog.value = false }
            )
        }
    }
}

@Composable
private fun ScreenContent(
    currentDestination: NavDestination,
    viewModel: StudyHubViewModel,
    subjects: List<com.example.data.SubjectItem>,
    schedules: List<com.example.data.ScheduleItem>,
    tasks: List<com.example.data.TaskItem>,
    assessments: List<com.example.data.AssessmentItem>,
    notifications: List<com.example.data.NotificationItem>,
    calendarEvents: List<com.example.data.DayEvent>,
    studentProfile: com.example.data.StudentProfile,
    focusTimerState: com.example.data.FocusTimerState,
    appSettings: com.example.data.AppSettings,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (currentDestination) {
            NavDestination.DASHBOARD -> DashboardScreen(
                tasks = tasks,
                schedules = schedules,
                onToggleTask = { viewModel.toggleTask(it) },
                onNavigate = { viewModel.navigateTo(it) },
                onAddSubject = { viewModel.showAddSubjectDialog.value = true },
                onAddTask = { viewModel.showAddTaskDialog.value = true },
                onAddSchedule = { viewModel.showAddScheduleDialog.value = true },
                onAddGrade = { viewModel.showAddGradeDialog.value = true },
                onStartFocus = { viewModel.navigateTo(NavDestination.TIMER) }
            )
            NavDestination.SUBJECTS -> SubjectsScreen(
                subjects = subjects,
                onAddSubject = { viewModel.showAddSubjectDialog.value = true }
            )
            NavDestination.CALENDAR -> CalendarScreen(
                events = calendarEvents,
                onAddEvent = { viewModel.showAddScheduleDialog.value = true }
            )
            NavDestination.SCHEDULE -> ScheduleScreen(
                schedules = schedules,
                onAddSchedule = { viewModel.showAddScheduleDialog.value = true },
                onDeleteSchedule = { viewModel.deleteSchedule(it) }
            )
            NavDestination.TIMETABLE -> TimetableScreen(
                onAddEvent = { viewModel.showAddScheduleDialog.value = true }
            )
            NavDestination.TASKS -> TasksScreen(
                tasks = tasks,
                onAddTask = { viewModel.showAddTaskDialog.value = true },
                onToggleTask = { viewModel.toggleTask(it) },
                onDeleteTask = { viewModel.deleteTask(it) }
            )
            NavDestination.GRADES -> GradesScreen(
                subjects = subjects,
                assessments = assessments,
                onAddAssessment = { viewModel.showAddGradeDialog.value = true }
            )
            NavDestination.STUDY_FOCUS -> StudyFocusScreen(
                timerState = focusTimerState,
                onNavigate = { viewModel.navigateTo(it) }
            )
            NavDestination.TIMER -> TimerScreen(
                timerState = focusTimerState,
                subjects = subjects,
                onStartTimer = { viewModel.startTimer() },
                onPauseTimer = { viewModel.pauseTimer() },
                onResetTimer = { viewModel.resetTimer() },
                onSetMode = { mode, mins -> viewModel.setTimerMode(mode, mins) },
                onUpdateGoal = { subj, goal -> viewModel.updateFocusGoal(subj, goal) }
            )
            NavDestination.PROGRESS -> ProgressScreen(
                subjects = subjects
            )
            NavDestination.NOTIFICATIONS -> NotificationsScreen(
                notifications = notifications,
                onMarkAllRead = { viewModel.markAllNotificationsRead() },
                onDismissNotification = { viewModel.dismissNotification(it) }
            )
            NavDestination.PROFILE -> ProfileScreen(
                profile = studentProfile,
                subjects = subjects
            )
            NavDestination.SETTINGS -> SettingsScreen(
                settings = appSettings,
                profile = studentProfile,
                onUpdateSettings = { viewModel.updateSettings(it) },
                onUpdateProfile = { viewModel.updateStudentProfile(it) }
            )
            NavDestination.IONIC_HUB -> IonicWebViewScreen(
                viewModel = viewModel,
                onNavigateBack = { viewModel.navigateTo(NavDestination.DASHBOARD) }
            )
        }
    }
}
