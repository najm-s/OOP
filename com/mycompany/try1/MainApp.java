package com.mycompany.try1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private User currentUser;
    private UserManager userManager;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        userManager = new UserManager();

        primaryStage.setTitle("To-Do List App");
        showLoginPage();
        primaryStage.show();
    }

    //Will forward it to login page, using LoginContoller class
    public void showLoginPage() {
        LoginController loginController = new LoginController(this, userManager);
        Scene scene = new Scene(loginController.getView(), 450, 350);
        primaryStage.setScene(scene);
    }

    //Forward to DashboardController
    public void showDashboardPage(User user) {
        currentUser = user;
        DashboardController dashboardController = new DashboardController(this, currentUser);
        Scene scene = new Scene(dashboardController.getView(), 900, 600);
        primaryStage.setScene(scene);
    }

    //Forward to TaskController
    public void showTaskPage(User user) {
        currentUser = user;
        TaskController taskController = new TaskController(this, currentUser);
        Scene scene = new Scene(taskController.getView(), 950, 620);
        primaryStage.setScene(scene);
    }

    //Back to LoginController
    public void logout() {
        currentUser = null;
        showLoginPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
