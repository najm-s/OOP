package com.mycompany.try1;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TaskController {
    private MainApp mainApp;
    private User currentUser;
    private TaskManager taskManager;

    private TableView<Task> taskTable;
    private TextField activityNameField;
    private DatePicker deadlinePicker;
    private ComboBox<String> priorityComboBox;
    private Label messageLabel;

    public TaskController(MainApp mainApp, User currentUser) {
        this.mainApp = mainApp;
        this.currentUser = currentUser;
        taskManager = new TaskManager(currentUser);
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F7FA;");

        Label titleLabel = new Label("Tasks for " + currentUser.getUsername());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: #2C3E50;");

        Button dashboardButton = new Button("← Back to Dashboard");
        Button logoutButton = new Button("Logout");

        dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498DB; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 5 0;");
        logoutButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #E74C3C; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 5 0;");

        dashboardButton.setOnAction(event -> mainApp.showDashboardPage(currentUser));
        logoutButton.setOnAction(event -> mainApp.logout());

        HBox topButtons = new HBox(20);
        topButtons.getChildren().addAll(dashboardButton, logoutButton);
        topButtons.setAlignment(Pos.CENTER_LEFT);

        VBox topBox = new VBox(10);
        topBox.getChildren().addAll(titleLabel, topButtons);
        topBox.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(topBox);

        setupTaskTable();
        
        VBox tableWrapper = new VBox(taskTable);
        tableWrapper.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-border-width: 1;");
        root.setCenter(tableWrapper);

        root.setBottom(createTaskForm());
        refreshTable();

        return root;
    }

    private void setupTaskTable() {
        taskTable = new TableView<>();
        taskTable.setPrefHeight(320);
        
        taskTable.setStyle("-fx-background-color: transparent; " +
                           "-fx-border-color: transparent; " +
                           "-fx-selection-bar: #EBF5FB; " + 
                           "-fx-selection-bar-non-focused: #F4F6F7;");

        TableColumn<Task, String> nameColumn = new TableColumn<>("Activity Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getActivityName()));
        nameColumn.setPrefWidth(260);

        TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
        deadlineColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDeadline().toString()));
        deadlineColumn.setPrefWidth(140);

        TableColumn<Task, String> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPriority()));
        priorityColumn.setPrefWidth(110);

        TableColumn<Task, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatusText()));
        statusColumn.setPrefWidth(110);

        taskTable.getColumns().addAll(nameColumn, deadlineColumn, priorityColumn, statusColumn);

        taskTable.getSelectionModel().selectedItemProperty().addListener((obs, oldTask, selectedTask) -> {
            if (selectedTask != null) {
                activityNameField.setText(selectedTask.getActivityName());
                deadlinePicker.setValue(selectedTask.getDeadline());
                priorityComboBox.setValue(selectedTask.getPriority());
            }
        });
    }

    private Parent createTaskForm() {
        String inputStyle = "-fx-background-color: #FAFAFA; -fx-border-color: #BDC3C7; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;";

        activityNameField = new TextField();
        activityNameField.setPromptText("What needs to be done?");
        activityNameField.setPrefWidth(240);
        activityNameField.setStyle(inputStyle);

        deadlinePicker = new DatePicker(LocalDate.now());
        deadlinePicker.setPrefWidth(240);
        deadlinePicker.setStyle(inputStyle);

        priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("High", "Medium", "Low");
        priorityComboBox.setValue("Medium");
        priorityComboBox.setPrefWidth(240);
        priorityComboBox.setStyle(inputStyle);

        Button addButton = new Button("Add Task");
        Button editButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");
        Button completeButton = new Button("Mark Completed");
        Button clearButton = new Button("Clear Fields");

        String baseBtnStyle = "-fx-font-weight: bold; -fx-padding: 10 16; -fx-background-radius: 6; -fx-cursor: hand;";
        addButton.setStyle(baseBtnStyle + "-fx-background-color: #3498DB; -fx-text-fill: white;");
        editButton.setStyle(baseBtnStyle + "-fx-background-color: white; -fx-text-fill: #555555; -fx-border-color: #BDC3C7; -fx-border-radius: 6;");
        deleteButton.setStyle(baseBtnStyle + "-fx-background-color: #E74C3C; -fx-text-fill: white;");
        completeButton.setStyle(baseBtnStyle + "-fx-background-color: #2ECC71; -fx-text-fill: white;");
        clearButton.setStyle(baseBtnStyle + "-fx-background-color: transparent; -fx-text-fill: #7F8C8D;");

        addButton.setOnAction(event -> handleAddTask());
        editButton.setOnAction(event -> handleEditTask());
        deleteButton.setOnAction(event -> handleDeleteTask());
        completeButton.setOnAction(event -> handleMarkCompleted());
        clearButton.setOnAction(event -> clearTaskFields());

        messageLabel = new Label();
        messageLabel.setFont(Font.font("System", 13));
        messageLabel.setStyle("-fx-text-fill: #7F8C8D;");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(12);
        form.setPadding(new Insets(10, 0, 15, 0));

        Label nameLbl = new Label("Activity Name:"); nameLbl.setStyle("-fx-text-fill: #34495E; -fx-font-weight: bold;");
        Label deadLbl = new Label("Deadline:");      deadLbl.setStyle("-fx-text-fill: #34495E; -fx-font-weight: bold;");
        Label prioLbl = new Label("Priority:");      prioLbl.setStyle("-fx-text-fill: #34495E; -fx-font-weight: bold;");

        form.add(nameLbl, 0, 0); form.add(activityNameField, 1, 0);
        form.add(deadLbl, 0, 1); form.add(deadlinePicker, 1, 1);
        form.add(prioLbl, 0, 2); form.add(priorityComboBox, 1, 2);

        HBox buttons = new HBox(12);
        buttons.getChildren().addAll(addButton, editButton, completeButton, deleteButton, clearButton);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setPadding(new Insets(10, 0, 5, 0));

        VBox bottomBox = new VBox(10);
        bottomBox.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 20; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-border-width: 1;");
        bottomBox.getChildren().addAll(form, buttons, messageLabel);
        
        VBox.setMargin(bottomBox, new Insets(20, 0, 0, 0));

        return bottomBox;
    }

    private void handleAddTask() {
        String activityName = activityNameField.getText().trim();
        LocalDate deadline = deadlinePicker.getValue();
        String priority = priorityComboBox.getValue();

        if (activityName.isBlank() || deadline == null || priority == null) {
            messageLabel.setText("⚠️ Please fill out all fields before adding a task.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
            return;
        }

        taskManager.addTask(activityName, deadline, priority);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("✅ Task added successfully.");
        messageLabel.setStyle("-fx-text-fill: #2ECC71;");
    }

    private void handleEditTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("⚠️ Please select a task from the table to edit.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
            return;
        }

        String newName = activityNameField.getText().trim();
        LocalDate newDeadline = deadlinePicker.getValue();
        String newPriority = priorityComboBox.getValue();

        if (newName.isBlank() || newDeadline == null || newPriority == null) {
            messageLabel.setText("⚠️ Please complete all fields before saving edits.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
            return;
        }

        taskManager.editTask(selectedTask, newName, newDeadline, newPriority);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("✅ Task updated successfully.");
        messageLabel.setStyle("-fx-text-fill: #2ECC71;");
    }

    private void handleDeleteTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("⚠️ Please select a task to delete.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
            return;
        }

        taskManager.deleteTask(selectedTask);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("🗑️ Task deleted successfully.");
        messageLabel.setStyle("-fx-text-fill: #E74C3C;");
    }

    private void handleMarkCompleted() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("⚠️ Please select a task to mark as completed.");
            messageLabel.setStyle("-fx-text-fill: #E74C3C;");
            return;
        }

        taskManager.markTaskCompleted(selectedTask);
        refreshTable();
        messageLabel.setText("🎉 Task marked as completed!");
        messageLabel.setStyle("-fx-text-fill: #2ECC71;");
    }

    private void refreshTable() {
        taskTable.setItems(FXCollections.observableArrayList(taskManager.getAllTasks()));
    }

    private void clearTaskFields() {
        activityNameField.clear();
        deadlinePicker.setValue(LocalDate.now());
        priorityComboBox.setValue("Medium");
        taskTable.getSelectionModel().clearSelection();
    }
}
