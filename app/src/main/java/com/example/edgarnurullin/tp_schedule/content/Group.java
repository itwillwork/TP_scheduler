package com.example.edgarnurullin.tp_schedule.content;

import android.icu.text.Replaceable;
import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {
    private Integer id;
    private String name;

    public Group() {
        this.id = null;
        this.name = "";
    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(id);
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        // распаковываем объект из Parcel
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    private Group(Parcel parcel) {
        name = parcel.readString();
        id = parcel.readInt();
    }
}
