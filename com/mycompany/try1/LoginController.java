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

    // tracks which mode the user is in: login or register
    private boolean isRegisterMode = false;

    public LoginController(MainApp mainApp, UserManager userManager) {
        this.mainApp = mainApp;
        this.userManager = userManager;
        streakManager = new StreakManager();
    }

    public Parent getView() {
        Label titleLabel = new Label("To-Do List");
        titleLabel.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #2C3E50;");

        Label subtitleLabel = new Label("Sign in to manage your daily tasks");
        subtitleLabel.setFont(Font.font("System", 14));
        subtitleLabel.setStyle("-fx-text-fill: #7F8C8D;");

        String inputStyle = "-fx-background-color: #FAFAFA; " +
                            "-fx-border-color: #BDC3C7; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-radius: 6; " +
                            "-fx-background-radius: 6; " +
                            "-fx-padding: 10;";

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(280);
        usernameField.setStyle(inputStyle);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(280);
        passwordField.setStyle(inputStyle);

        Button actionButton = new Button("Login");
        actionButton.setMaxWidth(280);
        actionButton.setStyle("-fx-background-color: #3498DB; " +
                              "-fx-text-fill: white; " +
                              "-fx-font-weight: bold; " +
                              "-fx-font-size: 14px; " +
                              "-fx-padding: 10; " +
                              "-fx-background-radius: 6; " +
                              "-fx-cursor: hand;");

        // toggle button — switches between login and register mode
        Button toggleButton = new Button("Don't have an account? Register");
        toggleButton.setMaxWidth(280);
        toggleButton.setStyle("-fx-background-color: transparent; " +
                              "-fx-text-fill: #3498DB; " +
                              "-fx-font-size: 13px; " +
                              "-fx-cursor: hand;");

        messageLabel = new Label();
        messageLabel.setFont(Font.font("System", 13));
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(280);
        messageLabel.setAlignment(Pos.CENTER);

        toggleButton.setOnAction(event -> {
            isRegisterMode = !isRegisterMode;
            messageLabel.setText("");

            if (isRegisterMode) {
                // switch to register mode
                titleLabel.setText("Create Account");
                subtitleLabel.setText("Register a new account");
                actionButton.setText("Register");
                toggleButton.setText("Already have an account? Login");
            } else {
                // switch back to login mode
                titleLabel.setText("To-Do List");
                subtitleLabel.setText("Sign in to manage your daily tasks");
                actionButton.setText("Login");
                toggleButton.setText("Don't have an account? Register");
            }

            usernameField.clear();
            passwordField.clear();
        });

        
        actionButton.setOnAction(event -> {
            if (isRegisterMode) {
                handleRegister();
            } else {
                handleLogin();
            }
        });

        VBox formContainer = new VBox(16);
        formContainer.getChildren().addAll(usernameField, passwordField, actionButton, toggleButton, messageLabel);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(10, 0, 0, 0));

        VBox rootLayout = new VBox(8);
        rootLayout.getChildren().addAll(titleLabel, subtitleLabel, formContainer);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setPadding(new Insets(40));
        rootLayout.setStyle("-fx-background-color: #F5F7FA;");

        return rootLayout;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user = userManager.login(username, password);

        if (user == null) {
            messageLabel.setText("Invalid username or password.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
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
            messageLabel.setStyle("-fx-text-fill: #2ECC71;");
            usernameField.clear();
            passwordField.clear();
        } else {
            messageLabel.setText("Registration failed. Username may exist or input is empty.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
        }
    }
}
