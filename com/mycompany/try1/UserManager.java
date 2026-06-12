package com.mycompany.try1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// This class manages common user management like registerUser and authentication (login)
// This class also checks for duplicates for new usernames to ensure uniqueness (usernameExist())
// and holds the  operation to  update user once any updates or tasks is given to a user. (updateuser())
public class UserManager {
    private ArrayList<User> userList;
    private FileHandler fileHandler;

    // runs when the application starts and load users.txt into userList  arrayList object. 
    // so the login can use the values in userList to check for duplicates.
    public UserManager() {
        fileHandler = new FileHandler();
        userList = fileHandler.loadUsers();
    }

    //runs after the user clicks register button
    public boolean registerUser(String username, String password) {

        // ensure username and password aren't blank and username is always unique.
        if (username == null || username.isBlank()) {return false;}

        if (password == null || password.isBlank()) {return false;}

        if (usernameExist(username)) {return false;}

        // create object to assign variables in User to help with formatting.  
        User newUser = new User(username.trim(), password);
        // adds newly registered account into array list of users.
        userList.add(newUser);

        // save newest list. 
        fileHandler.saveUsers(userList);

        // this part is received by a boolean value method in LoginController class (handleRegister())
        return true;
    }

    // handles login of users
    // runs after user clicks login button
    public User login(String username, String password) {
        // get current lists of users jsut incase if the user retgisters a new user. 
        userList = fileHandler.loadUsers();

        

        // loop to check if user entered ccredentials matches the credentials of any users in the list.
        for (int i = 0; i < userList.size(); i++) {

            // for every loop user is different due to i+1
            User user = userList.get(i);

            // checks if the entered username and password matches in any of those list. 
            boolean matchUsername = user.getUsername().equalsIgnoreCase(username.trim());
            boolean matchPassword = user.checkPassword(password);

            // if authentication process is a success then user's task list is loaded.
            if (matchUsername && matchPassword) {
                ArrayList<Task> tasks = fileHandler.loadTasksForUser(user.getUsername());
                user.setTaskList(tasks);

                // return when it is success
                return user;
            }
        }

        // if authentication process is not verified then return null.
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

            // a loop that goes for every line taht contains user credentials
            while ((line = reader.readLine()) != null) {
                // ignore blank lines or else can program falls into an runtime error.
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                // take username part from file
                String savedUsername = parts[0];

                // if username has a match return true. True value will activate if statement in registerUser. 
                if (savedUsername.equalsIgnoreCase(username.trim())) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            
        }
        catch (IOException e) {System.out.println("Error checking username: " + e.getMessage());}

        // if no match, then return false. 
        return false;
    }

    // Runs after the user's streak data changes during login
    // It replaces the old saved user data with the updated user data
    public void updateUser(User updatedUser) {

        userList = fileHandler.loadUsers();

        //for loop gets every member when i + 1
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);

            // find the intended user to update their data
            if (user.getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
                userList.set(i, updatedUser);
                break;
            }
        }

        // save new data to text file
        fileHandler.saveUsers(userList);
    }
}
