package com.example.notesproject;

import com.sun.jdi.ClassNotLoadedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:postgresql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("org.postgresql.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        System.out.println("Connection String: " + connectionString);
        System.out.println("User: " + dbUser);
        return dbConnection;
    }

    public void signUpUser(Data data) {
        String insert = "INSERT INTO " + Const.NOTES_TABLE + "(" +
                Const.NOTES_NAME + "," + Const.NOTES_TEXT + ")" +
                " VALUES(?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, data.getName_note());
            prSt.setString(2, data.getText_note());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Data note) {
        String update = "UPDATE shama.notes_table SET name_note=?, text_note=? WHERE id_notes=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, note.getName_note());
            prSt.setString(2, note.getText_note());
            prSt.setInt(3, note.getId_notes());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(int noteId) {
        String delete = "DELETE FROM shama.notes_table WHERE id_notes = ?";

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(delete)) {
            prSt.setInt(1, noteId);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Data> getAllData() {
        List<Data> dataList = new ArrayList<>(); // Список для хранения всех записей

        // SQL-запрос для выбора всех записей из таблицы
        String select = "SELECT * FROM " + Const.NOTES_TABLE;

        try (Connection connection = getDbConnection();
             PreparedStatement prSt = connection.prepareStatement(select);
             ResultSet resSet = prSt.executeQuery()) {

            // Обработка результатов
            while (resSet.next()) {
                int id = resSet.getInt(Const.NOTES_ID); // Получаем значение столбца id
                String name = resSet.getString(Const.NOTES_NAME); // Получаем значение столбца NOTES_NAME
                String text = resSet.getString(Const.NOTES_TEXT); // Получаем значение столбца NOTES_TEXT
                String date = resSet.getString(Const.NOTES_DATE); // Получаем значение столбца date

                // Создаем объект Data и добавляем его в список
                dataList.add(new Data(id, name, text, date));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Обработка исключений
        }

        return dataList; // Возвращаем список всех записей
    }

}
