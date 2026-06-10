package com.mycompany.try1;

import java.time.LocalDate;
import java.util.ArrayList;

public class User extends Account {
    private int streakCount;
    private LocalDate lastLoginDate;
    private ArrayList<Task> taskList;

    // Constructor is activated right after user account is created
    public User(String username, String password) {
        super(username, password);
        streakCount = 0;
        lastLoginDate = null;
        taskList = new ArrayList<>();
    }

    // is used to load an existing user from users.txt
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

    // format user data 
    public String toFileString() {
        // variable to store date in Strign form
        String dateText;

        // set dateText as null when the user haven't log in before
        if (lastLoginDate == null) {dateText = "null";} 
        else {dateText = lastLoginDate.toString();}

        return getUsername() + "|" + getStoredPassword() + "|" + streakCount + "|" + dateText;
    }
}
