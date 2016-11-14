package com.example.edgarnurullin.tp_schedule;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.fetch.response.Response;
import com.example.edgarnurullin.tp_schedule.helpers.TimeHelper;
import com.example.edgarnurullin.tp_schedule.loaders.SheduleLoader;

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


public class ScrollingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Response> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //указание на создание лоудера
        getLoaderManager().initLoader(R.id.airports_loader, Bundle.EMPTY, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO перенести в хелпер базы
                // api запроса за группой
                List<Group> result = new ArrayList<Group>();
                Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                result = GroupsTable.listFromCursor(cursor);
//                Integer idxGroup = result.get(3).getId();
//                Log.d("group ", result.get(3).getName());

                // TODO перенести в хелпер базы
                // api запроса за занятиями ВСЕМИ
//                List<Lesson> result = new ArrayList<Lesson>();
//                Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
//                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//                result = LessonsTable.listFromCursor(cursor);
//                Log.d("kek", result.get(1).getTitle());

//                String nowDate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//                String [] partsNowDate = nowDate.split("/");
//                Integer nowYear = Integer.parseInt(partsNowDate[0]);
//                Integer nowMonth = Integer.parseInt(partsNowDate[1]);
//                Integer nowDay = Integer.parseInt(partsNowDate[2]);

                // TODO перенести в хелпер базы
                // api запроса за занятиями ВСЕМИ
                List<Lesson> result2 = new ArrayList<Lesson>();
                Uri uri2 = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
                Cursor cursor2 = getContentResolver().query(uri2, null, " group_id = 2 ", null, " date ASC, time ASC");
                result2 = LessonsTable.listFromCursor(cursor2);
                for (int idx = 0; idx < result2.size(); idx++) {
                    String lessonDate = result2.get(idx).getDate();
                    result2.get(idx).setGroupName(result.get(3).getName());
                    if (TimeHelper.getInstance().isFutureDate(lessonDate)) {
                        Log.d("lessons date", lessonDate + " " + result2.get(idx).getGroupName());
                    }
                }

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

    //создание лоудера
    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case R.id.airports_loader:
                return new SheduleLoader(this, "55.749792,37.6324949");

            default:
                return null;
        }
    }

    //когда лоудер закончил работу
    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        int id = loader.getId();
        if (id == R.id.airports_loader) {
            //List<Lesson> airports = data.getTypedAnswer();

            //do something here
            //Log.d("lol", airports.toString());


        }
        getLoaderManager().destroyLoader(id);
    }

    //когда LoaderManager собрался уничтожать лоадер
    @Override
    public void onLoaderReset(Loader<Response> loader) {
        // Do nothing
    }}

