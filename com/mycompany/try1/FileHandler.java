package com.mycompany.try1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    private File userFile;
    private File taskFile;

    public FileHandler() {
        userFile = new File("users.txt");
        taskFile = new File("tasks.txt");
        createFilesIfMissing();
    }

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

    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length < 4) {
                    continue;
                }

                String username = parts[0];
                String password = parts[1];
                int streakCount = Integer.parseInt(parts[2]);

                LocalDate lastLoginDate;

                if (parts[3].equals("null")) {
                    lastLoginDate = null;
                } else {
                    lastLoginDate = LocalDate.parse(parts[3]);
                }

                User user = new User(username, password, streakCount, lastLoginDate);
                users.add(user);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Data format error in users file: " + e.getMessage());
        }

        return users;
    }

    public void saveTasks(User currentUser) {
        ArrayList<String> allTaskLines = new ArrayList<>();

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

                if (!savedUsername.equalsIgnoreCase(currentUser.getUsername())) {
                    allTaskLines.add(line);
                }
            }

            reader.close();

            for (int i = 0; i < currentUser.getTaskList().size(); i++) {
                Task task = currentUser.getTaskList().get(i);
                allTaskLines.add(task.toFileString(currentUser.getUsername()));
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile));

            for (int i = 0; i < allTaskLines.size(); i++) {
                writer.write(allTaskLines.get(i));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

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

                if (savedUsername.equalsIgnoreCase(username)) {
                    String activityName = parts[1];
                    LocalDate deadline = LocalDate.parse(parts[2]);
                    String priority = parts[3];
                    boolean completed = Boolean.parseBoolean(parts[4]);

                    Task task = new Task(activityName, deadline, priority, completed);
                    tasks.add(task);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Data format error in tasks file: " + e.getMessage());
        }

        return tasks;
    }
}
