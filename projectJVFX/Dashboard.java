package com.mycompany.try1;

public class Dashboard {
    private TaskManager taskManager;

    public Dashboard(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public int getTotalTasks() {
        return taskManager.getAllTasks().size();
    }

    public int getCompletedTasks() {
        return taskManager.countCompletedTasks();
    }

    public int getPendingTasks() {
        return taskManager.countPendingTasks();
    }

    public double getCompletionPercentage() {
        int total = getTotalTasks();

        if (total == 0) {
            return 0;
        }

        return (getCompletedTasks() * 100.0) / total;
    }

    public int getHighPriorityCount() {
        return taskManager.countPriority("High");
    }

    public int getMediumPriorityCount() {
        return taskManager.countPriority("Medium");
    }

    public int getLowPriorityCount() {
        return taskManager.countPriority("Low");
    }
}
