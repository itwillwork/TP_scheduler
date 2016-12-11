package com.example.edgarnurullin.tp_schedule;

import android.content.Context;
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



    public FragmentScheduler() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        ArrayList<Lesson> myClass = (ArrayList<Lesson>) args.getSerializable("Lesson");

        final View rootView = inflater.inflate(R.layout.fragment_fragment_scheduler, container, false);
        final TextView test = (TextView) rootView.findViewById(R.id.city_pick_autocompletetextview);
        test.setText();
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.fragment_layout);
        Button btn = (Button) rootView.findViewById(R.id.btn_do_it);
        btn.setText("TEST");


//        Context context = getActivity().getApplicationContext();
//        LinearLayout layout = new LinearLayout(context);
//        layout.setBackgroundColor(Color.BLUE);
//        TextView text = new TextView(context);
//        text.setText("Это область фрагмента");
//        layout.addView(text);

//        LinearLayout lessonNodeH = new LinearLayout(this);
//        lessonNodeH.setOrientation(LinearLayout.HORIZONTAL);
//        TextView nameLessonNode = new TextView(context);
//
//        nameLessonNode.setText("statusLesson" + "nameLesson");
//        lessonNodeH.setPadding(0, 30, 0, 30);
//        lessonNodeH.addView(nameLessonNode);
//
//        linearLayout.addView(lessonNodeH);


        return rootView;

    }

}
