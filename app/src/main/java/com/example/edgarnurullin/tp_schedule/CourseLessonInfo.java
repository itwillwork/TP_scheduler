package com.example.edgarnurullin.tp_schedule;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
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
                TableLayout tableLayout = (TableLayout) findViewById(R.id.course_lesson_info_table);


            TextView viewTitle = (TextView) rootLayout.findViewById(R.id.viewTitle);
            viewTitle.setText("Les" + lesson.getTitle());
//
            TextView viewTutor =  (TextView) rootLayout.findViewById(R.id.viewTutor);
            viewTutor.setText(lesson.getTutors());

//            TextView viewDetailedTitle = (TextView) rootLayout.findViewById(R.id.viewDetailedTitle);
//            viewDetailedTitle.setText("DetailedTitle: " + lesson.getDetailedTitle());

            TextView viewDetailedDescription = (TextView) rootLayout.findViewById(R.id.viewDetailedDescription);
            viewDetailedDescription.setText(lesson.getDetailedDescription());

//            TextView viewDisciplineBlog = (TextView) rootLayout.findViewById(R.id.viewDisciplineBlog);
//             viewDisciplineBlog.setText("DisciplineBlog: " + lesson.getDisciplineBlog());
//
            TextView viewDisciplineLink = (TextView) rootLayout.findViewById(R.id.viewDisciplineLink);
            viewDisciplineLink.setText(lesson.getDisciplineLink());
//
//            TextView viewGroupName = (TextView) rootLayout.findViewById(R.id.viewGroupName);
//            viewGroupName.setText("GroupName: " + lesson.getGroupName());


            TextView viewDate = (TextView) rootLayout.findViewById(R.id.viewDate);
             viewDate.setText(lesson.getDate());

            TextView viewPlace =(TextView) rootLayout.findViewById(R.id.viewPlace);
            viewPlace.setText(lesson.getPlace());



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
