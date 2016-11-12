package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Lessons;
import com.example.edgarnurullin.tp_schedule.db.realm.AirportsHelper;

import java.util.List;

import io.realm.Realm;

public class AirportsResponse extends Response {

    @Override
    public void save(@NonNull Context context) {
        List<Lessons> airports = getTypedAnswer();
        if (airports != null) {
            AirportsHelper.save(Realm.getDefaultInstance(), airports);
        }
    }
}
