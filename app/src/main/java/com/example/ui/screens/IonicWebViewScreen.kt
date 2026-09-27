package com.example.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.NavDestination
import com.example.data.TaskItem
import com.example.ui.theme.StudyHubTeal
import com.example.ui.viewmodel.StudyHubViewModel
import org.json.JSONArray
import org.json.JSONObject

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun IonicWebViewScreen(
    viewModel: StudyHubViewModel,
    onNavigateBack: () -> Unit = { viewModel.navigateTo(NavDestination.DASHBOARD) },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    val mainHandler = remember { Handler(Looper.getMainLooper()) }

    val studentProfile by viewModel.studentProfile.collectAsStateWithLifecycle()
    val subjects by viewModel.subjects.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    BackHandler {
        onNavigateBack()
    }

    // Helper to serialize current state to JSON string
    fun buildPayloadJson(): String {
        val root = JSONObject()

        // Profile
        val profObj = JSONObject().apply {
            put("name", studentProfile.name)
            put("email", studentProfile.email)
            put("studentId", studentProfile.studentId)
            put("degree", studentProfile.degree)
            put("yearAndSemester", studentProfile.yearLevel)
            put("currentGpa", studentProfile.gpa)
        }
        root.put("profile", profObj)

        // Subjects
        val subsArray = JSONArray()
        subjects.forEach { s ->
            val obj = JSONObject().apply {
                put("id", s.id)
                put("code", s.code)
                put("title", s.title)
                put("instructor", s.instructor)
                put("units", s.units)
                put("averageGrade", s.averageGrade)
                put("room", s.room)
            }
            subsArray.put(obj)
        }
        root.put("subjects", subsArray)

        // Tasks
        val tasksArray = JSONArray()
        tasks.forEach { t ->
            val obj = JSONObject().apply {
                put("id", t.id)
                put("title", t.title)
                put("subject", t.subject)
                put("dueDate", t.dueDate)
                put("priority", t.priority)
                put("isCompleted", t.isCompleted)
            }
            tasksArray.put(obj)
        }
        root.put("tasks", tasksArray)

        return root.toString()
    }

    // Push state updates from ViewModel down to JavaScript
    LaunchedEffect(studentProfile, subjects, tasks) {
        webViewInstance?.let { wv ->
            val json = buildPayloadJson()
            val escaped = json.replace("\\", "\\\\").replace("'", "\\'")
            wv.evaluateJavascript("if (window.updateFromAndroid) { window.updateFromAndroid('$escaped'); }", null)
        }
    }

    // JavaScript Interface to connect Ionic JS with Android Kotlin
    class AndroidBridge(private val bridgeContext: Context) {
        @JavascriptInterface
        fun getStudentData(): String {
            return buildPayloadJson()
        }

        @JavascriptInterface
        fun toggleTask(taskId: String) {
            mainHandler.post {
                viewModel.toggleTask(taskId)
            }
        }

        @JavascriptInterface
        fun addTask(title: String, subject: String, dueDate: String, priority: String) {
            mainHandler.post {
                val newTask = TaskItem(
                    id = "task-${System.currentTimeMillis()}",
                    title = title,
                    subject = subject,
                    dueDate = dueDate,
                    priority = priority,
                    colorHex = 0xFF0F766E
                )
                viewModel.addTask(newTask)
            }
        }

        @JavascriptInterface
        fun updateAcademicProfile(name: String, email: String, studentId: String, degree: String, semester: String) {
            mainHandler.post {
                val updated = studentProfile.copy(
                    name = name,
                    displayAccountName = name,
                    email = email,
                    studentId = studentId,
                    degree = degree,
                    yearLevel = semester
                )
                viewModel.updateStudentProfile(updated)
            }
        }

        @JavascriptInterface
        fun showToast(message: String) {
            mainHandler.post {
                Toast.makeText(bridgeContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        @JavascriptInterface
        fun onIonicLoaded() {
            mainHandler.post {
                // Initial sync confirmation
                val json = buildPayloadJson()
                val escaped = json.replace("\\", "\\\\").replace("'", "\\'")
                webViewInstance?.evaluateJavascript("if (window.updateFromAndroid) { window.updateFromAndroid('$escaped'); }", null)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("ionic_webview_screen")
    ) {
        // Top Toolbar Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(StudyHubTeal.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Ionic Code Badge",
                            tint = StudyHubTeal,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Ionic Web Suite",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "HTML5 • CSS3 • JavaScript (Ionic Web Components)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            webViewInstance?.let { wv ->
                                val json = buildPayloadJson()
                                val escaped = json.replace("\\", "\\\\").replace("'", "\\'")
                                wv.evaluateJavascript("if (window.updateFromAndroid) { window.updateFromAndroid('$escaped'); }", null)
                                Toast.makeText(context, "Synced Native -> Ionic", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.testTag("btn_sync_ionic")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync Data",
                            tint = StudyHubTeal
                        )
                    }

                    IconButton(
                        onClick = {
                            webViewInstance?.reload()
                        },
                        modifier = Modifier.testTag("btn_reload_ionic")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reload Ionic App",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Embedded WebView running Ionic (HTML/CSS/JS)
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .testTag("ionic_native_webview"),
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = true
                    settings.allowContentAccess = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true

                    webChromeClient = WebChromeClient()
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            // Initial data push
                            val json = buildPayloadJson()
                            val escaped = json.replace("\\", "\\\\").replace("'", "\\'")
                            view?.evaluateJavascript("if (window.updateFromAndroid) { window.updateFromAndroid('$escaped'); }", null)
                        }
                    }

                    addJavascriptInterface(AndroidBridge(ctx), "AndroidBridge")
                    loadUrl("file:///android_asset/www/index.html")
                    webViewInstance = this
                }
            },
            update = { wv ->
                webViewInstance = wv
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewInstance?.destroy()
            webViewInstance = null
        }
    }
}
