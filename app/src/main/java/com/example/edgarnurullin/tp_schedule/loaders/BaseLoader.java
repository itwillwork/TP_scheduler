package com.example.edgarnurullin.tp_schedule.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import java.io.IOException;

/**
 * @author Artur Vasilov
 */
public abstract class BaseLoader extends AsyncTaskLoader<Cursor> {

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return apiCall();
        } catch (IOException e) {
            return null;
        }
    }

    protected abstract Cursor apiCall() throws IOException;
}


