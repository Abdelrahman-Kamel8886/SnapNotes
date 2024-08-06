package com.abdok.snapnotes.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String header;
    private String content;
    private long timestamp ;
    private StyleModel style;

    public NoteModel() {
    }

    public NoteModel(String header, String content, long timestamp, StyleModel style) {
        this.header = header;
        this.content = content;
        this.timestamp = timestamp;
        this.style = style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StyleModel getStyle() {
        return style;
    }

    public void setStyle(StyleModel style) {
        this.style = style;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
