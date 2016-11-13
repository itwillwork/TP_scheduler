package com.example.edgarnurullin.tp_schedule.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "com.example.edgarnurullin.tp_schedule";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String DATABASE_NAME = ".db.ScheduleContentProvider";

    private static final int DATABASE_VERSION = 1;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GroupsTable.Requests.CREATION_REQUEST);
        db.execSQL(LessonsTable.Requests.CREATION_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GroupsTable.Requests.DROP_REQUEST);
        db.execSQL(LessonsTable.Requests.DROP_REQUEST);
        onCreate(db);
    }
}