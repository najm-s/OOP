package com.mycompany.try1;

// Model class
public class Account {
    private String username;
    private String password;

    // sets  details when user account is created
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    // checks user entered password mathces the stored password
    public boolean checkPassword(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    // used to retirve password to save in file
    public String getStoredPassword() {
        return password;
    }
}
