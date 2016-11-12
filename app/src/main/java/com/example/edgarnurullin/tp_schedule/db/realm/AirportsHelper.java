package com.example.edgarnurullin.tp_schedule.db.realm;

import android.support.annotation.NonNull;

import com.example.edgarnurullin.tp_schedule.content.Lessons;

import java.util.List;

import io.realm.Realm;

/**
 * @author Artur Vasilov
 */
public class AirportsHelper {

    public static void save(@NonNull Realm realm, List<Lessons> airports) {
        realm.beginTransaction();
        realm.delete(Lessons.class);
        realm.copyToRealm(airports);
        realm.commitTransaction();
    }

    @NonNull
    public static List<Lessons> getAirports(@NonNull Realm realm) {
        return realm.where(Lessons.class).findAll();
    }
}
