package edu.gcu.today.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String date;
    private String time;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }



    public Event(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;

    }

    public void setId(int id) {
        this.id = id;
    }
}
