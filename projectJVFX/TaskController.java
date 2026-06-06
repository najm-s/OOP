package com.mycompany.try1;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
        root.setPadding(new Insets(15));

        Label titleLabel = new Label("Tasks for " + currentUser.getUsername());
        titleLabel.setFont(new Font(24));

        Button dashboardButton = new Button("Dashboard");
        Button logoutButton = new Button("Logout");

        dashboardButton.setOnAction(event -> mainApp.showDashboardPage(currentUser));
        logoutButton.setOnAction(event -> mainApp.logout());

        HBox topButtons = new HBox(10);
        topButtons.getChildren().addAll(dashboardButton, logoutButton);

        VBox topBox = new VBox(10);
        topBox.getChildren().addAll(titleLabel, topButtons);
        root.setTop(topBox);

        setupTaskTable();
        root.setCenter(taskTable);

        root.setBottom(createTaskForm());
        refreshTable();

        return root;
    }

    private void setupTaskTable() {
        taskTable = new TableView<>();

        TableColumn<Task, String> nameColumn = new TableColumn<>("Activity Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getActivityName()));
        nameColumn.setPrefWidth(260);

        TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
        deadlineColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDeadline().toString()));
        deadlineColumn.setPrefWidth(150);

        TableColumn<Task, String> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPriority()));
        priorityColumn.setPrefWidth(120);

        TableColumn<Task, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatusText()));
        statusColumn.setPrefWidth(120);

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
        activityNameField = new TextField();
        activityNameField.setPromptText("Enter activity name");

        deadlinePicker = new DatePicker(LocalDate.now());

        priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("High", "Medium", "Low");
        priorityComboBox.setValue("Medium");

        Button addButton = new Button("Add Task");
        Button editButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");
        Button completeButton = new Button("Mark Completed");
        Button clearButton = new Button("Clear Fields");

        addButton.setOnAction(event -> handleAddTask());
        editButton.setOnAction(event -> handleEditTask());
        deleteButton.setOnAction(event -> handleDeleteTask());
        completeButton.setOnAction(event -> handleMarkCompleted());
        clearButton.setOnAction(event -> clearTaskFields());

        messageLabel = new Label();

        GridPane form = new GridPane();
        form.setPadding(new Insets(15, 0, 0, 0));
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Activity Name:"), 0, 0);
        form.add(activityNameField, 1, 0);

        form.add(new Label("Deadline:"), 0, 1);
        form.add(deadlinePicker, 1, 1);

        form.add(new Label("Priority:"), 0, 2);
        form.add(priorityComboBox, 1, 2);

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(addButton, editButton, deleteButton, completeButton, clearButton);

        VBox bottomBox = new VBox(10);
        bottomBox.getChildren().addAll(form, buttons, messageLabel);

        return bottomBox;
    }

    private void handleAddTask() {
        String activityName = activityNameField.getText().trim();
        LocalDate deadline = deadlinePicker.getValue();
        String priority = priorityComboBox.getValue();

        if (activityName.isBlank() || deadline == null || priority == null) {
            messageLabel.setText("Please enter activity name, deadline, and priority.");
            return;
        }

        taskManager.addTask(activityName, deadline, priority);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("Task added successfully.");
    }

    private void handleEditTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("Please select a task to edit.");
            return;
        }

        String newName = activityNameField.getText().trim();
        LocalDate newDeadline = deadlinePicker.getValue();
        String newPriority = priorityComboBox.getValue();

        if (newName.isBlank() || newDeadline == null || newPriority == null) {
            messageLabel.setText("Please complete all fields before editing.");
            return;
        }

        taskManager.editTask(selectedTask, newName, newDeadline, newPriority);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("Task edited successfully.");
    }

    private void handleDeleteTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("Please select a task to delete.");
            return;
        }

        taskManager.deleteTask(selectedTask);
        refreshTable();
        clearTaskFields();
        messageLabel.setText("Task deleted successfully.");
    }

    private void handleMarkCompleted() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            messageLabel.setText("Please select a task to mark completed.");
            return;
        }

        taskManager.markTaskCompleted(selectedTask);
        refreshTable();
        messageLabel.setText("Task marked as completed.");
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
