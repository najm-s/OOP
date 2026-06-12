package com.mycompany.try1;

import java.time.LocalDate;

// This class holds the core values of a task such as the name, deadline, priority
// and its completion status
public class Task {
    private String activityName;
    private LocalDate deadline;
    private String priority;
    private boolean completed;

    // invoked during adding a task and loadTaskForUser();
    public Task(String taskName, LocalDate deadLine, String taskPriority, boolean completed) {
        this.activityName = taskName;
        this.deadline = deadLine;
        this.priority = taskPriority;
        this.completed = completed;
    }

    // set and getter methods
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String taskPriority) {
        this.priority = taskPriority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStatusText() {
        if (completed) {
            return "Completed!";
        } else {
            return "Pending...";
        }
    }

    // ---------------------------------------------

    // Format 1 current task to save in file (saveTasks() method)
    public String toFileString(String username) {
        return username + "|" + activityName + "|" + deadline + "|" + priority + "|" + completed;
    }
}
