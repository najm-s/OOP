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
import javafx.scene.text.FontWeight;

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
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F7FA;");

        VBox headerBox = new VBox(6);
        Label titleLabel = new Label("Dashboard");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #2C3E50;");

        Label welcomeLabel = new Label("Welcome back, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(Font.font("System", 14));
        welcomeLabel.setStyle("-fx-text-fill: #7F8C8D;");
        headerBox.getChildren().addAll(titleLabel, welcomeLabel);

        String cardStyle = "-fx-background-color: white; " +
                           "-fx-padding: 15 20; " +
                           "-fx-background-radius: 8; " +
                           "-fx-border-radius: 8; " +
                           "-fx-border-color: #E2E8F0; " +
                           "-fx-border-width: 1; " +
                           "-fx-min-width: 140;";

        VBox streakCard = createStatCard("🔥 Streak", currentUser.getStreakCount() + " days", cardStyle);
        VBox totalCard = createStatCard("📋 Total", String.valueOf(dashboard.getTotalTasks()), cardStyle);
        VBox completedCard = createStatCard("✅ Done", String.valueOf(dashboard.getCompletedTasks()), cardStyle);
        VBox pendingCard = createStatCard("⏳ Pending", String.valueOf(dashboard.getPendingTasks()), cardStyle);
        VBox percentCard = createStatCard("📈 Progress", String.format("%.0f%%", dashboard.getCompletionPercentage()), cardStyle);

        HBox statsRow = new HBox(16);
        statsRow.getChildren().addAll(streakCard, totalCard, completedCard, pendingCard, percentCard);
        statsRow.setPadding(new Insets(15, 0, 15, 0));

        VBox topContainer = new VBox(15);
        topContainer.getChildren().addAll(headerBox, statsRow);
        root.setTop(topContainer);


        PieChart progressChart = createProgressChart();
        PieChart priorityChart = createPriorityChart();
        
        String chartContainerStyle = "-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 15; -fx-border-color: #E2E8F0; -fx-border-radius: 8;";
        VBox chart1Wrap = new VBox(progressChart); chart1Wrap.setStyle(chartContainerStyle);
        VBox chart2Wrap = new VBox(priorityChart); chart2Wrap.setStyle(chartContainerStyle);

        HBox chartBox = new HBox(25);
        chartBox.getChildren().addAll(chart1Wrap, chart2Wrap);
        chartBox.setAlignment(Pos.CENTER);
        chartBox.setPadding(new Insets(20, 0, 20, 0));
        root.setCenter(chartBox);

        Button taskButton = new Button("Manage Tasks");
        Button refreshButton = new Button("Refresh Dashboard");
        Button logoutButton = new Button("Logout");

        taskButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");
        refreshButton.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-border-color: #BDC3C7; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand;");
        logoutButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #E74C3C; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand;");

        taskButton.setOnAction(event -> mainApp.showTaskPage(currentUser));
        refreshButton.setOnAction(event -> mainApp.showDashboardPage(currentUser));
        logoutButton.setOnAction(event -> mainApp.logout());

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(taskButton, refreshButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Aligned beautifully to the bottom right
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        root.setBottom(buttonBox);

        return root;
    }

    private VBox createStatCard(String title, String value, String style) {
        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("System", 13));
        titleLbl.setStyle("-fx-text-fill: #7F8C8D;");

        Label valueLbl = new Label(value);
        valueLbl.setFont(Font.font("System", FontWeight.BOLD, 22));
        valueLbl.setStyle("-fx-text-fill: #2C3E50;");

        VBox card = new VBox(6);
        card.setStyle(style);
        card.getChildren().addAll(titleLbl, valueLbl);
        return card;
    }

    private PieChart createProgressChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Performance Progress");
        chart.setPrefSize(340, 280);
        chart.getData().add(new PieChart.Data("Completed", dashboard.getCompletedTasks()));
        chart.getData().add(new PieChart.Data("Pending", dashboard.getPendingTasks()));
        return chart;
    }

    private PieChart createPriorityChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Priority Overview");
        chart.setPrefSize(340, 280);
        chart.getData().add(new PieChart.Data("High", dashboard.getHighPriorityCount()));
        chart.getData().add(new PieChart.Data("Medium", dashboard.getMediumPriorityCount()));
        chart.getData().add(new PieChart.Data("Low", dashboard.getLowPriorityCount()));
        return chart;
    }
}
