package com.example.edgarnurullin.tp_schedule.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.edgarnurullin.tp_schedule.helpers.TimeHelper;

public class Lesson implements Parcelable {
    private Integer groupId;
    private String groupName;
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
        setDate(TimeHelper.getInstance().reversDate(date));
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) { this.time = time; }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) { this.place = place; }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) { this.groupName = groupName; }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(groupId);
        parcel.writeString(groupName);
        parcel.writeString(title);
        parcel.writeString(typeLesson);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(place);
    }

    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {
        // распаковываем объект из Parcel
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    private Lesson(Parcel parcel) {
        groupId = parcel.readInt();
        title = parcel.readString();
        groupName = parcel.readString();
        typeLesson = parcel.readString();
        date = parcel.readString();
        time = parcel.readString();
        place = parcel.readString();
    }
}
