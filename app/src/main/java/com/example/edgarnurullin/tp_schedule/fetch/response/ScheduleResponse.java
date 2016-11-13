package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleResponse extends Response {
    @Override
    public void save(Context context) {
        Map<String, Object> groupsWithLessons = getTypedAnswer();
        if (groupsWithLessons != null) {
            List<String> groupNames = new ArrayList(groupsWithLessons.keySet());

            for (String groupName : groupNames) {
                Object groupSchedule = groupsWithLessons.get(groupName);
                //предмет отдельно
                Log.d(groupName, groupSchedule.toString());
            }

            GroupsTable.save(context, groupNames);
            LessonsTable.save(context, new ArrayList<Lesson>());
        }
    }
}
