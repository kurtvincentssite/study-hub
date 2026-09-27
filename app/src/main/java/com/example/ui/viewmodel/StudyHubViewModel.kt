package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AssessmentItem
import com.example.data.AppSettings
import com.example.data.DayEvent
import com.example.data.FocusTimerState
import com.example.data.NavDestination
import com.example.data.NotificationItem
import com.example.data.ScheduleItem
import com.example.data.StudentProfile
import com.example.data.StudyHubRepository
import com.example.data.SubjectItem
import com.example.data.TaskItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StudyHubViewModel(
    private val repository: StudyHubRepository = StudyHubRepository()
) : ViewModel() {

    private val _currentDestination = MutableStateFlow(NavDestination.DASHBOARD)
    val currentDestination: StateFlow<NavDestination> = _currentDestination.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Dialogs
    val showAddSubjectDialog = MutableStateFlow(false)
    val showAddTaskDialog = MutableStateFlow(false)
    val showAddScheduleDialog = MutableStateFlow(false)
    val showAddGradeDialog = MutableStateFlow(false)
    val showHelpDialog = MutableStateFlow(false)

    val subjects: StateFlow<List<SubjectItem>> = repository.subjects
    val schedules: StateFlow<List<ScheduleItem>> = repository.schedules
    val tasks: StateFlow<List<TaskItem>> = repository.tasks
    val assessments: StateFlow<List<AssessmentItem>> = repository.assessments
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val calendarEvents: StateFlow<List<DayEvent>> = repository.calendarEvents
    val studentProfile: StateFlow<StudentProfile> = repository.studentProfile
    val focusTimerState: StateFlow<FocusTimerState> = repository.focusTimerState
    val appSettings: StateFlow<AppSettings> = repository.appSettings

    // Unread count
    val unreadNotificationsCount: StateFlow<Int> = notifications.combine(_currentDestination) { list, _ ->
        list.count { it.isUnread }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 4)

    val pendingTasksCount: StateFlow<Int> = tasks.combine(_currentDestination) { list, _ ->
        list.count { !it.isCompleted }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 8)

    init {
        // Ticker coroutine for study focus timer
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                repository.updateTimerTick()
            }
        }
    }

    fun navigateTo(destination: NavDestination) {
        _currentDestination.value = destination
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleTask(taskId: String) {
        repository.toggleTask(taskId)
    }

    fun addTask(task: TaskItem) {
        repository.addTask(task)
    }

    fun deleteTask(taskId: String) {
        repository.deleteTask(taskId)
    }

    fun addSubject(subject: SubjectItem) {
        repository.addSubject(subject)
    }

    fun addSchedule(schedule: ScheduleItem) {
        repository.addSchedule(schedule)
    }

    fun deleteSchedule(scheduleId: String) {
        repository.deleteSchedule(scheduleId)
    }

    fun addAssessment(assessment: AssessmentItem) {
        repository.addAssessment(assessment)
    }

    fun markAllNotificationsRead() {
        repository.markAllNotificationsRead()
    }

    fun dismissNotification(id: String) {
        repository.dismissNotification(id)
    }

    fun startTimer() {
        repository.startTimer()
    }

    fun pauseTimer() {
        repository.pauseTimer()
    }

    fun resetTimer() {
        repository.resetTimer()
    }

    fun setTimerMode(modeName: String, durationMinutes: Int) {
        repository.setTimerMode(modeName, durationMinutes)
    }

    fun updateFocusGoal(subject: String, goal: String) {
        repository.updateFocusGoal(subject, goal)
    }

    fun updateSettings(settings: AppSettings) {
        repository.updateSettings(settings)
    }

    fun toggleThemeMode() {
        val currentMode = appSettings.value.themeMode
        val nextMode = if (currentMode == "Dark") "Light" else "Dark"
        updateSettings(appSettings.value.copy(themeMode = nextMode))
    }

    fun setThemeMode(mode: String) {
        updateSettings(appSettings.value.copy(themeMode = mode))
    }

    fun updateStudentProfile(profile: StudentProfile) {
        repository.updateStudentProfile(profile)
    }
}
