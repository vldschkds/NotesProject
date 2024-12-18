package com.example.notesproject;

public class Data {
    private int id_notes; // ID заметки
    private String name_note; // Название заметки
    private String text_note; // Текст заметки
    private String date_note; // Дата создания

    // Полный конструктор
    public Data(int id_notes, String name_note, String text_note, String date_note) {
        this.id_notes = id_notes;
        this.name_note = name_note;
        this.text_note = text_note;
        this.date_note = date_note;
    }

    // Конструктор для случаев, когда ID и дата не указаны
    public Data(String name_note, String text_note) {
        this.name_note = name_note;
        this.text_note = text_note;
    }

    // Геттеры
    public int getId_notes() {
        return id_notes;
    }

    public String getName_note() {
        return name_note;
    }

    public void setName_note(String name) {
        this.name_note = name;
    }

    public String getText_note() {
        return text_note;
    }

    public void setText_note(String text) {
        this.text_note = text;
    }

    public String getDate_note() {
        return date_note;
    }
}
