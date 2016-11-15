package com.example.edgarnurullin.tp_schedule;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Polina on 14.11.2016.
 */

public class ScheduleItemService extends IntentService {

    public Item scheduleItem;

    public ScheduleItemService() {
       super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        scheduleItem.group = scheduleItem.getGroup();
    }

}

