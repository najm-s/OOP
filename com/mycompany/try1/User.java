package com.mycompany.try1;

import java.time.LocalDate;
import java.util.ArrayList;

public class User extends Account {
    private int streakCount;
    private LocalDate lastLoginDate;
    private ArrayList<Task> taskList;

    public User(String username, String password) {
        super(username, password);
        streakCount = 0;
        lastLoginDate = null;
        taskList = new ArrayList<>();
    }

    public User(String username, String password, int streakCount, LocalDate lastLoginDate) {
        super(username, password);
        this.streakCount = streakCount;
        this.lastLoginDate = lastLoginDate;
        taskList = new ArrayList<>();
    }

    public int getStreakCount() {
        return streakCount;
    }

    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public String toFileString() {
        String dateText;

        if (lastLoginDate == null) {
            dateText = "null";
        } else {
            dateText = lastLoginDate.toString();
        }

        return getUsername() + "|" + getStoredPassword() + "|" + streakCount + "|" + dateText;
    }
}
