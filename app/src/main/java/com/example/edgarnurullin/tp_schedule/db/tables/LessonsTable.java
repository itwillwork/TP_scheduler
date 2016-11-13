package com.example.edgarnurullin.tp_schedule.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.SqliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LessonsTable {

    public static final Uri URI = SqliteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Lesson airport) {
        //context.getContentResolver().insert(URI, toContentValues(airport));
    }

    public static void save(Context context, Integer groupIdx, @NonNull List<Map<String, Object>> lessonsOfGroup) {
        ContentValues[] values = new ContentValues[lessonsOfGroup.size()];
        for (int i = 0; i < lessonsOfGroup.size(); i++) {
            Map<String, Object> lesson = lessonsOfGroup.get(i);
            values[i] = toContentValues(groupIdx, lesson);
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(Integer groupIdx, @NonNull Map<String, Object> lesson) {
        ContentValues values = new ContentValues();
        values.put(Columns.GROUP_ID, groupIdx);

        try {
            JSONObject lol = new JSONObject(lesson.toString());
            String[] jsonNames = lol.getNames();
            String lalka = lol.getString("short_title");
        } catch (JSONException e) {

        }

//        Type listType = new TypeToken<ArrayList<Lesson>>(){}.getType();

//        List<Lesson> yourClassList = new Gson().fromJson(jsonArray, listType);

//        Gson gson = new Gson();
//        Lesson pojo = gson.fromJson(lesson, Lesson.class);

 //       String lol = lesson.getField("short_title");
        //values.put(Columns.TITLE, lol);
//        values.put(Columns.TYPE_LESSON, airport.getName());
//        values.put(Columns.DATE, airport.getName());
//        values.put(Columns.TIME, airport.getAirportName());
        return values;

        //        event_title: "Лекция"
//        schedule_date: "10/04/2016"
//        start_time: "18:00"
//        short_title: "Perl"
        //группа
        //auto increment id

    }

    @NonNull
    public static Lesson fromCursor(@NonNull Cursor cursor) {
        //TODO
//        String iata = cursor.getString(cursor.getColumnIndex(Columns.IATA));
//        String name = cursor.getString(cursor.getColumnIndex(Columns.NAME));
//        String airportName = cursor.getString(cursor.getColumnIndex(Columns.AIRPORT_NAME));
//        return new Lesson(iata, name, airportName);
        return new Lesson();
    }

    @NonNull
    public static List<Lesson> listFromCursor(@NonNull Cursor cursor) {
        //TODO
//        List<Lesson> airports = new ArrayList<>();
//        if (!cursor.moveToFirst()) {
//            return airports;
//        }
//        try {
//            do {
//                airports.add(fromCursor(cursor));
//            } while (cursor.moveToNext());
//            return airports;
//        } finally {
//            cursor.close();
//        }
        return new ArrayList<Lesson>();
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        // id группы
        String GROUP_ID = "group_id";
        // Perl
        String TITLE = "title";
        // Лекция
        String TYPE_LESSON = "type_lesson";
        // 10/04/2016
        String DATE = "date";
        // 18:00
        String TIME = "time";

    }

    public interface Requests {

        String TABLE_NAME = LessonsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                " ID int NOT NULL AUTO_INCREMENT, " +
                Columns.GROUP_ID + " INT NOT NULL, " +
                Columns.TITLE + " VARCHAR(50), " +
                Columns.DATE + " VARCHAR(50), " +
                Columns.TIME + " VARCHAR(50), " +
                Columns.TYPE_LESSON + " VARCHAR(50)" + "PRIMARY KEY (ID) );";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
