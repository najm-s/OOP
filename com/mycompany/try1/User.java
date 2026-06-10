package com.mycompany.try1;

import java.time.LocalDate;
import java.util.ArrayList;

public class User extends Account {
    private int streakCount;
    private LocalDate lastLoginDate;
    private ArrayList<Task> taskList;

    // Runs after a new user successfully registers.
    // and creates a new User object with default values like last login date, streak count and 
    // an empty task list.
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

    // used by dashboard to show user's current steak
    public int getStreakCount() {
        return streakCount;
    }

    // runs right after streak is calculated during login process
    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }
    // used by StreakManager to check the previous login date.
    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    //Return user's list of tasks
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    //used when FileHandler load task from tasks.txt
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
