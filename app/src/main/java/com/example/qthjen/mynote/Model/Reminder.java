package com.example.qthjen.mynote.Model;

public class Reminder {

    private int Id;
    private String Note;
    private String Date;

    public Reminder(int Id, String Note, String Date) {
        this.Id = Id;
        this.Note = Note;
        this.Date = Date;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getNote() {
        return Note;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDate() {
        return Date;
    }

}
