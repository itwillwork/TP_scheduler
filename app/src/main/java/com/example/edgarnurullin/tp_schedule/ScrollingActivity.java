package com.example.edgarnurullin.tp_schedule;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrollingActivity extends AppCompatActivity {

    ArrayList<Lesson> lessons;
    ArrayList<Group> groups;
    Spinner spinner;
    int spinnetItem;
    FragmentScheduler myFragment;

    protected android.support.v4.app.FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;
    protected Bundle mSavedInstanceState;

    private Tracker mTracker;
    private final String nameForTracker = "ScrollingActivity";
    private static final Logger LOGGER = LoggerFactory.getLogger(ScrollingActivity.class);
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
                LOGGER.error("Ошибка фетча");
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("error fetch")
                        .build());
            }
            else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_FETCH_SUCCESS)) {
                Toast.makeText(context, "Расписание синхронизировано", Toast.LENGTH_LONG).show();
                LOGGER.info("Успешный фетч");
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("success fetch")
                        .build());
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


       // startActivity(new Intent(ScrollingActivity.this, FragmentScheduler.class));

//        //TODO убрать
//        //добавил чтобы тестить
//        Intent intent2 = new Intent(ScrollingActivity.this, ScheduleIntentService.class);
//        intent2.putExtra("type_lesson", "Семинар");
//        intent2.setAction(ScheduleIntentService.ACTION_GET_TYPES_LESSONS);
//        startService(intent2);


        // Получение экземпляра общедоступного счетчика.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        LOGGER.info("onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_GROUPS);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_ERROR);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_SUCCESS);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_TYPE_LESSONS);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        mTracker.setScreenName("Image~" + nameForTracker);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    };


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    };

    @Override
    protected void onStart() {
        super.onStart();
//        final IntentFilter filter = new IntentFilter();
//        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE);
//        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_GROUPS);
//        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_ERROR);
//        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_FETCH_SUCCESS);
//        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_TYPE_LESSONS);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("start application")
                .build());
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Lessons", lessons);
        outState.putSerializable("Groups", groups);
        outState.putInt("selected_pos_spinner", spinner.getSelectedItemPosition());
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessons =  (ArrayList<Lesson>)  savedInstanceState.getSerializable("Lessons");
        groups =  (ArrayList<Group>)  savedInstanceState.getSerializable("Groups");
        spinnetItem = savedInstanceState.getInt("selected_pos_spinner");
    }


    private void updateGroups() {
        if (groups != null && groups.size() > 0) {
            List<String> group_names = new ArrayList<String>();
            for (Group cur_group : groups) {
                group_names.add(cur_group.getName());
            }
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScrollingActivity.this,
                    android.R.layout.simple_spinner_item, group_names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setSelection(spinnetItem);
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

    void SetFragments () {

        myFragment = new FragmentScheduler();

        Bundle args = new Bundle();
        args.putSerializable("Lesson", lessons);

        // получаем экземпляр FragmentTransaction
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        myFragment.setArguments(args);
        fragmentTransaction.add(R.id.fragment_container, myFragment);
        fragmentTransaction.commit();

    }




    private void updateScheduler() {

        myFragment = new FragmentScheduler();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fragment_container);
        linearLayout.removeAllViewsInLayout();

        Bundle args = new Bundle();
        args.putSerializable("Lesson", lessons);

        // получаем экземпляр FragmentTransaction
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        myFragment.setArguments(args);
        fragmentTransaction.add(R.id.fragment_container, myFragment);
        fragmentTransaction.commit();

    }

    // функция для записи выбранного id группы в преференсы
    private void setGroupIdToPreferences(int id) {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt("groupId", id);
        prefsEditor.commit();
    }

}

