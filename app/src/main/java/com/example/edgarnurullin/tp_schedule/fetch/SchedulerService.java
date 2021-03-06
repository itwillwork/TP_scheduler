package com.example.edgarnurullin.tp_schedule.fetch;

import java.util.List;
import java.util.Map;

import com.example.edgarnurullin.tp_schedule.content.Lesson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SchedulerService {
    @GET("api/")
    Call<Map<String, List<Map>>> lessons();
}
