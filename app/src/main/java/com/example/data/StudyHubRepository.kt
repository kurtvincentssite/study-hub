package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudyHubRepository {

    private val _subjects = MutableStateFlow(
        listOf(
            SubjectItem(
                id = "sub-1",
                code = "CS-401",
                title = "Advanced Web Development",
                instructor = "Prof. Garcia",
                room = "Room 301",
                units = 3,
                averageGrade = 91.2,
                studyTimeHours = 12.5,
                classesPerWeek = 3,
                colorHex = 0xFF2563EB,
                status = "Active",
                semester = "1st Sem 2026-2027"
            ),
            SubjectItem(
                id = "sub-2",
                code = "CS-405",
                title = "Mobile Computing",
                instructor = "Prof. Santos",
                room = "Room 205",
                units = 3,
                averageGrade = 87.5,
                studyTimeHours = 8.0,
                classesPerWeek = 2,
                colorHex = 0xFF7C3AED,
                status = "Active",
                semester = "1st Sem 2026-2027"
            ),
            SubjectItem(
                id = "sub-3",
                code = "CS-403",
                title = "Database Management",
                instructor = "Prof. Reyes",
                room = "Room 301",
                units = 3,
                averageGrade = 88.0,
                studyTimeHours = 4.0,
                classesPerWeek = 2,
                colorHex = 0xFF0D9488,
                status = "Active",
                semester = "1st Sem 2026-2027"
            ),
            SubjectItem(
                id = "sub-4",
                code = "CS-409",
                title = "Information Assurance",
                instructor = "Prof. Cruz",
                room = "Lab 2",
                units = 3,
                averageGrade = 85.4,
                studyTimeHours = 6.5,
                classesPerWeek = 1,
                colorHex = 0xFFEA580C,
                status = "Completed",
                semester = "2nd Sem 2025-2026"
            ),
            SubjectItem(
                id = "sub-5",
                code = "CS-411",
                title = "Social and Ethical Computing",
                instructor = "Prof. Lim",
                room = "Room 108",
                units = 3,
                averageGrade = 90.0,
                studyTimeHours = 2.0,
                classesPerWeek = 1,
                colorHex = 0xFFDB2777,
                status = "Active",
                semester = "1st Sem 2026-2027"
            ),
            SubjectItem(
                id = "sub-6",
                code = "CS-302",
                title = "Algorithms & Complexity",
                instructor = "Dr. Torres",
                room = "Room 201",
                units = 3,
                averageGrade = 93.0,
                studyTimeHours = 10.0,
                classesPerWeek = 2,
                colorHex = 0xFF059669,
                status = "Archived",
                semester = "2nd Sem 2025-2026"
            ),
            SubjectItem(
                id = "sub-7",
                code = "CS-304",
                title = "Web Systems & Technologies",
                instructor = "Prof. Navarro",
                room = "Lab 1",
                units = 3,
                averageGrade = 89.5,
                studyTimeHours = 7.5,
                classesPerWeek = 2,
                colorHex = 0xFF6366F1,
                status = "Completed",
                semester = "Summer 2026"
            )
        )
    )
    val subjects: StateFlow<List<SubjectItem>> = _subjects.asStateFlow()

    private val _schedules = MutableStateFlow(
        listOf(
            ScheduleItem(
                id = "sch-1",
                subject = "Advanced Web Development",
                day = "Monday",
                time = "1:00 PM - 2:30 PM",
                room = "Room 301",
                instructor = "Prof. Garcia",
                type = "Lecture",
                status = "Completed",
                colorHex = 0xFF2563EB
            ),
            ScheduleItem(
                id = "sch-2",
                subject = "Advanced Web Development",
                day = "Wednesday",
                time = "1:00 PM - 2:30 PM",
                room = "Room 301",
                instructor = "Prof. Garcia",
                type = "Lecture",
                status = "In Progress",
                colorHex = 0xFF2563EB
            ),
            ScheduleItem(
                id = "sch-3",
                subject = "Database Management",
                day = "Monday",
                time = "8:00 AM - 9:30 AM",
                room = "Room 301",
                instructor = "Prof. Reyes",
                type = "Lecture",
                status = "Completed",
                colorHex = 0xFF0D9488
            ),
            ScheduleItem(
                id = "sch-4",
                subject = "Database Management",
                day = "Wednesday",
                time = "8:00 AM - 9:30 AM",
                room = "Room 301",
                instructor = "Prof. Reyes",
                type = "Laboratory",
                status = "Completed",
                colorHex = 0xFF0D9488
            ),
            ScheduleItem(
                id = "sch-5",
                subject = "Mobile Computing",
                day = "Tuesday",
                time = "10:00 AM - 11:30 AM",
                room = "Room 205",
                instructor = "Prof. Santos",
                type = "Lecture",
                status = "Completed",
                colorHex = 0xFF7C3AED
            ),
            ScheduleItem(
                id = "sch-6",
                subject = "Mobile Computing",
                day = "Thursday",
                time = "10:00 AM - 11:30 AM",
                room = "Room 205",
                instructor = "Prof. Santos",
                type = "Online Class",
                status = "Upcoming",
                colorHex = 0xFF7C3AED
            ),
            ScheduleItem(
                id = "sch-7",
                subject = "Information Assurance",
                day = "Thursday",
                time = "3:00 PM - 4:30 PM",
                room = "Lab 2",
                instructor = "Prof. Cruz",
                type = "Laboratory",
                status = "Upcoming",
                colorHex = 0xFFEA580C
            ),
            ScheduleItem(
                id = "sch-8",
                subject = "Social and Ethical Computing",
                day = "Friday",
                time = "9:00 AM - 10:30 AM",
                room = "Room 108",
                instructor = "Prof. Lim",
                type = "Lecture",
                status = "Upcoming",
                colorHex = 0xFFDB2777
            )
        )
    )
    val schedules: StateFlow<List<ScheduleItem>> = _schedules.asStateFlow()

    private val _tasks = MutableStateFlow(
        listOf(
            TaskItem(
                id = "task-1",
                title = "Database Lab 3 Schema Design",
                subject = "Database Management",
                description = "Draft the relational mapping system and cardinal schemas for the study hub database.",
                dueDate = "Today, 11:59 PM",
                priority = "High",
                isCompleted = false,
                progress = 40,
                colorHex = 0xFF0D9488
            ),
            TaskItem(
                id = "task-2",
                title = "Quiz 3 React Routing",
                subject = "Advanced Web Development",
                description = "Complete the custom routing controller design for the model framework.",
                dueDate = "Sep 25, 2:00 PM",
                priority = "Medium",
                isCompleted = false,
                progress = 60,
                colorHex = 0xFF2563EB
            ),
            TaskItem(
                id = "task-3",
                title = "Ethics Response Paper",
                subject = "Social and Ethical Computing",
                description = "Completed peer-reviewed final report on artificial intelligence impact schemas.",
                dueDate = "Sep 28, 11:59 PM",
                priority = "Low",
                isCompleted = true,
                progress = 100,
                colorHex = 0xFFDB2777
            ),
            TaskItem(
                id = "task-4",
                title = "PHP OOP Laboratory Report",
                subject = "Advanced Web Development",
                description = "Complete the custom routing controller design for the model framework.",
                dueDate = "Sep 25, 2026",
                priority = "High",
                isCompleted = false,
                progress = 60,
                colorHex = 0xFF2563EB
            ),
            TaskItem(
                id = "task-5",
                title = "Database ER Diagram Project",
                subject = "Database Management",
                description = "Draft relational mapping system and cardinal schemas.",
                dueDate = "Sep 28, 2026",
                priority = "Medium",
                isCompleted = false,
                progress = 0,
                colorHex = 0xFF0D9488
            ),
            TaskItem(
                id = "task-6",
                title = "Network Security Case Study",
                subject = "Information Assurance",
                description = "Document firewall rulesets and potential vulnerability targets inside system infrastructure.",
                dueDate = "Sep 20, 2026",
                priority = "Overdue",
                isCompleted = false,
                progress = 40,
                colorHex = 0xFFEA580C
            ),
            TaskItem(
                id = "task-7",
                title = "Ethics Research Paper Final Draft",
                subject = "Social and Ethical Computing",
                description = "Completed peer-reviewed final report on artificial intelligence impact schemas.",
                dueDate = "Sep 18, 2026",
                priority = "Completed",
                isCompleted = true,
                progress = 100,
                colorHex = 0xFF16A34A
            )
        )
    )
    val tasks: StateFlow<List<TaskItem>> = _tasks.asStateFlow()

    private val _assessments = MutableStateFlow(
        listOf(
            AssessmentItem(
                id = "ass-1",
                subject = "Advanced Web Development",
                name = "Quiz 1 Basic ES6",
                type = "Quiz",
                score = 45.0,
                maxScore = 50.0,
                percentage = 90.0,
                weight = 10.0,
                weightedScore = 9.0,
                date = "Sep 08, 2026"
            ),
            AssessmentItem(
                id = "ass-2",
                subject = "Advanced Web Development",
                name = "Laboratory 1 CSS Layouts",
                type = "Laboratory",
                score = 88.0,
                maxScore = 100.0,
                percentage = 88.0,
                weight = 15.0,
                weightedScore = 13.2,
                date = "Sep 15, 2026"
            ),
            AssessmentItem(
                id = "ass-3",
                subject = "Advanced Web Development",
                name = "Midterm Project React App",
                type = "Project",
                score = 95.0,
                maxScore = 100.0,
                percentage = 95.0,
                weight = 25.0,
                weightedScore = 23.8,
                date = "Sep 22, 2026"
            ),
            AssessmentItem(
                id = "ass-4",
                subject = "Database Management",
                name = "Quiz 1 Relational Algebra",
                type = "Quiz",
                score = 20.0,
                maxScore = 20.0,
                percentage = 100.0,
                weight = 10.0,
                weightedScore = 10.0,
                date = "Sep 10, 2026"
            ),
            AssessmentItem(
                id = "ass-5",
                subject = "Database Management",
                name = "Database Design Phase 1",
                type = "Project",
                score = 85.0,
                maxScore = 100.0,
                percentage = 85.0,
                weight = 20.0,
                weightedScore = 17.0,
                date = "Sep 17, 2026"
            ),
            AssessmentItem(
                id = "ass-6",
                subject = "Mobile Computing",
                name = "Assg 1 Layouts",
                type = "Project",
                score = 19.0,
                maxScore = 20.0,
                percentage = 95.0,
                weight = 15.0,
                weightedScore = 14.25,
                date = "Sep 18, 2026"
            )
        )
    )
    val assessments: StateFlow<List<AssessmentItem>> = _assessments.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem(
                id = "notif-1",
                title = "Upcoming Class — Database Management",
                message = "Class starts in 30 minutes in Room 301.",
                timestamp = "25 min ago",
                category = "Classes",
                isUnread = true,
                iconType = "time",
                colorHex = 0xFF0D9488
            ),
            NotificationItem(
                id = "notif-2",
                title = "Assignment Due Tomorrow — PHP OOP Laboratory Report",
                message = "Submission close tomorrow at 11:59 PM.",
                timestamp = "1 hour ago",
                category = "Assignments",
                isUnread = true,
                iconType = "book",
                colorHex = 0xFF2563EB
            ),
            NotificationItem(
                id = "notif-3",
                title = "Study Reminder",
                message = "You planned a study session for Information Assurance at 3:00 PM.",
                timestamp = "2 hours ago",
                category = "Study",
                isUnread = true,
                iconType = "focus",
                colorHex = 0xFFEA580C
            ),
            NotificationItem(
                id = "notif-4",
                title = "Grade Posted — Quiz 3",
                message = "Your graded quiz score is available for Advanced Web Development.",
                timestamp = "Yesterday",
                category = "Classes",
                isUnread = false,
                iconType = "grade",
                colorHex = 0xFF2563EB
            ),
            NotificationItem(
                id = "notif-5",
                title = "Exam Reminder — Midterm Exam",
                message = "Midterm exam scheduled for Mobile Computing on Oct 10.",
                timestamp = "2 days ago",
                category = "Exams",
                isUnread = false,
                iconType = "calendar",
                colorHex = 0xFFDC2626
            ),
            NotificationItem(
                id = "notif-6",
                title = "Study Streak! — 12 Days",
                message = "Incredible effort! You've maintained your 12-day study streak.",
                timestamp = "3 days ago",
                category = "Study",
                isUnread = false,
                iconType = "star",
                colorHex = 0xFF16A34A
            ),
            NotificationItem(
                id = "notif-7",
                title = "Overdue Task — Ethics Research Paper",
                message = "The submission deadline was yesterday. Submit as soon as possible.",
                timestamp = "Yesterday",
                category = "Assignments",
                isUnread = true,
                isUrgent = true,
                iconType = "warning",
                colorHex = 0xFFDC2626
            ),
            NotificationItem(
                id = "notif-8",
                title = "System Update Complete",
                message = "Figma integration & Timer modes improved in v2.4.",
                timestamp = "4 days ago",
                category = "System",
                isUnread = false,
                iconType = "gear",
                colorHex = 0xFF64748B
            ),
            NotificationItem(
                id = "notif-9",
                title = "Profile Verified",
                message = "Your academic institutional email registration has been verified.",
                timestamp = "5 days ago",
                category = "System",
                isUnread = false,
                iconType = "person",
                colorHex = 0xFF0D9488
            ),
            NotificationItem(
                id = "notif-10",
                title = "Timetable Published",
                message = "First semester elective classes timetable updated.",
                timestamp = "1 week ago",
                category = "Classes",
                isUnread = false,
                iconType = "time",
                colorHex = 0xFF7C3AED
            )
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _calendarEvents = MutableStateFlow(
        listOf(
            DayEvent(
                id = "ev-1",
                dayOfMonth = 2,
                time = "1:00 PM",
                title = "1:00 PM Web Dev",
                type = "Class",
                description = "Advanced Web Development Lecture",
                colorHex = 0xFF2563EB
            ),
            DayEvent(
                id = "ev-2",
                dayOfMonth = 4,
                time = "All Day",
                title = "Ethics Essay",
                type = "Assignment",
                description = "Social and Ethical Computing Essay Due",
                colorHex = 0xFFDB2777
            ),
            DayEvent(
                id = "ev-3",
                dayOfMonth = 9,
                time = "3:00 PM",
                title = "3:00 PM Study",
                type = "Study Focus",
                description = "Study Session on Information Assurance",
                colorHex = 0xFF0D9488
            ),
            DayEvent(
                id = "ev-4",
                dayOfMonth = 15,
                time = "10:00 AM",
                title = "10 AM Mob Comp",
                type = "Class",
                description = "Mobile Computing Lecture",
                colorHex = 0xFF7C3AED
            ),
            DayEvent(
                id = "ev-5",
                dayOfMonth = 18,
                time = "All Day",
                title = "Lab Report Due",
                type = "Assignment",
                description = "Information Assurance Lab Submission",
                colorHex = 0xFFEA580C
            ),
            DayEvent(
                id = "ev-6",
                dayOfMonth = 23,
                time = "8:00 AM - 9:30 AM",
                title = "Database Systems Midterm",
                type = "Exam",
                description = "Covers SQL joins, Indexing and normalization schemas.",
                colorHex = 0xFFDC2626
            ),
            DayEvent(
                id = "ev-7",
                dayOfMonth = 23,
                time = "1:00 PM - 2:30 PM",
                title = "Advanced Web Development",
                type = "Class",
                description = "PHP OOP concepts and dynamic MVC frameworks.",
                colorHex = 0xFF2563EB
            ),
            DayEvent(
                id = "ev-8",
                dayOfMonth = 23,
                time = "4:00 PM - 5:30 PM",
                title = "Weekly Peer Review Session",
                type = "Study Focus",
                description = "Discussing Mobile UI wireframe designs and patterns.",
                colorHex = 0xFF0D9488
            ),
            DayEvent(
                id = "ev-9",
                dayOfMonth = 25,
                time = "All Day",
                title = "Security Presentation",
                type = "Assignment",
                description = "Group defense in Room 301",
                colorHex = 0xFFEA580C
            )
        )
    )
    val calendarEvents: StateFlow<List<DayEvent>> = _calendarEvents.asStateFlow()

    private val _studentProfile = MutableStateFlow(StudentProfile())
    val studentProfile: StateFlow<StudentProfile> = _studentProfile.asStateFlow()

    private val _focusTimerState = MutableStateFlow(FocusTimerState())
    val focusTimerState: StateFlow<FocusTimerState> = _focusTimerState.asStateFlow()

    private val _appSettings = MutableStateFlow(AppSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings.asStateFlow()

    fun toggleTask(taskId: String) {
        _tasks.update { list ->
            list.map { task ->
                if (task.id == taskId) {
                    val updatedStatus = !task.isCompleted
                    task.copy(
                        isCompleted = updatedStatus,
                        progress = if (updatedStatus) 100 else 0,
                        priority = if (updatedStatus) "Completed" else "Medium"
                    )
                } else task
            }
        }
    }

    fun addTask(task: TaskItem) {
        _tasks.update { listOf(task) + it }
    }

    fun deleteTask(taskId: String) {
        _tasks.update { list -> list.filterNot { it.id == taskId } }
    }

    fun addSubject(subject: SubjectItem) {
        _subjects.update { it + subject }
    }

    fun addSchedule(schedule: ScheduleItem) {
        _schedules.update { it + schedule }
    }

    fun deleteSchedule(scheduleId: String) {
        _schedules.update { list -> list.filterNot { it.id == scheduleId } }
    }

    fun addAssessment(assessment: AssessmentItem) {
        _assessments.update { listOf(assessment) + it }
    }

    fun markAllNotificationsRead() {
        _notifications.update { list -> list.map { it.copy(isUnread = false) } }
    }

    fun dismissNotification(id: String) {
        _notifications.update { list -> list.filterNot { it.id == id } }
    }

    fun updateTimerTick() {
        _focusTimerState.update { current ->
            if (current.isRunning && current.remainingSeconds > 0) {
                current.copy(remainingSeconds = current.remainingSeconds - 1)
            } else if (current.isRunning && current.remainingSeconds == 0) {
                current.copy(
                    isRunning = false,
                    sessionsCompletedToday = current.sessionsCompletedToday + 1,
                    todayStudyHours = current.todayStudyHours + (current.totalSeconds / 3600.0)
                )
            } else {
                current
            }
        }
    }

    fun startTimer() {
        _focusTimerState.update { it.copy(isRunning = true) }
    }

    fun pauseTimer() {
        _focusTimerState.update { it.copy(isRunning = false) }
    }

    fun resetTimer() {
        _focusTimerState.update {
            it.copy(isRunning = false, remainingSeconds = it.totalSeconds)
        }
    }

    fun setTimerMode(modeName: String, durationMinutes: Int) {
        val totalSecs = durationMinutes * 60
        _focusTimerState.update {
            it.copy(
                modeName = modeName,
                totalSeconds = totalSecs,
                remainingSeconds = totalSecs,
                isRunning = false
            )
        }
    }

    fun updateFocusGoal(subject: String, goal: String) {
        _focusTimerState.update {
            it.copy(currentSubject = subject, focusGoal = goal)
        }
    }

    fun updateSettings(settings: AppSettings) {
        _appSettings.value = settings
    }

    fun updateStudentProfile(profile: StudentProfile) {
        _studentProfile.value = profile
    }
}
