package com.example.edgarnurullin.tp_schedule;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ScrollingActivity extends AppCompatActivity {

    ArrayList<Lesson> lessons;
    ArrayList<Group> groups;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action=intent.getAction();
            // если приходят занятия группы
            if(action.equals(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE)) {
                lessons = intent.getParcelableArrayListExtra("schedule");
                Log.d("lessons", "onReceive");
                updateScheduler();
            }
            // если приходят группы
            else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_GROUPS)) {
                groups = intent.getParcelableArrayListExtra("groups");
                Log.d("groups", "onReceive");
                updateGroups();
            }
            // если приходят типы занятий которые отображены
            else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_TYPE_LESSONS)) {
                ArrayList<String> lol = intent.getStringArrayListExtra("types_lessons");
                Log.d("type_lessons", "onReceive");
            }
            else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_FETCH_ERROR)) {
                Toast.makeText(context, "Ошибка получения данных, что-то пошло не так (", Toast.LENGTH_LONG).show();
            }
            else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_FETCH_SUCCESS)) {
                Toast.makeText(context, "Расписание синхронизировано", Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        lessons = new ArrayList<Lesson>();
        groups = new ArrayList<Group>();

       //привязка интент сервера
        Intent intent = new Intent(ScrollingActivity.this, ScheduleIntentService.class);
        // для получения всего расписания
        // intent.setAction(ScheduleIntentService.ACTION_GET_SCHEDULE);
        // для получения всех групп
        // intent.setAction(ScheduleIntentService.ACTION_GET_GROUPS);
        // для обновления расписания
        intent.setAction(ScheduleIntentService.ACTION_NEED_FETCH);
        startService(intent);

        //TODO убрать
        //добавил чтобы тестить
        Intent intent2 = new Intent(ScrollingActivity.this, ScheduleIntentService.class);
        intent2.putExtra("type_lesson", "Семинар");
        intent2.setAction(ScheduleIntentService.ACTION_GET_TYPES_LESSONS);
        startService(intent2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_GROUPS);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_ERROR);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_SUCCESS);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_TYPE_LESSONS);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void updateGroups() {

        if (groups != null && groups.size() > 0) {
            List<String> group_names = new ArrayList<String>();
            for (Group cur_group : groups) {
                group_names.add(cur_group.getName());
            }
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScrollingActivity.this,
                    android.R.layout.simple_spinner_item, group_names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    Log.d("spinner", "onItemSelected");
                    //TODO надо починить уходит в вечный цикл
                    //при первом запуске приложения почему-то функция вызывается бесконечное число раз
                    // или если при повторном запуске приложения выбираю что-нибудь в дропдауне
                    // начинается огромное количество запросов в интент сервис и все виснит
                    setGroupIdToPreferences(pos);
                    Intent intent = new Intent(ScrollingActivity.this, ScheduleIntentService.class);
                    intent.setAction(ScheduleIntentService.ACTION_GET_SCHEDULE);
                    startService(intent);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });
        }
    }


    private void updateScheduler() {
        if (lessons != null) {
            try {
                JSONArray cur_scheduler = new JSONArray();
                for (Lesson cur_lesson : lessons) {
                    JSONObject json_lesson = new JSONObject();
                    json_lesson.put("discipline", cur_lesson.getTitle());
                    json_lesson.put("status", cur_lesson.getTypeLesson());
                    json_lesson.put("location", cur_lesson.getPlace());
                    json_lesson.put("startTime",  cur_lesson.getDate() +" "+ cur_lesson.getTime());
                    cur_scheduler.put(json_lesson);
                }

                String[] weekdays = {"СБ", "ВС", "ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"};
                String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
                        "июля", "августа", "сентября", "октября", "ноября", "декабря"};
                String delimeter = ", ";
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pull_city);
                linearLayout.removeAllViewsInLayout();
                linearLayout.setPadding(0, 0, 0, 50);

                for (int i = 0; i < cur_scheduler.length()-1; i++) {
                        try {
                        JSONObject dateLesson = cur_scheduler.getJSONObject(i);
                        String nameLesson = dateLesson.getString("discipline");
                        String locationLesson = delimeter + dateLesson.getString("location");
                        String statusLesson = dateLesson.getString("status") + delimeter;
                        DateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm", Locale
                                .ENGLISH);
                        Date date = format.parse(dateLesson.getString("startTime"));

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        String weekdayLesson = weekdays[calendar.get(Calendar.DAY_OF_WEEK)];
                        Integer dayLesson = calendar.get(Calendar.DAY_OF_MONTH);
                        String monthLesson = " " + months[calendar.get(Calendar.MONTH)];

                        LinearLayout lessonNodeH = new LinearLayout(this);
                        LinearLayout lessonNodeV = new LinearLayout(this);
                        LinearLayout lessonNodeH2 = new LinearLayout(this);
                        lessonNodeV.setOrientation(LinearLayout.VERTICAL);
                        lessonNodeH.setOrientation(LinearLayout.HORIZONTAL);
                        lessonNodeH2.setOrientation(LinearLayout.HORIZONTAL);

                        TextView nameLessonNode = new TextView(this);
                        nameLessonNode.setTextSize(14);
                        nameLessonNode.setText(statusLesson + nameLesson);

                        TextView weekdayLessonNode = new TextView(this);
                        weekdayLessonNode.setTextSize(28);
                        weekdayLessonNode.setText(weekdayLesson);

                        TextView dateLessonNode = new TextView(this);
                        dateLessonNode.setTextSize(14);
                        dateLessonNode.setText(dayLesson + monthLesson + locationLesson);

                        weekdayLessonNode.setPadding(10, 0, 0, 0);
                        lessonNodeV.setPadding(30, 0, 10, 0);
                        lessonNodeH.setPadding(0, 30, 0, 30);

                        lessonNodeV.addView(nameLessonNode);
                        lessonNodeV.addView(dateLessonNode);

                        lessonNodeH.addView(weekdayLessonNode);
                        lessonNodeH.addView(lessonNodeV);
                        linearLayout.addView(lessonNodeH);
                    } catch (ParseException e) {
                    }
                }
            } catch (JSONException e) {}
        }
    }

    // функция для записи выбранного id группы в преференсы
    private void setGroupIdToPreferences(int id) {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt("groupId", id);
        prefsEditor.commit();
    }

}

