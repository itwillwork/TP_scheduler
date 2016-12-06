package com.example.edgarnurullin.tp_schedule;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import retrofit2.Call;

public class ScheduleIntentService extends IntentService {

    private static final String PREFIX = "com.example.edgarnurullin.tp_schedule.action.";
    private static final String DB_PREFIX = "content://com.example.edgarnurullin.tp_schedule/";

    //отдача расписания для группы группы
    public static final String ACTION_GET_SCHEDULE = PREFIX + "GET_SCHEDULE";
    //отдача всех групп
    public static final String ACTION_GET_GROUPS = PREFIX + "GET_GROUPS";
    //на обновление базы
    public static final String ACTION_NEED_FETCH = PREFIX + "NEED_FETCH";
    //отдача по нужному типу занятия выбранной группы из базы
    public static final String ACTION_GET_TYPES_LESSONS = PREFIX + "GET_TYPES_LESSONS";

    //отдача расписания группы
    public static final String ACTION_RECEIVE_SCHEDULE = PREFIX + "RECEIVE_SCHEDULE";
    //отдача групп
    public static final String ACTION_RECEIVE_GROUPS = PREFIX + "RECEIVE_GROUPS";
    //отдача групп
    public static final String ACTION_RECEIVE_TYPE_LESSONS = PREFIX + "RECEIVE_TYPE_LESSONS";
    //удачный фетч
    public static final String ACTION_RECEIVE_FETCH_ERROR = PREFIX + "RECEIVE_FETCH_ERROR";
    //неудачный фетч
    public static final String ACTION_RECEIVE_FETCH_SUCCESS = PREFIX + "RECEIVE_FETCH_SUCCESS";
    //неудачная обработка результат fetch
    public static final String ACTION_RECEIVE_FETCH_TREATMENT_ERROR = PREFIX + "RECEIVE_FETCH_TREATMENT_ERROR";

    public ScheduleIntentService() {
        super("ScheduleIntentService");
    }

