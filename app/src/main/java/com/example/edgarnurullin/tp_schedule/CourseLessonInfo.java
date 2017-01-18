package com.example.edgarnurullin.tp_schedule;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.google.android.gms.analytics.HitBuilders;

public class CourseLessonInfo extends AppCompatActivity {

    int BH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        View test = this.
//
//                findViewById(R.id.toolbar);
        int iddd = R.id.toolbar;
        setContentView(R.layout.activity_course_lesson_info);



        BH = 0;

    }


        @Override
    protected void onResume() {
        super.onResume();

        if (BH == 0) {
            Lesson lesson = (Lesson) getIntent().getParcelableExtra(Lesson.class.getCanonicalName());

            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.course_lesson_info);

            TextView viewTitle = new TextView(this);
            viewTitle.setTextSize(14);
            viewTitle.setText("Title: " + lesson.getTitle());

            TextView viewTutor = new TextView(this);
            viewTutor.setPadding(0, 40, 0, 0);
            viewTutor.setTextSize(14);
            viewTutor.setText("Tutors: " + lesson.getTutors());

            TextView viewDetailedTitle = new TextView(this);
            viewDetailedTitle.setPadding(0, 80, 0, 0);
            viewDetailedTitle.setTextSize(14);
            viewDetailedTitle.setText("DetailedTitle: " + lesson.getDetailedTitle());

            TextView viewDetailedDescription = new TextView(this);
            viewDetailedDescription.setPadding(0, 40, 0, 0);
            viewDetailedDescription.setTextSize(14);
            viewDetailedDescription.setText("DetailedDescription: " + lesson.getDetailedDescription());

            TextView viewDisciplineBlog = new TextView(this);
            viewDisciplineBlog.setPadding(0, 40, 0, 0);
            viewDisciplineBlog.setTextSize(14);
            viewDisciplineBlog.setText("DisciplineBlog: " + lesson.getDisciplineBlog());

            TextView viewDisciplineLink = new TextView(this);
            viewDisciplineLink.setPadding(0, 40, 0, 0);
            viewDisciplineLink.setTextSize(14);
            viewDisciplineLink.setText("DisciplineLink: " + lesson.getDisciplineLink());

            TextView viewGroupName = new TextView(this);
            viewGroupName.setPadding(0, 40, 0, 0);
            viewGroupName.setTextSize(14);
            viewGroupName.setText("GroupName: " + lesson.getGroupName());


            TextView viewDate = new TextView(this);
            viewDate.setPadding(0, 40, 0, 0);
            viewDate.setTextSize(14);
            viewDate.setText("Date: " + lesson.getDate());

            TextView viewPlace = new TextView(this);
            viewPlace.setPadding(0, 40, 0, 0);
            viewPlace.setTextSize(14);
            viewPlace.setText("Date: " + lesson.getPlace());


            rootLayout.addView(viewTitle);
            rootLayout.addView(viewTutor);
            rootLayout.addView(viewDate);
            rootLayout.addView(viewDetailedTitle);
            rootLayout.addView(viewDetailedDescription);
            rootLayout.addView(viewDisciplineBlog);
            rootLayout.addView(viewDisciplineLink);
            rootLayout.addView(viewGroupName);
            rootLayout.addView(viewPlace);

            BH = 1;
        }


        }

    void setColorScheme(int id){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch (id) {
            case 1:
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
                //ScrollingActivity.this.setTheme(R.style.BlackTheme);
                return;
            case 2:
                toolbar.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                toolbar.setTitleTextColor(getResources().getColor(R.color.darkBlue));
                //ScrollingActivity.this.setTheme(R.style.BlueTheme);
                return;
            case 3:
                toolbar.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                toolbar.setTitleTextColor(getResources().getColor(R.color.darkGreen));
                // ScrollingActivity.this.setTheme(R.style.GreenTheme);
                return;
        }
    }


}
