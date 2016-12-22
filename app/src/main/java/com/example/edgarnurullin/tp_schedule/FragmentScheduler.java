package com.example.edgarnurullin.tp_schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgarnurullin.tp_schedule.content.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class FragmentScheduler extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private JSONObject dateLesson;
    private int index_of_lesson;
    private ArrayList<Lesson> fragment_lessons;

    public FragmentScheduler() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_fragment_scheduler, container, true);



        Bundle args = getArguments();
        ArrayList<Lesson> lessons = (ArrayList<Lesson>) args.getSerializable("Lesson");

        final LinearLayout rootView = new LinearLayout(getActivity());
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.removeAllViewsInLayout();

        fragment_lessons = lessons;

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
                //rootLayout.removeAllViewsInLayout();
                //rootLayout.setPadding(0, 0, 0, 50);

                for (int i = 0; i < cur_scheduler.length()-1; i++) {
                    try {

                        dateLesson = cur_scheduler.getJSONObject(i);
                        index_of_lesson = i;
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


                        View myview = inflater.inflate(R.layout.lesson_layout, rootView,
                                false);

                        TextView nameLessonNode = (TextView) myview.findViewById(R.id.nameLessonNode);
                        nameLessonNode.setText(statusLesson + nameLesson);

                        TextView weekdayLessonNode = (TextView) myview.findViewById(R.id.weekdayLessonNode);
                        weekdayLessonNode.setText(weekdayLesson);

                        TextView dateLessonNode = (TextView) myview.findViewById(R.id.dateLessonNode);
                        dateLessonNode.setText(dayLesson + monthLesson + locationLesson);

                        myview.setId(i);

                        myview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CourseLessonInfo.class);
                                index_of_lesson = v.getId();
                                intent.putExtra(Lesson.class.getCanonicalName(), fragment_lessons.get(index_of_lesson));
                                startActivity(intent);

                            }
                        });

                        rootView.addView(myview);

                    } catch (ParseException e) {
                    }
                }
            } catch (JSONException e) {}
        }

        return rootView;
    }


}
