// StudyHub Ionic Suite - Modern HTML5, CSS3 & JavaScript Engine

// App State
let state = {
  profile: {
    name: "Alex Morgan",
    email: "alex.morgan@university.edu",
    studentId: "STU-2026-8891",
    degree: "B.S. Computer Science",
    yearAndSemester: "3rd Year • 1st Semester",
    currentGpa: 3.84,
    avatarUrl: ""
  },
  subjects: [
    { id: "sub-1", code: "CS 301", title: "Data Structures & Algorithms", instructor: "Dr. Evelyn Reed", units: 4, averageGrade: 94.5, room: "Sci-204" },
    { id: "sub-2", code: "CS 305", title: "Database Systems", instructor: "Prof. Michael Chang", units: 3, averageGrade: 91.0, room: "Tech-110" },
    { id: "sub-3", code: "MATH 240", title: "Discrete Mathematics", instructor: "Dr. Sarah Jenkins", units: 3, averageGrade: 88.5, room: "Hall-B" },
    { id: "sub-4", code: "ENG 210", title: "Technical Communication", instructor: "Prof. David Miller", units: 3, averageGrade: 96.0, room: "Arts-102" }
  ],
  tasks: [
    { id: "task-1", title: "Implement Red-Black Tree in C++", subject: "CS 301", dueDate: "Today, 11:59 PM", priority: "High", isCompleted: false },
    { id: "task-2", title: "SQL Schema & Normalization Assignment", subject: "CS 305", dueDate: "Tomorrow, 5:00 PM", priority: "High", isCompleted: false },
    { id: "task-3", title: "Read Chapter 4: Proof by Induction", subject: "MATH 240", dueDate: "Friday", priority: "Medium", isCompleted: true },
    { id: "task-4", title: "Draft Engineering Ethics Case Study", subject: "ENG 210", dueDate: "Next Monday", priority: "Low", isCompleted: false }
  ],
  timer: {
    durationSeconds: 25 * 60,
    remainingSeconds: 25 * 60,
    isRunning: false,
    intervalId: null,
    mode: "focus" // focus, break, long-break
  }
};

// Check if Android Native Javascript Bridge is available
function isAndroidAvailable() {
  return typeof window.AndroidBridge !== "undefined";
}

// Initialize App
document.addEventListener("DOMContentLoaded", () => {
  setupNavigation();
  setupTimer();
  loadData();
  renderAll();

  // Notify Android that Ionic UI is ready
  if (isAndroidAvailable() && window.AndroidBridge.onIonicLoaded) {
    window.AndroidBridge.onIonicLoaded();
  }
});

// Load Data from Native Android or fallback
function loadData() {
  if (isAndroidAvailable() && window.AndroidBridge.getStudentData) {
    try {
      const raw = window.AndroidBridge.getStudentData();
      if (raw) {
        const parsed = JSON.parse(raw);
        if (parsed.profile) state.profile = { ...state.profile, ...parsed.profile };
        if (parsed.subjects && parsed.subjects.length > 0) state.subjects = parsed.subjects;
        if (parsed.tasks && parsed.tasks.length > 0) state.tasks = parsed.tasks;
      }
    } catch (e) {
      console.warn("Failed to parse native data:", e);
    }
  }
}

// Navigation Segment Switching
function setupNavigation() {
  const chips = document.querySelectorAll(".segment-chip");
  chips.forEach(chip => {
    chip.addEventListener("click", () => {
      const targetTab = chip.getAttribute("data-tab");
      chips.forEach(c => c.classList.remove("active"));
      chip.classList.add("active");

      document.querySelectorAll(".tab-content-container").forEach(tab => {
        tab.classList.remove("active");
      });

      const activeTabEl = document.getElementById(`tab-${targetTab}`);
      if (activeTabEl) {
        activeTabEl.classList.add("active");
      }
    });
  });
}

// Render Master Coordinator
function renderAll() {
  renderHeroAndMetrics();
  renderTasks();
  renderSubjects();
  renderProfile();
}