    public void onCreate() {
        super.onCreate();
        updateAllReceiveInfo();
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
                Log.d("action", "handleActionGetSchedule");
                handleActionGetSchedule();
            } else if (ACTION_GET_TYPES_LESSONS.equals(action)) {
                handleActionGetTypesLessons(intent.getExtras().getString("type_lesson"));
            }
        }
    }

    /**
     * Функция возвращающая все будующие уроки выбранной группы
     */
    private ArrayList<Lesson> getLessonsOfSelectedGroup() {
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
            Uri uri = Uri.parse(DB_PREFIX + LessonsTable.Requests.TABLE_NAME);
            Cursor cursor2 = getContentResolver().query(uri, null, selection, null, " date ASC, time ASC");
            result = LessonsTable.listFromCursor(cursor2);
            ArrayList<Group> groups = null;
            if (isAllGroupSelected) {
                groups = getAllGroups();
            }

            //проставляем название группы
            for (int idx = 0; idx < result.size(); idx++) {
                Lesson curLesson = result.get(idx);
                if (isAllGroupSelected) {
                    int groudId = curLesson.getGroupId();
                    curLesson.setGroupName(groups.get(groudId).getName());
                } else {
                    curLesson.setGroupName(selectedGroup.getName());
                }
            }

            //пропустить только адекватные
            result = passedActualLessons(result);
        }

        return result;
    }

    /**
     * Обработчик запроса данных расписания
     */
    private void handleActionGetSchedule() {
        Log.d("intentService", "handleActionGetSchedule");
        ArrayList<Lesson> schedule = getLessonsOfSelectedGroup();

        //отправляем обратно занятия
        final Intent outIntent = new Intent(ACTION_RECEIVE_SCHEDULE);
        outIntent.putParcelableArrayListExtra("schedule", schedule);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);

        receiveTypesLessons(schedule);
    }

    /**
     * Функция кидающая данные о типах занятий, вызывается в обработчике запроса расписания handleActionGetSchedule
     */
    private void receiveTypesLessons(ArrayList<Lesson> schedule) {
        HashSet<String> typeLessons = new HashSet<String>();
        for (int idx = 0; idx < schedule.size(); idx++) {
            typeLessons.add(schedule.get(idx).getTypeLesson());
        }

        //отправляем обратно типы занятия
        final Intent outIntent = new Intent(ACTION_RECEIVE_TYPE_LESSONS);
        outIntent.putStringArrayListExtra("types_lessons", new ArrayList<String>(typeLessons));
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }

    /**
     * Обработчик запроса занятий по типу занятия
     */
    private void handleActionGetTypesLessons(String type) {
        ArrayList<Lesson> schedule = getLessonsOfSelectedGroup();
        ArrayList<Lesson> scheduleBeforeTreatment = new ArrayList<Lesson>();
        for (int idx = 0; idx < schedule.size(); idx++) {
            Lesson curLesson = schedule.get(idx);
            if (curLesson.getTypeLesson().equals(type)) {
                scheduleBeforeTreatment.add(curLesson);
            }
        }
        final Intent outIntent = new Intent(ACTION_RECEIVE_SCHEDULE);
        outIntent.putParcelableArrayListExtra("schedule", scheduleBeforeTreatment);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }

    /**
     * Обработчик запроса всех групп
     */
    private void handleActionGetGroups() {
        //запрашиваем все группы
        ArrayList<Group> result = getAllGroups();

        //отправляем обратно группы
        final Intent outIntent = new Intent(ACTION_RECEIVE_GROUPS);
        outIntent.putParcelableArrayListExtra("groups", result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
    }


    /**
     * Отсеиватель уже прошедших занятий
     */
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

    /**
     * Функция возвращающая все группы
     */
    private ArrayList<Group> getAllGroups () {
        Uri uri = Uri.parse(DB_PREFIX + GroupsTable.Requests.TABLE_NAME);
        Cursor cursor = getContentResolver().query(uri, null, null, null, "id ASC");
        return GroupsTable.listFromCursor(cursor);
    }

    /**
     * Запрос в бд для получения объекта группы зная id группы в базе
     */
    private Group getGroupViaId(int id) {
        Uri uri = Uri.parse(DB_PREFIX + GroupsTable.Requests.TABLE_NAME);
        Cursor cursor = getContentResolver().query(uri, null, "id=" + String.valueOf(id), null, "id ASC");
        ArrayList<Group> result =  GroupsTable.listFromCursor(cursor);
        if (result.size() != 0) {
            return result.get(0);
        }
        return null;
    }

    /**
     * Получаем id выбранной группы из преференсов
     */
    private int getGroupIdFromPreferences() {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        return myPrefs.getInt("groupId", 0);
    }
    /**
     * Ставим id выбранной группы в преференсы
     */
    private void setFetchDateToPreferences() {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString("fetchDate", TimeHelper.getInstance().getDate());
        prefsEditor.commit();
    }

    /**
     * Запускает цепную реакцию отдачу во вне расписания выбранной группа (внутри типов занятий) и списка групп
     */
    private void updateAllReceiveInfo() {
        handleActionGetGroups();
        handleActionGetSchedule();
    }

    /**
     * Обработчик запроса на синхронизацию расписания
     */
    private void handleActionNeedFetch() {
        try {
            Response response = apiCall();
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getApplicationContext());
                onSuccess();
            } else {
                onParsingError();
            }
        } catch (IOException e) {
            onError();
        }
    }
    private void onSuccess() {
        receiveStatus(ACTION_RECEIVE_FETCH_SUCCESS);
        setFetchDateToPreferences();
        updateAllReceiveInfo();
    }
    private void onError() {
        receiveStatus(ACTION_RECEIVE_FETCH_ERROR);
    }
    private void onParsingError() {
        receiveStatus(ACTION_RECEIVE_FETCH_TREATMENT_ERROR);
    }

    /**
     * Кидает во вне статус фетча
     */
    private void receiveStatus (String status) {
        final Intent outIntent = new Intent(status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
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
