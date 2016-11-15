package com.example.edgarnurullin.tp_schedule;

/**
 * Created by Polina on 13.11.2016.
 */

import android.os.Parcel;
import android.os.Parcelable;


public class Item implements Parcelable {

    String day;
    String time;
    String subject;
    String venue;
    String group;

    public Item(String day, String time, String subject, String venue, String group) {
        super();
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.venue = venue;
        this.group = group;
    }

    protected Item(Parcel in) {
        day = in.readString();
        time = in.readString();
        subject = in.readString();
        venue = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getSubject() {
        return subject;
    }

    public String getVenue() {
        return venue;
    }

    public String getGroup() {
        return group;
    }
    @Override
    public String toString() {
        return time + "\n" + subject + "\n" + venue;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(day);
        dest.writeString(time);
        dest.writeString(subject);
        dest.writeString(venue);
        dest.writeString(group);

    }



}