// Render Hero Banner & Metric Cards
function renderHeroAndMetrics() {
  // Hero
  const nameEl = document.getElementById("hero-student-name");
  const degreeEl = document.getElementById("hero-degree-text");
  const avatarEl = document.getElementById("hero-avatar-initials");
  const gpaValEl = document.getElementById("hero-gpa-val");
  const pendingCountEl = document.getElementById("hero-pending-count");
  const subjectsCountEl = document.getElementById("hero-subjects-count");

  const initials = state.profile.name
    .split(" ")
    .map(n => n[0])
    .join("")
    .substring(0, 2)
    .toUpperCase();

  if (nameEl) nameEl.textContent = state.profile.name;
  if (degreeEl) degreeEl.textContent = `${state.profile.degree} • ${state.profile.yearAndSemester}`;
  if (avatarEl) avatarEl.textContent = initials;
  if (gpaValEl) gpaValEl.textContent = state.profile.currentGpa.toFixed(2);

  const pendingTasks = state.tasks.filter(t => !t.isCompleted).length;
  if (pendingCountEl) pendingCountEl.textContent = pendingTasks;
  if (subjectsCountEl) subjectsCountEl.textContent = state.subjects.length;

  // Metric Cards
  const metricGpa = document.getElementById("metric-gpa-number");
  const metricTasks = document.getElementById("metric-tasks-number");
  const metricSubjects = document.getElementById("metric-subjects-number");

  if (metricGpa) metricGpa.textContent = state.profile.currentGpa.toFixed(2);
  if (metricTasks) metricTasks.textContent = pendingTasks;
  if (metricSubjects) metricSubjects.textContent = state.subjects.length;
}

// Render Task List
function renderTasks() {
  const container = document.getElementById("tasks-list-container");
  if (!container) return;

  container.innerHTML = "";

  if (state.tasks.length === 0) {
    container.innerHTML = `<div style="text-align:center; padding: 24px; color: var(--app-text-secondary);">No tasks created yet. Tap "+ Add Task" to create one!</div>`;
    return;
  }

  state.tasks.forEach(task => {
    const item = document.createElement("div");
    item.className = `task-card-item ${task.isCompleted ? "completed" : ""}`;
    item.setAttribute("data-id", task.id);

    const priorityClass = (task.priority || "Medium").toLowerCase();

    item.innerHTML = `
      <div class="task-checkbox-wrap">
        <div class="task-custom-checkbox">
          ${task.isCompleted ? "✓" : ""}
        </div>
      </div>
      <div class="task-content-wrap">
        <div class="task-title-text">${escapeHtml(task.title)}</div>
        <div class="task-meta-row">
          <span class="badge-subject">${escapeHtml(task.subject)}</span>
          <span class="badge-priority ${priorityClass}">${escapeHtml(task.priority || "Medium")}</span>
          <span class="task-due-date">📅 ${escapeHtml(task.dueDate || "No due date")}</span>
        </div>
      </div>
    `;

    item.addEventListener("click", () => {
      toggleTask(task.id);
    });

    container.appendChild(item);
  });
}

// Toggle Task Completion (Bidirectional Native Sync)
function toggleTask(taskId) {
  const task = state.tasks.find(t => t.id === taskId);
  if (task) {
    task.isCompleted = !task.isCompleted;
    renderTasks();
    renderHeroAndMetrics();

    if (isAndroidAvailable() && window.AndroidBridge.toggleTask) {
      window.AndroidBridge.toggleTask(taskId);
    }
    showToast(task.isCompleted ? "Task completed! 🎉" : "Task marked pending");
  }
}

// Render Subjects
function renderSubjects() {
  const container = document.getElementById("subjects-grid-container");
  if (!container) return;

  container.innerHTML = "";

  state.subjects.forEach(sub => {
    const card = document.createElement("div");
    card.className = "subject-card";
    card.innerHTML = `
      <div class="subject-card-top">
        <span class="subject-code-chip">${escapeHtml(sub.code)}</span>
        <span class="subject-grade-chip">${sub.averageGrade ? sub.averageGrade.toFixed(1) + "%" : "A"}</span>
      </div>
      <div class="subject-name">${escapeHtml(sub.title)}</div>
      <div class="subject-instructor">👨‍🏫 ${escapeHtml(sub.instructor)}</div>
      <div class="subject-info-footer">
        <span>Units: <strong>${sub.units}</strong></span>
        <span>Room: <strong>${escapeHtml(sub.room || "TBA")}</strong></span>
      </div>
    `;
    container.appendChild(card);
  });
}

