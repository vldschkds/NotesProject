package com.example.notesproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea textArea;

    // Метод для передачи данных из MainController
    public void setNoteData(Data note) {
        nameField.setText(note.getName_note());
        textArea.setText(note.getText_note());
    }

}
