package com.mycompany.try1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginController {
    private MainApp mainApp;
    private UserManager userManager;
    private StreakManager streakManager;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;

    public LoginController(MainApp mainApp, UserManager userManager) {
        this.mainApp = mainApp;
        this.userManager = userManager;
        streakManager = new StreakManager();
    }

    public Parent getView() {
        Label titleLabel = new Label("To-Do List Login");
        titleLabel.setFont(new Font(24));

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(260);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(260);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.setMaxWidth(260);
        registerButton.setMaxWidth(260);

        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());

        messageLabel = new Label();

        VBox layout = new VBox(12);
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        return layout;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user = userManager.login(username, password);

        if (user == null) {
            messageLabel.setText("Invalid username or password.");
            return;
        }

        streakManager.updateStreak(user);
        userManager.updateUser(user);
        mainApp.showDashboardPage(user);
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        boolean success = userManager.registerUser(username, password);

        if (success) {
            messageLabel.setText("Account registered successfully. You can now login.");
            usernameField.clear();
            passwordField.clear();
        } else {
            messageLabel.setText("Registration failed. Username may exist or input is empty.");
        }
    }
}