// Render Profile
function renderProfile() {
  const nameEl = document.getElementById("prof-val-name");
  const emailEl = document.getElementById("prof-val-email");
  const idEl = document.getElementById("prof-val-id");
  const degEl = document.getElementById("prof-val-degree");
  const semEl = document.getElementById("prof-val-semester");

  if (nameEl) nameEl.textContent = state.profile.name;
  if (emailEl) emailEl.textContent = state.profile.email;
  if (idEl) idEl.textContent = state.profile.studentId;
  if (degEl) degEl.textContent = state.profile.degree;
  if (semEl) semEl.textContent = state.profile.yearAndSemester;
}

// Pomodoro Focus Timer Logic
function setupTimer() {
  const startBtn = document.getElementById("timer-start-btn");
  const resetBtn = document.getElementById("timer-reset-btn");
  const presets = document.querySelectorAll(".preset-pill");

  if (startBtn) {
    startBtn.addEventListener("click", () => {
      if (state.timer.isRunning) {
        pauseTimer();
      } else {
        startTimer();
      }
    });
  }

  if (resetBtn) {
    resetBtn.addEventListener("click", () => {
      resetTimer();
    });
  }

  presets.forEach(pill => {
    pill.addEventListener("click", () => {
      presets.forEach(p => p.classList.remove("active"));
      pill.classList.add("active");

      const mins = parseInt(pill.getAttribute("data-mins"), 10) || 25;
      const mode = pill.getAttribute("data-mode") || "focus";
      setTimerMode(mode, mins);
    });
  });

  updateTimerDisplay();
}

function startTimer() {
  if (state.timer.isRunning) return;
  state.timer.isRunning = true;

  const startBtn = document.getElementById("timer-start-btn");
  if (startBtn) startBtn.textContent = "Pause Focus";

  state.timer.intervalId = setInterval(() => {
    if (state.timer.remainingSeconds > 0) {
      state.timer.remainingSeconds--;
      updateTimerDisplay();
    } else {
      pauseTimer();
      showToast("Focus session complete! Take a well-deserved break! ☕");
      if (isAndroidAvailable() && window.AndroidBridge.showToast) {
        window.AndroidBridge.showToast("Ionic Focus Session Complete!");
      }
    }
  }, 1000);
}

function pauseTimer() {
  state.timer.isRunning = false;
  clearInterval(state.timer.intervalId);
  const startBtn = document.getElementById("timer-start-btn");
  if (startBtn) startBtn.textContent = "Resume Focus";
}

function resetTimer() {
  pauseTimer();
  state.timer.remainingSeconds = state.timer.durationSeconds;
  const startBtn = document.getElementById("timer-start-btn");
  if (startBtn) startBtn.textContent = "Start Focus";
  updateTimerDisplay();
}

function setTimerMode(mode, minutes) {
  state.timer.mode = mode;
  state.timer.durationSeconds = minutes * 60;
  resetTimer();

  const modeLbl = document.getElementById("timer-mode-label");
  if (modeLbl) {
    modeLbl.textContent = mode === "focus" ? "Study Focus" : (mode === "break" ? "Short Break" : "Long Rest");
  }
}

function updateTimerDisplay() {
  const digitsEl = document.getElementById("timer-digits-text");
  const circleEl = document.getElementById("timer-progress-circle");

  const mins = Math.floor(state.timer.remainingSeconds / 60);
  const secs = state.timer.remainingSeconds % 60;
  const formatted = `${String(mins).padStart(2, "0")}:${String(secs).padStart(2, "0")}`;

  if (digitsEl) digitsEl.textContent = formatted;

  if (circleEl) {
    const total = state.timer.durationSeconds;
    const progress = total > 0 ? (total - state.timer.remainingSeconds) / total : 0;
    const circumference = 2 * Math.PI * 90; // r = 90
    circleEl.style.strokeDasharray = `${circumference}`;
    circleEl.style.strokeDashoffset = `${circumference * (1 - progress)}`;
  }
}

