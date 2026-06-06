package com.mycompany.try1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DashboardController {
    private MainApp mainApp;
    private User currentUser;
    private TaskManager taskManager;
    private Dashboard dashboard;

    public DashboardController(MainApp mainApp, User currentUser) {
        this.mainApp = mainApp;
        this.currentUser = currentUser;
        taskManager = new TaskManager(currentUser);
        dashboard = new Dashboard(taskManager);
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard");
        titleLabel.setFont(new Font(28));

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        Label streakLabel = new Label("Login Streak: " + currentUser.getStreakCount() + " day(s)");
        Label totalLabel = new Label("Total Tasks: " + dashboard.getTotalTasks());
        Label completedLabel = new Label("Completed Tasks: " + dashboard.getCompletedTasks());
        Label pendingLabel = new Label("Pending Tasks: " + dashboard.getPendingTasks());
        Label percentageLabel = new Label(String.format("Completion Percentage: %.2f%%", dashboard.getCompletionPercentage()));

        VBox summaryBox = new VBox(10);
        summaryBox.getChildren().addAll(titleLabel, welcomeLabel, streakLabel, totalLabel, completedLabel, pendingLabel, percentageLabel);
        summaryBox.setPadding(new Insets(10));

        PieChart progressChart = createProgressChart();
        PieChart priorityChart = createPriorityChart();

        HBox chartBox = new HBox(30);
        chartBox.getChildren().addAll(progressChart, priorityChart);
        chartBox.setAlignment(Pos.CENTER);

        Button taskButton = new Button("Manage Tasks");
        Button refreshButton = new Button("Refresh Dashboard");
        Button logoutButton = new Button("Logout");

        taskButton.setOnAction(event -> mainApp.showTaskPage(currentUser));
        refreshButton.setOnAction(event -> mainApp.showDashboardPage(currentUser));
        logoutButton.setOnAction(event -> mainApp.logout());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(taskButton, refreshButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        root.setTop(summaryBox);
        root.setCenter(chartBox);
        root.setBottom(buttonBox);

        return root;
    }

    private PieChart createProgressChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Performance Progress");
        chart.getData().add(new PieChart.Data("Completed", dashboard.getCompletedTasks()));
        chart.getData().add(new PieChart.Data("Pending", dashboard.getPendingTasks()));
        return chart;
    }

    private PieChart createPriorityChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Priority Overview");
        chart.getData().add(new PieChart.Data("High", dashboard.getHighPriorityCount()));
        chart.getData().add(new PieChart.Data("Medium", dashboard.getMediumPriorityCount()));
        chart.getData().add(new PieChart.Data("Low", dashboard.getLowPriorityCount()));
        return chart;
    }
}
