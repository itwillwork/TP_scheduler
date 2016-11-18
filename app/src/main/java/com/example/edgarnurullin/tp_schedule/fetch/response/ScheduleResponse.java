package com.example.edgarnurullin.tp_schedule.fetch.response;

import android.content.Context;
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

            //чистим базу
            LessonsTable.clear(context);
            GroupsTable.clear(context);

            //напонляем базу
            List<String> groupNames = new ArrayList();
            groupNames.add("Все группы");
            groupNames.addAll(groupsWithLessons.keySet());

            for (int idx = 1; idx < groupNames.size(); idx++) {
                List<Map> groupSchedule = groupsWithLessons.get(groupNames.get(idx));
                LessonsTable.save(context, idx, groupSchedule);
            }
            GroupsTable.save(context, groupNames);

        }
    }
}
