package com.example.edgarnurullin.tp_schedule.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.SqliteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LessonsTable {

    public static final Uri URI = SqliteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, Integer groupIdx, @NonNull List<Map> lessonsOfGroup) {
        ContentValues[] values = new ContentValues[lessonsOfGroup.size()];
        for (int i = 0; i < lessonsOfGroup.size(); i++) {
            Map<String, String> lesson = lessonsOfGroup.get(i);
            values[i] = toContentValues(groupIdx, lesson);
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    public static Lesson crutchForSerializationObject(Map<String, String> data) {
        List<String> keysFields = new ArrayList(data.keySet());
        Lesson resultLesson = new Lesson();
        for (int idx = 0; idx < keysFields.size(); idx++) {
            String key = keysFields.get(idx);
            if (key.equals("event_title")) {
                resultLesson.setTypeLesson(data.get(key));
            } else if (key.equals("schedule_date")) {
                resultLesson.setAndReversDate(data.get(key));
            } else if (key.equals("start_time")) {
                resultLesson.setTime(data.get(key));
            } else if (key.equals("auditorium_title")) {
                resultLesson.setPlace(data.get(key));
            } else if (key.equals("place_title")) {
                resultLesson.setPlace(data.get(key));
            } else if (key.equals("title")) {
                resultLesson.setTitle(data.get(key));
            } else if (key.equals("short_title")) {
                resultLesson.setTitle(data.get(key));
            } else if (key.equals("lesson_topic")) {
                String lol = data.get(key);
                //resultLesson.setTitle(data.get(key));
            } else if (key.equals("lesson_title")) {
                String lol = data.get(key);
                //resultLesson.setTitle(data.get(key));
            } else if (key.equals("lesson_tutors")) {
                Object[] tutors = data.values().toArray();
                String stringTutors = tutors[tutors.length - 1].toString();
                stringTutors = stringTutors.substring(1, stringTutors.length()-1);
                Log.d("tutors", stringTutors);
                //resultLesson.setTitle(data.get(key));
            } else if (key.equals("type_entity")) {
                String lol = data.get(key);
                //resultLesson.setTitle(data.get(key));
            } else if (key.equals("discipline_blog")) {
                String lol = data.get(key);
                //resultLesson.setTitle(data.get(key));
            } else if (key.equals("discipline_link")) {
                String lol = data.get(key);
                //resultLesson.setTitle(data.get(key));
            } else {
                //default
            }
        }
        return resultLesson;
    }

    @NonNull
    public static ContentValues toContentValues(Integer groupIdx, @NonNull Map<String, String> lesson) {
        ContentValues values = new ContentValues();
        values.put(Columns.GROUP_ID, groupIdx);

        Lesson lessonData = crutchForSerializationObject(lesson);

        values.put(Columns.TITLE, lessonData.getTitle());
        values.put(Columns.TYPE_LESSON, lessonData.getTypeLesson());
        values.put(Columns.DATE, lessonData.getDate());
        values.put(Columns.TIME, lessonData.getTime());
        values.put(Columns.PLACE, lessonData.getPlace());
        return values;
    }

    @NonNull
    public static Lesson fromCursor(@NonNull Cursor cursor) {
        Integer groupId = cursor.getInt(cursor.getColumnIndex(Columns.GROUP_ID));
        String title = cursor.getString(cursor.getColumnIndex(Columns.TITLE));
        String typeLesson = cursor.getString(cursor.getColumnIndex(Columns.TYPE_LESSON));
        String date = cursor.getString(cursor.getColumnIndex(Columns.DATE));
        String time = cursor.getString(cursor.getColumnIndex(Columns.TIME));
        String place = cursor.getString(cursor.getColumnIndex(Columns.PLACE));
        return new Lesson(groupId, title, typeLesson, date, time, place);
    }

    @NonNull
    public static ArrayList<Lesson> listFromCursor(@NonNull Cursor cursor) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return lessons;
        }
        try {
            do {
                lessons.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return lessons;
        } finally {
            cursor.close();
        }
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
        // ауд. 395
        String PLACE = "place";

    }

    public interface Requests {

        String TABLE_NAME = LessonsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                " id INTEGER PRIMARY KEY ASC, " +
                Columns.GROUP_ID + " INT NOT NULL, " +
                Columns.TITLE + " VARCHAR(50), " +
                Columns.DATE + " VARCHAR(50), " +
                Columns.TIME + " VARCHAR(50), " +
                Columns.PLACE + " VARCHAR(50), " +
                Columns.TYPE_LESSON + " VARCHAR(50)" + " );";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
