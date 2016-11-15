package com.example.edgarnurullin.tp_schedule.loaders;

import android.content.Context;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.fetch.ApiFactory;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;
import com.example.edgarnurullin.tp_schedule.fetch.response.ScheduleResponse;
import com.example.edgarnurullin.tp_schedule.fetch.response.RequestResult;
import com.example.edgarnurullin.tp_schedule.fetch.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class SheduleLoader extends BaseLoader {

    public SheduleLoader(Context context) {
        super(context);
    }

    @Override
    protected Response apiCall() throws IOException {
        ShedulerService service = ApiFactory.getLessonsService();
        Call<Map<String, List<Map>>> call = service.lessons();
        Map<String, List<Map>> schedule = call.execute().body();
        return new ScheduleResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(schedule);
    }
}
