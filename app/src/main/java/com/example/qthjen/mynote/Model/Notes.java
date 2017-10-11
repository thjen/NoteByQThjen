package com.example.qthjen.mynote.Model;

import java.io.Serializable;

public class Notes implements Serializable {

    private int id;
    private String title;
    private String note;
    private String date;
    private byte[] image;

    public Notes(int id, String title, String note, String date, byte[] image) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
