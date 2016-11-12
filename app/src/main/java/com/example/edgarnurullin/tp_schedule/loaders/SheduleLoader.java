package com.example.edgarnurullin.tp_schedule.loaders;

import android.content.Context;
import android.database.Cursor;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.AirportsTable;
import com.example.edgarnurullin.tp_schedule.fetch.ApiFactory;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;
import com.example.edgarnurullin.tp_schedule.fetch.response.AirportsResponse;
import com.example.edgarnurullin.tp_schedule.fetch.response.RequestResult;
import com.example.edgarnurullin.tp_schedule.fetch.response.Response;

import java.io.IOException;
import java.util.List;

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
        Call<List<Lesson>> call = service.airports(mGps);
        //тело запроса
        List<Lesson> airports = call.execute().body();
        return new AirportsResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(airports);
    }
}
