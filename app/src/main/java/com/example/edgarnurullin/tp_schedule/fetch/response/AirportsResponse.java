package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.AirportsTable;

import java.util.List;

public class AirportsResponse extends Response {
    @Override
    public void save(Context context) {
        Object airports = getTypedAnswer();
        if (airports != null) {

            AirportsTable.save(context, new Lesson());
        }
    }
}
