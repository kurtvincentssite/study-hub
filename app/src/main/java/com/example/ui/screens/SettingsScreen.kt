package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppSettings
import com.example.data.StudentProfile
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentGreenLight
import com.example.ui.theme.StudyHubTeal
import com.example.ui.theme.StudyHubTextPrimary
import com.example.ui.theme.StudyHubTextSecondary

@Composable
fun SettingsScreen(
    settings: AppSettings,
    profile: StudentProfile,
    onUpdateSettings: (AppSettings) -> Unit,
    onUpdateProfile: (StudentProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTheme = settings.themeMode
    var classReminders by remember(settings.classReminders) { mutableStateOf(settings.classReminders) }
    var taskDeadlines by remember(settings.taskDeadlines) { mutableStateOf(settings.taskDeadlines) }
    var examReminders by remember { mutableStateOf(true) }
    var studyReminders by remember { mutableStateOf(false) }

    var studyDuration by remember { mutableStateOf(settings.defaultStudyMinutes.toString()) }
    var breakDuration by remember { mutableStateOf(settings.defaultBreakMinutes.toString()) }
    var autoStartBreak by remember(settings.autoStartBreak) { mutableStateOf(settings.autoStartBreak) }

    // Account Management States
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }
    var isSignedOut by remember { mutableStateOf(false) }
    var statusFeedbackMessage by remember { mutableStateOf<String?>(null) }

    val outlineColor = MaterialTheme.colorScheme.outline
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val userInitials = profile.displayAccountName
        .split(" ")
        .filter { it.isNotBlank() }
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")
        .ifEmpty { "AM" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Title & Subtitle
        Column {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "App & Account Preferences",
                fontSize = 12.sp,
                color = onSurfaceVariant
            )
        }

        // Feedback Banner
        if (statusFeedbackMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDCFCE7))
                    .border(1.dp, Color(0xFF86EFAC), RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .testTag("account_feedback_banner")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = AccentGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = statusFeedbackMessage ?: "",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF166534)
                        )
                    }
                    IconButton(
                        onClick = { statusFeedbackMessage = null },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = Color(0xFF166534),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        // Appearance Mode Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_appearance_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Appearance Mode",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = onSurface
                )

                AppearanceRadioCard(
                    title = "Light Mode",
                    subtitle = "Standard off-white background with high legibility",
                    icon = Icons.Default.LightMode,
                    isSelected = selectedTheme == "Light",
                    showActiveBadge = selectedTheme == "Light",
                    onClick = { onUpdateSettings(settings.copy(themeMode = "Light")) },
                    isDarkCard = false
                )

                AppearanceRadioCard(
                    title = "Dark Mode",
                    subtitle = "Easier on the eyes in low light conditions",
                    icon = Icons.Default.DarkMode,
                    isSelected = selectedTheme == "Dark",
                    showActiveBadge = selectedTheme == "Dark",
                    onClick = { onUpdateSettings(settings.copy(themeMode = "Dark")) },
                    isDarkCard = true
                )

                AppearanceRadioCard(
                    title = "System Default",
                    subtitle = "Automatically sync system color scheme",
                    icon = Icons.Default.BrightnessAuto,
                    isSelected = selectedTheme == "System",
                    showActiveBadge = selectedTheme == "System",
                    onClick = { onUpdateSettings(settings.copy(themeMode = "System")) },
                    isDarkCard = false
                )
            }
        }

        // Notification Preferences Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_notifications_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column {
                    Text(
                        text = "Notification Preferences",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Manage how and where StudyHub alerts you",
                        fontSize = 11.sp,
                        color = onSurfaceVariant
                    )
                }

                SettingToggleRow(
                    title = "Class Reminders",
                    subtitle = "Alert me 30 minutes before any class starts",
                    checked = classReminders,
                    onCheckedChange = {
                        classReminders = it
                        onUpdateSettings(settings.copy(classReminders = it))
                    }
                )

                SettingToggleRow(
                    title = "Assignment Reminders",
                    subtitle = "Notify me 24 hours prior to deadline closures",
                    checked = taskDeadlines,
                    onCheckedChange = {
                        taskDeadlines = it
                        onUpdateSettings(settings.copy(taskDeadlines = it))
                    }
                )

                SettingToggleRow(
                    title = "Exam Reminders",
                    subtitle = "Weekly alert digests of upcoming midterms or final exams",
                    checked = examReminders,
                    onCheckedChange = { examReminders = it }
                )

                SettingToggleRow(
                    title = "Study Reminders",
                    subtitle = "Push alert updates of planned target hour slots",
                    checked = studyReminders,
                    onCheckedChange = { studyReminders = it }
                )
            }
        }

        // Study Timer Defaults Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_timer_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Study Timer Defaults",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Study Duration (mins)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = studyDuration,
                            onValueChange = {
                                studyDuration = it
                                it.toIntOrNull()?.let { mins ->
                                    onUpdateSettings(settings.copy(defaultStudyMinutes = mins))
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = StudyHubTeal,
                                unfocusedBorderColor = outlineColor
                            ),
                            modifier = Modifier.fillMaxWidth().height(52.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Break Duration (mins)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = breakDuration,
                            onValueChange = {
                                breakDuration = it
                                it.toIntOrNull()?.let { mins ->
                                    onUpdateSettings(settings.copy(defaultBreakMinutes = mins))
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = StudyHubTeal,
                                unfocusedBorderColor = outlineColor
                            ),
                            modifier = Modifier.fillMaxWidth().height(52.dp)
                        )
                    }
                }

                SettingToggleRow(
                    title = "Auto-start Break Mode",
                    subtitle = "Transition to break timer immediately when focus slots end",
                    checked = autoStartBreak,
                    onCheckedChange = {
                        autoStartBreak = it
                        onUpdateSettings(settings.copy(autoStartBreak = it))
                    }
                )
            }
        }

        // Account Management Card (Fully Interactive)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_account_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Account Management",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = onSurface
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSignedOut) Color(0xFFF1F5F9) else AccentGreenLight)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isSignedOut) "Signed Out" else "Active Session",
                            fontSize = 10.sp,
                            color = if (isSignedOut) onSurfaceVariant else AccentGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (isSignedOut) {
                    // Signed out state with one-tap Re-authenticate
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "You are currently signed out.",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Sign in to sync assignments and manage academic profiles.",
                                fontSize = 11.sp,
                                color = onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = {
                                    isSignedOut = false
                                    statusFeedbackMessage = "Signed in as ${profile.displayAccountName}"
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("btn_reauthenticate")
                            ) {
                                Text("Sign In as ${profile.displayAccountName}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                } else {
                    // Connected User Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0F172A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(userInitials, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = profile.displayAccountName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = onSurface
                            )
                            Text(
                                text = profile.email,
                                fontSize = 11.sp,
                                color = onSurfaceVariant
                            )
                            Text(
                                text = "${profile.degree} • ${profile.studentId}",
                                fontSize = 10.sp,
                                color = onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Working Action Buttons
                    Button(
                        onClick = { showEditProfileDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_edit_academic_info")
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Academic Info", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = { showChangePasswordDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, outlineColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_change_password")
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = onSurface, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Change Password", color = onSurface, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { showSignOutDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFEE2E2),
                            contentColor = Color(0xFFDC2626)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_sign_out")
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign Out of Hub", color = Color(0xFFDC2626), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }

    // Edit Academic Info Dialog
    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(profile.displayAccountName) }
        var editEmail by remember { mutableStateOf(profile.email) }
        var editStudentId by remember { mutableStateOf(profile.studentId) }
        var editDegree by remember { mutableStateOf(profile.degree) }
        var editYearLevel by remember { mutableStateOf(profile.yearLevel) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text("Edit Academic Info", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = onSurface)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Update your student and university registration details.", fontSize = 12.sp, color = onSurfaceVariant)

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Display Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_name")
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("University Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_email")
                    )

                    OutlinedTextField(
                        value = editStudentId,
                        onValueChange = { editStudentId = it },
                        label = { Text("Student ID") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_id")
                    )

                    OutlinedTextField(
                        value = editDegree,
                        onValueChange = { editDegree = it },
                        label = { Text("Degree / Program") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_degree")
                    )

                    OutlinedTextField(
                        value = editYearLevel,
                        onValueChange = { editYearLevel = it },
                        label = { Text("Year & Semester") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("input_edit_year")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = profile.copy(
                            displayAccountName = editName.ifBlank { profile.displayAccountName },
                            name = editName.ifBlank { profile.name },
                            email = editEmail.ifBlank { profile.email },
                            studentId = editStudentId.ifBlank { profile.studentId },
                            degree = editDegree.ifBlank { profile.degree },
                            yearLevel = editYearLevel.ifBlank { profile.yearLevel }
                        )
                        onUpdateProfile(updated)
                        showEditProfileDialog = false
                        statusFeedbackMessage = "Academic profile updated successfully!"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                    modifier = Modifier.testTag("btn_save_academic_info")
                ) {
                    Text("Save Changes", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showEditProfileDialog = false }) {
                    Text("Cancel", color = onSurface)
                }
            }
        )
    }

    // Change Password Dialog
    if (showChangePasswordDialog) {
        var currentPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var showPasswordVisibility by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = {
                Text("Change Password", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = onSurface)
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Enter your current password and choose a secure new password.", fontSize = 12.sp, color = onSurfaceVariant)

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            fontSize = 11.sp,
                            color = Color(0xFFDC2626),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = {
                            currentPassword = it
                            errorMessage = null
                        },
                        label = { Text("Current Password") },
                        singleLine = true,
                        visualTransformation = if (showPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPasswordVisibility = !showPasswordVisibility }) {
                                Icon(
                                    if (showPasswordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().testTag("input_current_password")
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            errorMessage = null
                        },
                        label = { Text("New Password (min 6 chars)") },
                        singleLine = true,
                        visualTransformation = if (showPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().testTag("input_new_password")
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            errorMessage = null
                        },
                        label = { Text("Confirm New Password") },
                        singleLine = true,
                        visualTransformation = if (showPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().testTag("input_confirm_password")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        when {
                            currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                                errorMessage = "Please fill in all password fields."
                            }
                            newPassword.length < 6 -> {
                                errorMessage = "New password must be at least 6 characters."
                            }
                            newPassword != confirmPassword -> {
                                errorMessage = "New passwords do not match."
                            }
                            else -> {
                                showChangePasswordDialog = false
                                statusFeedbackMessage = "Password changed successfully!"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StudyHubTeal),
                    modifier = Modifier.testTag("btn_confirm_change_password")
                ) {
                    Text("Update Password", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel", color = onSurface)
                }
            }
        )
    }

    // Sign Out Confirmation Dialog
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = {
                Text("Sign Out of StudyHub", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = onSurface)
            },
            text = {
                Text(
                    text = "Are you sure you want to sign out of ${profile.displayAccountName}'s account (${profile.email})? Offline records and timer preferences are preserved.",
                    fontSize = 13.sp,
                    color = onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSignOutDialog = false
                        isSignedOut = true
                        statusFeedbackMessage = "Signed out of Hub."
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    modifier = Modifier.testTag("btn_confirm_sign_out")
                ) {
                    Text("Sign Out", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSignOutDialog = false }) {
                    Text("Cancel", color = onSurface)
                }
            }
        )
    }
}

@Composable
private fun AppearanceRadioCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    showActiveBadge: Boolean,
    onClick: () -> Unit,
    isDarkCard: Boolean
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val cardBg = when {
        isDarkCard -> Color(0xFF1E293B)
        isSelected -> Color(0xFFF0FDF4)
        else -> MaterialTheme.colorScheme.surface
    }

    val cardBorder = when {
        isSelected -> AccentGreen
        isDarkCard -> Color(0xFF334155)
        else -> outlineColor
    }

    val textColor = if (isDarkCard) Color.White else onSurface
    val subtextColor = if (isDarkCard) Color(0xFF94A3B8) else onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.5.dp, cardBorder, RoundedCornerShape(12.dp))
            .background(cardBg)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (isSelected) AccentGreen else (if (isDarkCard) Color(0xFF94A3B8) else onSurfaceVariant),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = subtextColor
                    )
                }
            }

            if (showActiveBadge) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(AccentGreenLight)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("Active", fontSize = 10.sp, color = AccentGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = onSurface)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, fontSize = 11.sp, color = onSurfaceVariant)
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = StudyHubTeal,
                uncheckedTrackColor = Color(0xFFCBD5E1)
            )
        )
    }
}
