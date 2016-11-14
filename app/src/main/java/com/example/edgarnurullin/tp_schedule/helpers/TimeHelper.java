package com.example.edgarnurullin.tp_schedule.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeHelper {
    //ALL FORMATED IN YYYY/MM/DD
    private static TimeHelper instance;
    private String date;
    private int nowYear;
    private int nowMonth;
    private int nowDay;
    private TimeHelper() {
        date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        String [] partsNowDate = date.split("/");
        nowYear = Integer.parseInt(partsNowDate[0]);
        nowMonth = Integer.parseInt(partsNowDate[1]);
        nowDay = Integer.parseInt(partsNowDate[2]);
    }
    public String getDate() {
        return nowYear + "/" + nowMonth + "/" + nowDay;
    }
    public Boolean isFutureDate(String date) {
        Boolean result = false;
        String [] partsLessonDate = date.split("/");
        int lessonYear = Integer.parseInt(partsLessonDate[0]);
        int lessonMonth = Integer.parseInt(partsLessonDate[1]);
        int lessonDay = Integer.parseInt(partsLessonDate[2]);
        if (lessonYear > nowYear) {
            result = true;
        } else if (lessonYear == nowYear) {
            if (lessonMonth > nowMonth) {
                result = true;
            } else if (lessonMonth == nowMonth) {
                if (lessonDay >= nowDay) {
                    result = true;
                }
            }
        }
        return result;
    }

    public String reversDate(String sourceDate) {
        // 08/16/2016 to 2016/08/16
        String [] partsDate = sourceDate.split("/");
        String year = partsDate[2];
        String month = partsDate[0];
        String day = partsDate[1];
        return year + "/" + month + "/" + day;
    }
    public static TimeHelper getInstance() {
        if (instance == null) {
            instance = new TimeHelper();
        }
        return instance;
    }
}
