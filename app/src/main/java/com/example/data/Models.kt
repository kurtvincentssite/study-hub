package com.example.data

enum class NavDestination(val label: String, val title: String) {
    DASHBOARD("Dashboard", "Dashboard"),
    SUBJECTS("Subjects", "Subjects"),
    CALENDAR("Calendar", "Calendar"),
    SCHEDULE("Schedule", "Class Schedule"),
    TIMETABLE("Timetable", "Weekly Timetable"),
    TASKS("Tasks", "Tasks & Assignments"),
    GRADES("Grades", "Academic Grades"),
    STUDY_FOCUS("Study Focus", "Study Focus Session"),
    TIMER("Timer", "Timer"),
    PROGRESS("Progress & Statistics", "Progress & Statistics"),
    NOTIFICATIONS("Notifications", "Notifications"),
    PROFILE("Profile", "My Profile"),
    SETTINGS("Settings", "Settings"),
    IONIC_HUB("Ionic HTML/JS", "Ionic Web Suite")
}

data class SubjectItem(
    val id: String,
    val code: String,
    val title: String,
    val instructor: String,
    val room: String,
    val units: Int = 3,
    val averageGrade: Double,
    val studyTimeHours: Double,
    val classesPerWeek: Int,
    val colorHex: Long,
    val status: String = "Active",
    val semester: String = "1st Sem 2026-2027"
)

data class ScheduleItem(
    val id: String,
    val subject: String,
    val day: String,
    val time: String,
    val room: String,
    val instructor: String,
    val type: String, // Lecture, Laboratory, Online Class, Consultation, Exam
    val status: String = "Upcoming", // Completed, In Progress, Upcoming
    val colorHex: Long
)

data class TaskItem(
    val id: String,
    val title: String,
    val subject: String,
    val description: String = "",
    val dueDate: String,
    val priority: String, // High, Medium, Low, Overdue, Completed
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val colorHex: Long
)

data class AssessmentItem(
    val id: String,
    val subject: String,
    val name: String,
    val type: String, // Quiz, Laboratory, Project, Exam
    val score: Double,
    val maxScore: Double,
    val percentage: Double,
    val weight: Double,
    val weightedScore: Double,
    val date: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val category: String, // Classes, Assignments, Exams, Study, System
    val isUnread: Boolean = true,
    val isUrgent: Boolean = false,
    val iconType: String = "calendar",
    val colorHex: Long = 0xFF2563EB
)

data class StudentProfile(
    val name: String = "Student User",
    val displayAccountName: String = "Alex Mercer",
    val email: String = "alex.m@university.edu",
    val studentId: String = "2024-CS-0419",
    val degree: String = "BS in Computer Science",
    val yearLevel: String = "3rd Year • Semester 1",
    val overallAverage: Double = 88.5,
    val gpa: Double = 3.45,
    val enrolledSubjectsCount: Int = 5,
    val totalStudyHours: Double = 156.5,
    val completedAssignmentsCount: Int = 15,
    val totalAssignmentsCount: Int = 23,
    val studyStreakDays: Int = 12
)

data class FocusTimerState(
    val currentSubject: String = "Advanced Web Development",
    val focusGoal: String = "Review PHP OOP concepts and MVC architectures",
    val totalSeconds: Int = 25 * 60,
    val remainingSeconds: Int = 25 * 60,
    val isRunning: Boolean = false,
    val modeName: String = "Pomodoro",
    val todayStudyHours: Double = 2.5,
    val sessionsCompletedToday: Int = 3,
    val targetSessionsToday: Int = 4,
    val focusStreakDays: Int = 12
)

data class AppSettings(
    val themeMode: String = "Light", // Light, Dark, System
    val classReminders: Boolean = true,
    val taskDeadlines: Boolean = true,
    val breakReminders: Boolean = true,
    val ambientSoundEnabled: Boolean = false,
    val defaultStudyMinutes: Int = 25,
    val defaultBreakMinutes: Int = 5,
    val autoStartBreak: Boolean = false
)

data class DayEvent(
    val id: String,
    val dayOfMonth: Int,
    val time: String,
    val title: String,
    val type: String, // Exam, Class, Study Focus, Assignment
    val description: String,
    val colorHex: Long
)