// Modal Handlers
function openAddTaskModal() {
  const modal = document.getElementById("modal-add-task");
  const select = document.getElementById("modal-task-subject");
  if (select) {
    select.innerHTML = state.subjects.map(s => `<option value="${escapeHtml(s.code)}">${escapeHtml(s.code)} - ${escapeHtml(s.title)}</option>`).join("");
  }
  if (modal) modal.classList.add("open");
}

function closeAddTaskModal() {
  const modal = document.getElementById("modal-add-task");
  if (modal) modal.classList.remove("open");
}

function submitNewTask(e) {
  e.preventDefault();
  const title = document.getElementById("input-task-title").value.trim();
  const subject = document.getElementById("modal-task-subject").value;
  const due = document.getElementById("input-task-due").value.trim() || "Soon";
  const priority = document.getElementById("input-task-priority").value;

  if (!title) {
    showToast("Please enter a task title");
    return;
  }

  const newTask = {
    id: `task-${Date.now()}`,
    title,
    subject,
    dueDate: due,
    priority,
    isCompleted: false
  };

  state.tasks.unshift(newTask);
  renderTasks();
  renderHeroAndMetrics();
  closeAddTaskModal();
  document.getElementById("form-add-task").reset();

  if (isAndroidAvailable() && window.AndroidBridge.addTask) {
    window.AndroidBridge.addTask(title, subject, due, priority);
  }

  showToast("Task added successfully! 📋");
}

function openEditProfileModal() {
  const modal = document.getElementById("modal-edit-profile");
  document.getElementById("edit-prof-name").value = state.profile.name;
  document.getElementById("edit-prof-email").value = state.profile.email;
  document.getElementById("edit-prof-id").value = state.profile.studentId;
  document.getElementById("edit-prof-degree").value = state.profile.degree;
  document.getElementById("edit-prof-semester").value = state.profile.yearAndSemester;

  if (modal) modal.classList.add("open");
}

function closeEditProfileModal() {
  const modal = document.getElementById("modal-edit-profile");
  if (modal) modal.classList.remove("open");
}

function submitProfileEdit(e) {
  e.preventDefault();
  const name = document.getElementById("edit-prof-name").value.trim();
  const email = document.getElementById("edit-prof-email").value.trim();
  const studentId = document.getElementById("edit-prof-id").value.trim();
  const degree = document.getElementById("edit-prof-degree").value.trim();
  const semester = document.getElementById("edit-prof-semester").value.trim();

  if (!name || !email) {
    showToast("Name and email are required");
    return;
  }

  state.profile.name = name;
  state.profile.email = email;
  state.profile.studentId = studentId;
  state.profile.degree = degree;
  state.profile.yearAndSemester = semester;

  renderAll();
  closeEditProfileModal();

  if (isAndroidAvailable() && window.AndroidBridge.updateAcademicProfile) {
    window.AndroidBridge.updateAcademicProfile(name, email, studentId, degree, semester);
  }

  showToast("Academic profile updated! 🎓");
}

// Toast notification display
function showToast(message) {
  const toast = document.getElementById("toast-banner");
  if (!toast) return;

  toast.textContent = message;
  toast.classList.add("show");

  setTimeout(() => {
    toast.classList.remove("show");
  }, 2600);
}

// Called by Android Kotlin WebView when data changes in ViewModel
window.updateFromAndroid = function(jsonString) {
  try {
    const data = JSON.parse(jsonString);
    if (data.profile) state.profile = { ...state.profile, ...data.profile };
    if (data.subjects) state.subjects = data.subjects;
    if (data.tasks) state.tasks = data.tasks;
    renderAll();
  } catch (err) {
    console.error("Error receiving data from Android:", err);
  }
};

// Sync button clicked by user
function syncWithAndroid() {
  if (isAndroidAvailable() && window.AndroidBridge.getStudentData) {
    loadData();
    renderAll();
    showToast("Synced with Native Kotlin ViewModel! 🔄");
    if (window.AndroidBridge.showToast) {
      window.AndroidBridge.showToast("Ionic Suite Synchronized with Android");
    }
  } else {
    showToast("Ionic Suite is running in standalone Web mode");
  }
}

// HTML escape helper
function escapeHtml(str) {
  if (!str) return "";
  return str
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}
