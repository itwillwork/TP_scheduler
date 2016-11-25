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

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.dbApi;

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

    public ArrayList<Lesson> lessons;
    public ArrayList<Group> groups;

    private com.example.edgarnurullin.tp_schedule.db.dbApi dbApi;
    private BroadcastReceiver receiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbApi = new dbApi(getContentResolver());
        setContentView(R.layout.activity_scrolling);

        //привязка интент сервера
        Intent intent = new Intent(ScrollingActivity.this, ScheduleIntentService.class);
        // для получения всего расписания
        // intent.setAction(ScheduleIntentService.ACTION_GET_SCHEDULE);
        // для получения всех групп
        // intent.setAction(ScheduleIntentService.ACTION_GET_GROUPS);
        // для обновления расписания
        intent.setAction(ScheduleIntentService.ACTION_NEED_FETCH);
        startService(intent);

        try{
          JSONArray jsonArray = new JSONArray("" +
                    "[" +
                    "{discipline: \"Тестирование\", status: \"ЛК\", location: \"ауд. 395\", startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\", status: \"РК\", location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "{discipline: \"Тестирование\", location: \"ауд. 395\", status: \"ЛК\",  startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\", status: \"СМ\",  location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "{discipline: \"Тестирование\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\",  status: \"РК\", location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "{discipline: \"Тестирование\", location: \"ауд. 395\", status: \"ЛК\",  startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\",  status: \"СМ\", location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "{discipline: \"Тестирование\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\",  status: \"ЛК\", location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "]");

            String[] weekdays = {"СБ", "ВС", "ПН", "ВТ", "СР", "ЧТ", "ПТ"};
            String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            String delimeter = ", ";
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pull_city);
            linearLayout.setPadding(0, 0, 0, 50);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject dateLesson = jsonArray.getJSONObject(i);
                    String nameLesson = dateLesson.getString("discipline");
                    String locationLesson = delimeter + dateLesson.getString("location");
                    String statusLesson = dateLesson.getString("status") + delimeter;
                    DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy", Locale.ENGLISH);
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
                } catch (ParseException e) {}
            }
        } catch (JSONException e) {}

    }

    protected void onStart() {
        super.onStart();
        //бродкаст ресивер
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE);
        filter.addAction(ScheduleIntentService.ACTION_RECEIVE_GROUPS);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action=intent.getAction();
                // если приходят занятия группы
                if(action.equals(ScheduleIntentService.ACTION_RECEIVE_SCHEDULE)) {
                    ArrayList<Lesson> lessons = intent.getParcelableArrayListExtra("schedule");
                    Log.d("lessons", "onReceive");
                }
                // если приходят группы
                else if(action.equals(ScheduleIntentService.ACTION_RECEIVE_GROUPS)) {
                    ArrayList<Group> groups = intent.getParcelableArrayListExtra("groups");
                    Log.d("groups", "onReceive");
                }
            }
        };
        LocalBroadcastManager.getInstance(ScrollingActivity.this).registerReceiver(receiver, filter);
    }

    // функция для записи выбранного id группы в преференсы
    private void setGroupIdToPreferences(int id) {
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("app", 0);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt("groupId", id);
        prefsEditor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        List<Group> all_groups = dbApi.getGroups();
        List<String> group_names = new ArrayList<String>();
        for (Group cur_group: all_groups) {
            group_names.add(cur_group.getName());
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScrollingActivity.this,
                android.R.layout.simple_spinner_item, group_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        try{
            JSONArray jsonArray = new JSONArray("" +
                    "[" +
                    "{discipline: \"sc\", status: \"ЛК\", location: \"аrthrth\", startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"sc безопасность\", status: \"РК\", location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                    "{discipline: \"Android\", location: \"ауд. 395\",  status: \"ЛК\", startTime: \"Nov 3 20:29:30 2016\"}," +
                    "{discipline: \"Тестирование\", location: \"ауд. 395\", status: \"ЛК\",  startTime: \"Nov 1 20:29:30 2016\"}," +
                    "{discipline: \"Информационная безопасность\", status: \"СМ\",  location: \"ауд. 395\", startTime: \"Nov 2 20:29:30 2016\"}," +
                     "]");

            String[] weekdays = {"СБ", "ВС", "ПН", "ВТ", "СР", "ЧТ", "ПТ"};
            String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            String delimeter = ", ";
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pull_city);
            linearLayout.setPadding(0, 0, 0, 50);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject dateLesson = jsonArray.getJSONObject(i);
                    String nameLesson = dateLesson.getString("discipline");
                    String locationLesson = delimeter + dateLesson.getString("location");
                    String statusLesson = dateLesson.getString("status") + delimeter;
                    DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy", Locale.ENGLISH);
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
                } catch (ParseException e) {}
            }
        } catch (JSONException e) {}



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, GroupChooser.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }



}

