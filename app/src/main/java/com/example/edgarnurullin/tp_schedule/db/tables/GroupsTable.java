package com.example.edgarnurullin.tp_schedule.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.db.SqliteHelper;

import java.util.ArrayList;
import java.util.List;


public class GroupsTable {

    public static final Uri URI = SqliteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull List<String> groupNames) {
        ContentValues[] values = new ContentValues[groupNames.size()];
        for (int i = 0; i < groupNames.size(); i++) {
            values[i] = toContentValues(groupNames.get(i), i);
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull String groupName, @NonNull int index) {
        ContentValues values = new ContentValues();
        values.put(Columns.ID, index);
        values.put(Columns.GROUP_NAME, groupName);
        return values;
    }

    @NonNull
    public static Group fromCursor(@NonNull Cursor cursor) {
        Group group = new Group();
        group.setId(cursor.getInt(cursor.getColumnIndex(Columns.ID)));
        group.setName(cursor.getString(cursor.getColumnIndex(Columns.GROUP_NAME)));
        return group;
    }

    @NonNull
    public static ArrayList<Group> listFromCursor(@NonNull Cursor cursor) {
        ArrayList<Group> groups = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return groups;
        }
        try {
            do {
                groups.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return groups;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String ID = "id";
        String GROUP_NAME = "group_name";
    }

    public interface Requests {

        String TABLE_NAME = GroupsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ID + " INT NOT NULL, " +
                Columns.GROUP_NAME + " VARCHAR(200) NOT NULL);";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
