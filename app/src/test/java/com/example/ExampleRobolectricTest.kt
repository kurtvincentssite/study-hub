package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.viewmodel.StudyHubViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("StudyHub", appName)
    }

    @Test
    fun `theme mode toggle and updates`() {
        val viewModel = StudyHubViewModel()
        
        // Initial state is Light
        assertEquals("Light", viewModel.appSettings.value.themeMode)

        // Toggle to Dark
        viewModel.toggleThemeMode()
        assertEquals("Dark", viewModel.appSettings.value.themeMode)

        // Toggle back to Light
        viewModel.toggleThemeMode()
        assertEquals("Light", viewModel.appSettings.value.themeMode)

        // Explicitly set to System
        viewModel.setThemeMode("System")
        assertEquals("System", viewModel.appSettings.value.themeMode)
    }

    @Test
    fun `task completion toggle`() {
        val viewModel = StudyHubViewModel()
        val firstTask = viewModel.tasks.value.first()
        val initialStatus = firstTask.isCompleted

        viewModel.toggleTask(firstTask.id)
        val updatedTask = viewModel.tasks.value.first { it.id == firstTask.id }
        assertEquals(!initialStatus, updatedTask.isCompleted)
    }
}
