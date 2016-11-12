package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Lesson;

import java.util.List;

public class AirportsResponse extends Response {

    @Override
    public void save(@NonNull Context context) {
        List<Lesson> airports = getTypedAnswer();
        if (airports != null) {
            //AirportsHelper.save(Realm.getDefaultInstance(), airports);
        }
    }
}
