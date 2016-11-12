package com.example.edgarnurullin.tp_schedule.loaders;

import android.content.Context;
import android.database.Cursor;

import com.example.edgarnurullin.tp_schedule.content.Lessons;
import com.example.edgarnurullin.tp_schedule.db.tables.AirportsTable;
import com.example.edgarnurullin.tp_schedule.fetch.ApiFactory;
import com.example.edgarnurullin.tp_schedule.fetch.ShedulerService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SheduleLoader extends BaseLoader {

    private final String mGps;

    public SheduleLoader(Context context, String gps) {
        super(context);
        mGps = gps;
    }

    @Override
    protected Cursor apiCall() throws IOException {
        ShedulerService service = ApiFactory.getAirportsService();
        Call<List<Lessons>> call = service.airports(mGps);
        List<Lessons> airports = call.execute().body();
        AirportsTable.save(getContext(), airports);
        return getContext().getContentResolver().query(AirportsTable.URI,
                null, null, null, null);
    }
}