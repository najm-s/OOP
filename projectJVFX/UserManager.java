package com.mycompany.try1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> userList;
    private FileHandler fileHandler;

    public UserManager() {
        fileHandler = new FileHandler();
        userList = fileHandler.loadUsers();
    }

    public boolean registerUser(String username, String password) {
        if (username == null || username.isBlank()) {
            return false;
        }

        if (password == null || password.isBlank()) {
            return false;
        }

        if (usernameExist(username)) {
            return false;
        }

        User newUser = new User(username.trim(), password);
        userList.add(newUser);
        fileHandler.saveUsers(userList);

        return true;
    }

    public User login(String username, String password) {
        userList = fileHandler.loadUsers();

        if (username == null || password == null) {
            return null;
        }

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);

            boolean sameUsername = user.getUsername().equalsIgnoreCase(username.trim());
            boolean correctPassword = user.checkPassword(password);

            if (sameUsername && correctPassword) {
                ArrayList<Task> tasks = fileHandler.loadTasksForUser(user.getUsername());
                user.setTaskList(tasks);

                return user;
            }
        }

        return null;
    }

    public boolean usernameExist(String username) {
        File file = new File("users.txt");

        if (!file.exists()) {
            return false;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length < 1) {
                    continue;
                }

                String savedUsername = parts[0];

                if (savedUsername.equalsIgnoreCase(username.trim())) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }

        return false;
    }

    public void updateUser(User updatedUser) {
        userList = fileHandler.loadUsers();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);

            if (user.getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
                userList.set(i, updatedUser);
                break;
            }
        }

        fileHandler.saveUsers(userList);
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
