package com.mycompany.try1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

// Handles all file operations for the application.
// Responsible for saving and loading user and task data from text files.
public class FileHandler {
    private File userFile;
    private File taskFile;

    // assigns file paths to File object
    
    public FileHandler() {
        userFile = new File("users.txt");
        taskFile = new File("tasks.txt");
        createFilesIfMissing();
    }

    // Creates users.txt and tasks.txt if they do not already exist.
    private void createFilesIfMissing() {
        try {
            if (!userFile.exists()) {
                userFile.createNewFile();
            }

            if (!taskFile.exists()) {
                taskFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating files: " + e.getMessage());
        }
    }

    // saves all users to users.txt by overwriting the existing file with updated data
    public void saveUsers(ArrayList<User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userFile));

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                writer.write(user.toFileString());
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Loads all users from users.txt and converts them into User objects
    // invoked when start of program
    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            String line;

            // continue when it detects a blank line
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length < 4) {
                    continue;
                }

                // retrieve username and password
                LocalDate lastLoginDate;
                String username = parts[0];
                String password = parts[1];
                int streakCount = Integer.parseInt(parts[2]);

                if (parts[3].equals("null")) {lastLoginDate = null;}
                else{ lastLoginDate = LocalDate.parse(parts[3]); }

                // adds loaded users into users once application starts to be used in authentication.
                User user = new User(username, password, streakCount, lastLoginDate);
                users.add(user);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Data format error in users file: " + e.getMessage());
        }

        // accepts by userList in  UserManager.
        return users;
    }

    public void saveTasks(User currentUser) {
        ArrayList<String> allTasks = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile));
            String line;

            while ((line = reader.readLine()) != null) { 
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length < 5) {
                    continue;
                }

                String savedUsername = parts[0];

                // This block of code saves tasks for the non-current users
                if (!savedUsername.equalsIgnoreCase(currentUser.getUsername())) {
                    allTasks.add(line);
                }
            }

            reader.close();

            for (int i = 0; i < currentUser.getTaskList().size(); i++) {
                // gets current user's list and adds to the task list that holds tasks of other users
                Task task = currentUser.getTaskList().get(i);
                allTasks.add(task.toFileString(currentUser.getUsername()));
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile));

            // saves into tasks.txt
            for (int i = 0; i < allTasks.size(); i++) {
                writer.write(allTasks.get(i));
                writer.newLine();
            }

            writer.close();
        } 
        catch (IOException e) {System.out.println("Error saving tasks: " + e.getMessage());}
    }

    // scans tasks.txt and pulls out only the tasks that match a specific username
    public ArrayList<Task> loadTasksForUser(String username) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taskFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length < 5) {
                    continue;
                }

                String savedUsername = parts[0];

                // searches for the correct username
                if (savedUsername.equalsIgnoreCase(username)) {
                    String taskName = parts[1];
                    LocalDate deadLine = LocalDate.parse(parts[2]);
                    String taskPriority = parts[3];
                    boolean completed = Boolean.parseBoolean(parts[4]);

                    // create a Task object using the extracted values
                    Task task = new Task(taskName, deadLine, taskPriority, completed);
                    tasks.add(task);
                }
            }

            reader.close();
        } 
        catch (IOException e) {System.out.println("Error loading tasks: " + e.getMessage());} 
        catch (Exception e) {System.out.println("Data format error in tasks file: " + e.getMessage());}
        
        // return the completed list of tasks belonging to the logged-in user
        // received by user.setTaskList() in UserManager.login()
        return tasks;
    }
}
