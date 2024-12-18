package com.example.notesproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aboutButton;

    @FXML
    private Button enterButton;

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
            enterButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main-view.fxml"));

            try {
                Parent root = loader.load();

                // Получаем контроллер главного окна
                MainController mainController = loader.getController();

                // Загружаем данные в таблицу
                mainController.loadTableData();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Notes");
                AppSize.setSize(stage);
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        aboutButton.setOnAction(actionEvent -> {
            aboutButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("about-view.fxml"));

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
            stage.showAndWait();
        });
    }
}
