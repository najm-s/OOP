package com.mycompany.try1;

import java.time.LocalDate;

public class Task {
    private String activityName;
    private LocalDate deadline;
    private String priority;
    private boolean completed;

    public Task(String activityName, LocalDate deadline, String priority, boolean completed) {
        this.activityName = activityName;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = completed;
    }

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

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStatusText() {
        if (completed) {
            return "Completed";
        } else {
            return "Pending";
        }
    }

    public String toFileString(String username) {
        return username + "|" + activityName + "|" + deadline + "|" + priority + "|" + completed;
    }
}
