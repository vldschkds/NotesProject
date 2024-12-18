package com.example.notesproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button finallyAddButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea textArea;

    @FXML
    void initialize() {

        finallyAddButton.setOnAction(actionEvent -> {

            Alert a = new Alert(Alert.AlertType.NONE);
            String name_text = nameField.getText().trim();

            if(!name_text.isEmpty()) {
                getNewData();
                finallyAddButton.getScene().getWindow().hide();
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setTitle("Ошибка");
                a.setHeaderText("Пустое значение");
                a.setContentText("Введите название заметки!");

            }
            else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Ошибка");
                a.setHeaderText("Пустое значение");
                a.setContentText("Введите название заметки!");
                // show the dialog
                a.show();
            }
        });
    }

    private void getNewData() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String name_note = nameField.getText();
        String text_note = textArea.getText();

        Data data = new Data(name_note, text_note);

        dbHandler.signUpUser(data);

    }
}
