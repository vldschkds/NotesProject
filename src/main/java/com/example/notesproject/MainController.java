package com.example.notesproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button lookButton;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Data> tableData;

    @FXML
    private TableColumn<Data, Integer> idColumn; // Колонка для ID

    @FXML
    private TableColumn<Data, String> nameColumn; // Колонка для названия

    @FXML
    private TableColumn<Data, String> textColumn; // Колонка для текста

    @FXML
    private TableColumn<Data, String> dateColumn; // Колонка для даты

    void loadTableData() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Data> dataList = FXCollections.observableArrayList(dbHandler.getAllData());

        // Привязка колонок
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_notes"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_note"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text_note"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_note"));

        // Устанавливаем данные
        tableData.setItems(dataList);
    }

    private void openChangeView(Data noteData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("change-view.fxml"));
            Parent root = loader.load();

            // Передача данных в ChangeController
            ChangeController controller = loader.getController();
            controller.setNoteData(noteData);
            controller.setMainController(this); // Передаем ссылку на текущий MainController

            Stage stage = new Stage();
            stage.setTitle("Изменить заметку");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // После закрытия окна изменения — обновляем таблицу
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonYes = new ButtonType("Да");
        ButtonType buttonNo = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        return alert.showAndWait().orElse(buttonNo) == buttonYes;
    }

    private void showWarningDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openLookView(Data noteData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("look-view.fxml"));
            Parent root = loader.load();

            // Передача данных в LookController
            LookController controller = loader.getController();
            controller.setNoteData(noteData); // Передаем выбранную запись

            Stage stage = new Stage();
            stage.setTitle("Просмотр записи");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        loadTableData();
        tableData.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);


        lookButton.setOnAction(actionEvent -> {
            Data selectedData = tableData.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                openLookView(selectedData);
            } else {
                System.out.println("Ни одна запись не выбрана!"); // Сообщение при отсутствии выбора
            }
        });

        addButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add-view.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Добавить заметку");
            stage.showAndWait();

            // Обновляем таблицу после добавления
            loadTableData();
        });

        changeButton.setOnAction(actionEvent -> {
            Data selectedData = tableData.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                openChangeView(selectedData);
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            ObservableList<Data> selectedItems = tableData.getSelectionModel().getSelectedItems();

            if (selectedItems != null && !selectedItems.isEmpty()) {
                // Подтверждение удаления
                boolean confirm = showConfirmationDialog("Удалить заметки?", "Вы уверены, что хотите удалить выбранные заметки?");
                if (confirm) {
                    DatabaseHandler dbHandler = new DatabaseHandler();

                    // Проходим по всем выбранным заметкам и удаляем их
                    for (Data note : selectedItems) {
                        dbHandler.deleteNote(note.getId_notes());
                    }

                    // Обновляем таблицу после удаления
                    loadTableData();
                }
            } else {
                showWarningDialog("Ничего не выбрано", "Пожалуйста, выберите хотя бы одну заметку для удаления.");
            }
        });

        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("register-view.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Notes");
            AppSize.setSize(stage);
            stage.show();
        });
    }
}
