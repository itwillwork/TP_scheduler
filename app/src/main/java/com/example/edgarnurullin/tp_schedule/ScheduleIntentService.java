package com.example.edgarnurullin.tp_schedule;

import android.app.IntentService;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.fetch.ApiFactory;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;
import com.example.edgarnurullin.tp_schedule.fetch.response.RequestResult;
import com.example.edgarnurullin.tp_schedule.fetch.response.Response;
import com.example.edgarnurullin.tp_schedule.fetch.response.ScheduleResponse;
import com.example.edgarnurullin.tp_schedule.helpers.TimeHelper;
import com.example.edgarnurullin.tp_schedule.loaders.SheduleLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable.listFromCursor;

public class ScheduleIntentService extends IntentService {
    //отдача расписания для группы группы
    private static final String PREFIX = "com.example.edgarnurullin.tp_schedule.action.";
    public static final String ACTION_GET_SCHEDULE = PREFIX + "GET_SCHEDULE";

    //отдача всех групп
    public static final String ACTION_GET_GROUPS = PREFIX + "GET_GROUPS";

    //на обновление базы
    public static final String ACTION_NEED_FETCH = PREFIX + "NEED_FETCH";

    //отдача расписания группы
    public static final String ACTION_RECEIVE_SCHEDULE = PREFIX + "RECEIVE_SCHEDULE";

    //отдача групп
    public static final String ACTION_RECEIVE_GROUPS = PREFIX + "RECEIVE_GROUPS";

    public ScheduleIntentService() {
        super("ScheduleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEED_FETCH.equals(action)) {
                handleActionNeedFetch();
            } else if (ACTION_GET_GROUPS.equals(action)) {
                handleActionGetGroups();
            } else if (ACTION_GET_SCHEDULE.equals(action)) {
                handleActionGetSchedule();
            }
        }
    }

    private void handleActionNeedFetch() {
        try {
            Response response = apiCall();
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getApplicationContext());
                onSuccess();
            } else {
                onError();
            }
        } catch (IOException e) {
            onError();
        }
    }
    private void onSuccess() {
    }
    private void onError() {

    }
    private void handleActionGetSchedule() {
        int groupId = getGroupIdFromPreferences();
        String selection = " group_id = " + String.valueOf(groupId);
        Boolean isAllGroupSelected = false;
        if (groupId == 0) {
            isAllGroupSelected = true;
            selection = null;
        }

        Group selectedGroup = getGroupViaId(groupId);
        ArrayList<Lesson> result;

        if (selectedGroup == null) {
            result = null;
        } else {
            //запрос за занятиями конкретной группы
            Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
            Cursor cursor2 = getContentResolver().query(uri, null, selection, null, " date ASC, time ASC");
            result = LessonsTable.listFromCursor(cursor2);
            ArrayList<Group> groups = null;
            if (isAllGroupSelected) {
                groups = getGroups();
            }
            //проставляем название группы
            for (int idx = 0; idx < result.size(); idx++) {
                if (isAllGroupSelected) {
                    Lesson curLesson = result.get(idx);
                    int groudId = curLesson.getGroupId();
                    result.get(idx).setGroupName(groups.get(groudId).getName());
                } else {
                    result.get(idx).setGroupName(selectedGroup.getName());
                }
            }

            //пропустить только адекватные
            result = passedActualLessons(result);
        }



        //отправляем обратно занятия
        final Intent outIntent = new Intent(ACTION_RECEIVE_SCHEDULE);
        outIntent.putParcelableArrayListExtra("schedule", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }

    private void handleActionGetGroups() {
        //запрашиваем все группы
        ArrayList<Group> result = getGroups();

        //отправляем обратно группы
        final Intent outIntent = new Intent(ACTION_RECEIVE_GROUPS);
        outIntent.putParcelableArrayListExtra("groups", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }


    private ArrayList<Lesson> passedActualLessons (ArrayList<Lesson> listLessons) {
        ArrayList<Lesson> result = new ArrayList<>();
        for (int idx = 0; idx < listLessons.size(); idx++) {
            String lessonDate = listLessons.get(idx).getDate();
            if (TimeHelper.getInstance().isFutureDate(lessonDate)) {
                result.add(listLessons.get(idx));
            }
        }
        return result;
    }

    private ArrayList<Group> getGroups () {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = getContentResolver().query(uri, null, null, null, "id ASC");
        return GroupsTable.listFromCursor(cursor);
    }

    private Group getGroupViaId(int id) {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = getContentResolver().query(uri, null, "id=" + String.valueOf(id), null, "id ASC");
        ArrayList<Group> result =  GroupsTable.listFromCursor(cursor);
        if (result.size() != 0) {
            return result.get(0);
        }
        return null;
    }
    private int getGroupIdFromPreferences() {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        return myPrefs.getInt("groupId", 0);
    }

    protected Response apiCall() throws IOException {
        ShedulerService service = ApiFactory.getLessonsService();
        Call<Map<String, List<Map>>> call = service.lessons();
        Map<String, List<Map>> schedule = call.execute().body();
        return new ScheduleResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(schedule);
    }
}
