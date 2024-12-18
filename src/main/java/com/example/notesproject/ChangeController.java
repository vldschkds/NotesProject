package com.example.notesproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChangeController {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea textArea;

    @FXML
    private Button finallyChangeButton;

    private Data currentNote;

    @FXML
    void initialize() {
        finallyChangeButton.setOnAction(actionEvent -> {
            String newName = nameField.getText().trim();
            String newText = textArea.getText().trim();

            if (!newName.isEmpty() && !newText.isEmpty()) {
                updateNoteInDatabase(newName, newText);
                finallyChangeButton.getScene().getWindow().hide();
            } else {
                showAlert("Ошибка", "Поля не должны быть пустыми");
            }
        });
    }

    public void setNoteData(Data noteData) {
        this.currentNote = noteData;
        nameField.setText(noteData.getName_note());
        textArea.setText(noteData.getText_note());
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void updateNoteInDatabase(String newName, String newText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        currentNote.setName_note(newName);
        currentNote.setText_note(newText);

        dbHandler.updateNote(currentNote); // Обновляем запись в БД

        // Обновляем таблицу в MainController после изменения
        if (mainController != null) {
            mainController.loadTableData();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
