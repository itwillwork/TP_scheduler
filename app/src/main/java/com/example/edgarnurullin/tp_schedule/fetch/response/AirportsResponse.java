package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.AirportsTable;

import java.util.List;

public class AirportsResponse extends Response {

//    @Override
//    public void save(@NonNull Context context) {
//        List<Lesson> airports = getTypedAnswer();
//        if (airports != null) {
//            //AirportsHelper.save(Realm.getDefaultInstance(), airports);
//        }
//    }
    @Override
    public void save(Context context) {
        Log.d("lol", "234234");
        List<Lesson> airports = getTypedAnswer();
        if (airports != null) {
            AirportsTable.save(context, airports);
        }
    }
}
