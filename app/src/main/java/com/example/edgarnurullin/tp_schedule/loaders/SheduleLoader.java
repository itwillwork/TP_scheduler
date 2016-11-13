package com.example.edgarnurullin.tp_schedule.loaders;

import android.content.Context;

import com.example.edgarnurullin.tp_schedule.fetch.ApiFactory;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;
import com.example.edgarnurullin.tp_schedule.fetch.response.ScheduleResponse;
import com.example.edgarnurullin.tp_schedule.fetch.response.RequestResult;
import com.example.edgarnurullin.tp_schedule.fetch.response.Response;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;




public class SheduleLoader extends BaseLoader {

    private final String mGps;

    public SheduleLoader(Context context, String gps) {
        super(context);
        mGps = gps;
    }

    @Override
    protected Response apiCall() throws IOException {
        ShedulerService service = ApiFactory.getAirportsService();
        Call<Map<String, Object>> call = service.airports(mGps);
        //тело запроса
        Map<String, Object> airports = call.execute().body();
        return new ScheduleResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(airports);
    }
}
