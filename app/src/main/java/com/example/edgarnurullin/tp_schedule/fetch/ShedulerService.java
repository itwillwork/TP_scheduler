package com.example.edgarnurullin.tp_schedule.fetch;

import java.util.List;
import com.example.edgarnurullin.tp_schedule.content.Lesson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ShedulerService {
    @GET("/places/coords_to_places_ru.json")
    //запрос
    Call<List<Lesson>> airports(@Query("coords") String gps);
}
