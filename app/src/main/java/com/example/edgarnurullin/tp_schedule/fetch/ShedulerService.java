package com.example.edgarnurullin.tp_schedule.fetch;

import java.util.List;
import java.util.Map;

import com.example.edgarnurullin.tp_schedule.content.Lesson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ShedulerService {
    @GET("api/")
    //запрос
    Call<Map<String, Object>> airports(@Query("coords") String gps);
}
