package com.example.edgarnurullin.tp_schedule.content;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private Integer groupId;
    private String title;
    private String typeLesson;
    private String date;
    private String time;
    private String place;

    public Lesson() {
    }

    public Lesson(Integer groupId, String title, String typeLesson, String date, String time, String place) {
        this.groupId = groupId;
        this.title = title;
        this.typeLesson = typeLesson;
        this.date = date;
        this.time = time;
        this.place = place;
    }

    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer id) {
        this.groupId = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeLesson() {
        return typeLesson;
    }
    public void setTypeLesson(String typeLesson) {
        this.typeLesson = typeLesson;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setAndReversDate(String date) {
        setDate(reversDate(date));
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) { this.time = time; }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) { this.place = place; }

    //return date in YYYY/MM/DD format
    private String reversDate(String sourceDate) {
        // 08/16/2016 to 2016/08/16
        String [] partsDate = sourceDate.split("/");
        String year = partsDate[2];
        String month = partsDate[0];
        String day = partsDate[1];
        return year + "/" + month + "/" + day;
    }
}
