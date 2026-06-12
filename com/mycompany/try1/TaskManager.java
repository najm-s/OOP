package com.mycompany.try1;

import java.time.LocalDate;
import java.util.ArrayList;

// Handles all operations of task such as adding a task (addTask()), edit a task (editTask()), delete a task (deleteTask()),
// marking completed tasks, 
public class TaskManager {
    private User currentUser;
    private FileHandler fileHandler;

    public TaskManager(User currentUser) {
        this.currentUser = currentUser;
        fileHandler = new FileHandler();
    }

    // runs when user clicks on add task then activates
    // handleAddTask() in TaskController.
    // handleAddTask() invokes addTask();
    public void addTask(String activityName, LocalDate deadLine, String taskPriority) {
        // Assigns task values to task variables.
        // initially status set to false.
        Task newTask = new Task(activityName, deadLine, taskPriority, false);
        // add new task into tasklist
        currentUser.getTaskList().add(newTask);
        // save to file immediately
        fileHandler.saveTasks(currentUser);
    }
    // runs when user clicks on edit task then activates
    // handleEditTask() in TaskController
    // handleEditTask() calls editTask();
    public void editTask(Task selectedTask, String newTaskName, LocalDate newDeadLine, String newTaskPriority) {
        if (selectedTask != null) {
            selectedTask.setActivityName(newTaskName);
            selectedTask.setDeadline(newDeadLine);
            selectedTask.setPriority(newTaskPriority);
            fileHandler.saveTasks(currentUser);
        }
    }


    // runs when user clicks on delete task button then activates
    // handleDeleteTask() in TaskController
    // handleDeleteTask() calls deleteTask();
    public void deleteTask(Task selectedTask) {
        if (selectedTask != null) {
            currentUser.getTaskList().remove(selectedTask);
            fileHandler.saveTasks(currentUser);
        }
    }

    public void markTaskCompleted(Task selectedTask) {
        if (selectedTask != null) {
            boolean markAsComplete = true;
            selectedTask.setCompleted(markAsComplete);
            fileHandler.saveTasks(currentUser);
        }
    }

    // returns the current user's tasks
    public ArrayList<Task> getAllTasks() {
        return currentUser.getTaskList();
    }

    // counts the exact number of finished tasks.
    // value is returned to to the Dashboard
    // used by Dashboard and DashboardController classes.
    public int countCompletedTasks() {
        int counter = 0;

        for (int t = 0; t < currentUser.getTaskList().size(); t++) {
            Task task = currentUser.getTaskList().get(t);

            if (task.isCompleted()) {
                counter++;
            }
        }

        return counter;
    }

    // used in the calculation on how many uncompleted task a user has. 
    public int countPendingTasks() {
        int counter = 0;

        for (int i = 0; i < currentUser.getTaskList().size(); i++) {
            Task task = currentUser.getTaskList().get(i);

            if (!task.isCompleted()) {
                counter++;
            }
        }

        return counter;
    }

    // counts how many tasks in the list match a specific priority level for a particular user
    public int countPriority(String priority) {
        int counter = 0;

        for (int i = 0; i < currentUser.getTaskList().size(); i++) {
            Task task = currentUser.getTaskList().get(i);

            if (task.getPriority().equalsIgnoreCase(priority)) {
                counter++;
            }
        }

        return counter;
    }
}
