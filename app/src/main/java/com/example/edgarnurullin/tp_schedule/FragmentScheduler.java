package com.example.edgarnurullin.tp_schedule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edgarnurullin.tp_schedule.content.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentScheduler.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentScheduler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentScheduler extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentScheduler() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentScheduler.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentScheduler newInstance(String param1, String param2) {
        FragmentScheduler fragment = new FragmentScheduler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        TextView text = new TextView(context);
        text.setText("Это область фрагмента");
        layout.addView(text);


//        // Inflate the layout for this fragment
//        String[] weekdays = {"СБ", "ВС", "ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"};
//        String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
//                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
//        String delimeter = ", ";
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.removeAllViewsInLayout();
//        linearLayout.setPadding(0, 0, 0, 50);
//
//        JSONArray cur_scheduler = new JSONArray();





//        for (int i = 0; i < cur_scheduler.length()-1; i++) {
//            try {
//                JSONObject dateLesson = cur_scheduler.getJSONObject(i);
//                String nameLesson = dateLesson.getString("discipline");
//                String locationLesson = delimeter + dateLesson.getString("location");
//                String statusLesson = dateLesson.getString("status") + delimeter;
//                DateFormat format = new SimpleDateFormat("yyyy/MM/dd kk:mm", Locale
//                        .ENGLISH);
//                Date date = format.parse(dateLesson.getString("startTime"));
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                String weekdayLesson = weekdays[calendar.get(Calendar.DAY_OF_WEEK)];
//                Integer dayLesson = calendar.get(Calendar.DAY_OF_MONTH);
//                String monthLesson = " " + months[calendar.get(Calendar.MONTH)];
//
//                LinearLayout lessonNodeH = new LinearLayout(this);
//                LinearLayout lessonNodeV = new LinearLayout(this);
//                LinearLayout lessonNodeH2 = new LinearLayout(this);
//                lessonNodeV.setOrientation(LinearLayout.VERTICAL);
//                lessonNodeH.setOrientation(LinearLayout.HORIZONTAL);
//                lessonNodeH2.setOrientation(LinearLayout.HORIZONTAL);
//
//                TextView nameLessonNode = new TextView(this);
//                nameLessonNode.setTextSize(14);
//                nameLessonNode.setText(statusLesson + nameLesson);
//
//                TextView weekdayLessonNode = new TextView(this);
//                weekdayLessonNode.setTextSize(28);
//                weekdayLessonNode.setText(weekdayLesson);
//
//                TextView dateLessonNode = new TextView(this);
//                dateLessonNode.setTextSize(14);
//                dateLessonNode.setText(dayLesson + monthLesson + locationLesson);
//
//                weekdayLessonNode.setPadding(10, 0, 0, 0);
//                lessonNodeV.setPadding(30, 0, 10, 0);
//                lessonNodeH.setPadding(0, 30, 0, 30);
//
//                lessonNodeV.addView(nameLessonNode);
//                lessonNodeV.addView(dateLessonNode);
//
//                lessonNodeH.addView(weekdayLessonNode);
//                lessonNodeH.addView(lessonNodeV);
//                linearLayout.addView(lessonNodeH);
//            } catch (ParseException e) {
//            }
//        }



        return inflater.inflate(R.layout.fragment_fragment_scheduler, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
