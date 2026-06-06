package com.mycompany.try1;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskManager {
    private User currentUser;
    private FileHandler fileHandler;

    public TaskManager(User currentUser) {
        this.currentUser = currentUser;
        fileHandler = new FileHandler();
    }

    public void addTask(String activityName, LocalDate deadline, String priority) {
        Task newTask = new Task(activityName, deadline, priority, false);
        currentUser.getTaskList().add(newTask);
        fileHandler.saveTasks(currentUser);
    }

    public void editTask(Task selectedTask, String newName, LocalDate newDeadline, String newPriority) {
        if (selectedTask != null) {
            selectedTask.setActivityName(newName);
            selectedTask.setDeadline(newDeadline);
            selectedTask.setPriority(newPriority);
            fileHandler.saveTasks(currentUser);
        }
    }

    public void deleteTask(Task selectedTask) {
        if (selectedTask != null) {
            currentUser.getTaskList().remove(selectedTask);
            fileHandler.saveTasks(currentUser);
        }
    }

    public void markTaskCompleted(Task selectedTask) {
        if (selectedTask != null) {
            selectedTask.setCompleted(true);
            fileHandler.saveTasks(currentUser);
        }
    }

    public ArrayList<Task> getAllTasks() {
        return currentUser.getTaskList();
    }

    public int countCompletedTasks() {
        int count = 0;

        for (int i = 0; i < currentUser.getTaskList().size(); i++) {
            Task task = currentUser.getTaskList().get(i);

            if (task.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    public int countPendingTasks() {
        int count = 0;

        for (int i = 0; i < currentUser.getTaskList().size(); i++) {
            Task task = currentUser.getTaskList().get(i);

            if (!task.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    public int countPriority(String priority) {
        int count = 0;

        for (int i = 0; i < currentUser.getTaskList().size(); i++) {
            Task task = currentUser.getTaskList().get(i);

            if (task.getPriority().equalsIgnoreCase(priority)) {
                count++;
            }
        }

        return count;
    }
}
