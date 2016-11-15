package com.example.edgarnurullin.tp_schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ScrollingActivity extends AppCompatActivity {

    public Item scheduleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //открытие нового активити с выбором  группы
                Intent intent = new Intent(view.getContext(), GroupChooser.class);
                startActivity(intent);
            }
        });

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

            TextView num_group = (TextView) findViewById(R.id.num_of_group);
            num_group.setText("Группа ?");

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
    protected void onResume() {
        super.onResume();

        TextView num_group = (TextView) findViewById(R.id.num_of_group);
        String group = scheduleItem.getGroup();
        num_group.setText("Группа " + group);
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
}

