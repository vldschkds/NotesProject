module com.example.notesproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.sql;
    requires jdk.jdi;


    opens com.example.notesproject to javafx.fxml;
    exports com.example.notesproject;
}