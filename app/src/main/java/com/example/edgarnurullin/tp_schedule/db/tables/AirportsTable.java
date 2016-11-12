package com.example.edgarnurullin.tp_schedule.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Lessons;
import com.example.edgarnurullin.tp_schedule.db.SqliteHelper;

import java.util.ArrayList;
import java.util.List;


public class AirportsTable {

    public static final Uri URI = SqliteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull Lessons airport) {
        context.getContentResolver().insert(URI, toContentValues(airport));
    }

    public static void save(Context context, @NonNull List<Lessons> airports) {
        ContentValues[] values = new ContentValues[airports.size()];
        for (int i = 0; i < airports.size(); i++) {
            values[i] = toContentValues(airports.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Lessons airport) {
        ContentValues values = new ContentValues();
        values.put(Columns.IATA, airport.getIata());
        values.put(Columns.NAME, airport.getName());
        values.put(Columns.AIRPORT_NAME, airport.getAirportName());
        return values;
    }

    @NonNull
    public static Lessons fromCursor(@NonNull Cursor cursor) {
        String iata = cursor.getString(cursor.getColumnIndex(Columns.IATA));
        String name = cursor.getString(cursor.getColumnIndex(Columns.NAME));
        String airportName = cursor.getString(cursor.getColumnIndex(Columns.AIRPORT_NAME));
        return new Lessons(iata, name, airportName);
    }

    @NonNull
    public static List<Lessons> listFromCursor(@NonNull Cursor cursor) {
        List<Lessons> airports = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return airports;
        }
        try {
            do {
                airports.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return airports;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String IATA = "iata";
        String NAME = "name";
        String AIRPORT_NAME = "airport_name";
    }

    public interface Requests {

        String TABLE_NAME = AirportsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.IATA + " VARCHAR(10) NOT NULL, " +
                Columns.NAME + " VARCHAR(200), " +
                Columns.AIRPORT_NAME + " VARCHAR(200)" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
