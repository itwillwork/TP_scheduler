package com.example.edgarnurullin.tp_schedule;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.helpers.TimeHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable.listFromCursor;

public class ScheduleIntentService extends IntentService {
    //отдача расписания для группы группы
    public static final String ACTION_GET_SCHEDULE = "com.example.edgarnurullin.tp_schedule.action.GET_SCHEDULE";

    //отдача всех групп
    public static final String ACTION_GET_GROUPS = "com.example.edgarnurullin.tp_schedule.action.GET_GROUPS";

    //на обновление базы
    private static final String ACTION_NEED_FETCH = "com.example.edgarnurullin.tp_schedule.action.NEED_FETCH";

    //отдача расписания группы
    public static final String ACTION_RECEIVE_SCHEDULE = "com.example.edgarnurullin.tp_schedule.action.RECEIVE_SCHEDULE";

    //отдача групп
    public static final String ACTION_RECEIVE_GROUPS = "com.example.edgarnurullin.tp_schedule.action.RECEIVE_GROUPS";

    public ScheduleIntentService() {
        super("ScheduleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            Context lol = getApplicationContext();
SharedPreferences myPrefs = lol.getSharedPreferences("lol", 0);
            String wallPaper = myPrefs.getString("kek", null);
            Log.d("lol", "для дебаггера ");

            final String action = intent.getAction();
            if (ACTION_NEED_FETCH.equals(action)) {
                handleActionNeedFetch();
            } else if (ACTION_GET_GROUPS.equals(action)) {
                handleActionGetGroups();
            } else if (ACTION_GET_SCHEDULE.equals(action)) {
//                final String groupName = intent.getStringExtra(EXTRA_GROUP_NAME);
//                final int groupId = intent.getIntExtra(EXTRA_GROUP_ID, -1);
//                handleActionGetSchedule(groupName, groupId);
                handleActionGetSchedule();
            }
        }
    }

    private void handleActionNeedFetch() {
        //TODO связать как - нибудь с лоудером
        handleActionGetSchedule();
        handleActionGetGroups();
    }

    private void handleActionGetSchedule() {

        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
        String res = sPref.getString("kek", "");

        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        ArrayList<Group> result = GroupsTable.listFromCursor(cursor);

        final Intent outIntent = new Intent(ACTION_RECEIVE_GROUPS);
        outIntent.putParcelableArrayListExtra("groups", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }

    private void handleActionGetGroups() {
        //TODO определить selectedGroup
        Group selectedGroup = new Group(3, "АПО-22");
        //запрос за занятиями конкретной группы
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = getContentResolver().query(uri, null, " group_id = " + selectedGroup.getId(), null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);

        //проставляем название группы
        for (int idx = 0; idx < result.size(); idx++) {
            //result.get(idx).setGroupName(selectedGroup.getName());
        }

        result = passedActualLessons(result);

        Log.d("lol", "handleActionGetGroups");
    }


    private List<Lesson> passedActualLessons (List<Lesson> listLessons) {
        List<Lesson> result = new ArrayList<>();
        for (int idx = 0; idx < listLessons.size(); idx++) {
            String lessonDate = listLessons.get(idx).getDate();
            if (TimeHelper.getInstance().isFutureDate(lessonDate)) {
                result.add(listLessons.get(idx));
            }
        }
        return result;
    }
    public List<Lesson> getLessons () {
        //занятия всех групп
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = getContentResolver().query(uri, null, null, null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);

        result = passedActualLessons(result);

        //проставляем название группы
        List<Group> groups = getGroups();
        for (int idx = 0; idx < result.size(); idx++) {
            Lesson curLesson = result.get(idx);
            Integer group = curLesson.getGroupId();
            curLesson.setGroupName(groups.get(group).getName());
        }
        return result;
    }

    public List<Group> getGroups () {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        return GroupsTable.listFromCursor(cursor);
    }
}
