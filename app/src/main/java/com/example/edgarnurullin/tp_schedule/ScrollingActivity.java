package com.example.edgarnurullin.tp_schedule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class ScrollingActivity extends AppCompatActivity {

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
            }
        });

        try{
            JSONArray jsonArray = new JSONArray("" +
                    "[" +
                    "{discipline: \"Тестирование\", location: \"Ауд. 395\", startTime: \"Thu Sep 28 20:29:30 JST 2016\"}," +
                    "{discipline: \"Информационная безопасность\", location: \"Ауд. 395\", startTime: \"Thu Sep 28 20:29:30 JST 2016\"}," +
                    "{discipline: \"Android\", location: \"Ауд. 395\", startTime: \"Thu Sep 28 20:29:30 JST 2016\"}," +
                    "]");

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pull_city);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject animal = jsonArray.getJSONObject(i);
                String name = animal.getString("discipline");
                String target = animal.getString("startTime");
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                Date result;
                int dayOfWeek = 1;
                try {
                    result = df.parse(target);


                } catch (ParseException e) {}
                

                TextView lesson = new TextView(this);
                lesson.setTextSize(24);
                String lol = name + dayOfWeek;
                lesson.setText(lol);
                linearLayout.addView(lesson);


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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
