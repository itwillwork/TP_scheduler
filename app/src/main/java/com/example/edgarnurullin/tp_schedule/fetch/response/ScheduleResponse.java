package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleResponse extends Response {
    @Override
    public void save(Context context) {
        Map<String, List> groupsWithLessons = getTypedAnswer();
        if (groupsWithLessons != null) {
            List<String> groupNames = new ArrayList(groupsWithLessons.keySet());

            for (int idx = 0; idx < groupNames.size(); idx++) {
                List<Map<String, Object>> groupSchedule = groupsWithLessons.get(groupNames.get(idx));
                //предмет отдельно
                //Log.d(groupNames.get(idx), groupSchedule.toString());
                LessonsTable.save(context, idx, groupSchedule);
            }

            GroupsTable.save(context, groupNames);

        }
    }
}
