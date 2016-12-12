package com.example.edgarnurullin.tp_schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;

public class CourseLessonInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_lesson_info);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast toast = Toast.makeText(this, "BLOODY HELL!", Toast.LENGTH_SHORT);
        toast.show();


    }

}
