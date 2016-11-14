package com.example.edgarnurullin.tp_schedule.db;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.edgarnurullin.tp_schedule.content.Group;
import com.example.edgarnurullin.tp_schedule.content.Lesson;
import com.example.edgarnurullin.tp_schedule.db.tables.GroupsTable;
import com.example.edgarnurullin.tp_schedule.db.tables.LessonsTable;
import com.example.edgarnurullin.tp_schedule.helpers.TimeHelper;

import java.util.ArrayList;
import java.util.List;


public class DBApi {
    ContentResolver mContentResolver;
    public DBApi(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }
    public List<Group> getGroups () {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = mContentResolver.query(uri, null, null, null, null);
        return GroupsTable.listFromCursor(cursor);
    }
    public List<Lesson> getLessons (Group selectedGroup) {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = mContentResolver.query(uri, null, " group_id = " + selectedGroup.getId(), null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);
        for (int idx = 0; idx < result.size(); idx++) {
            result.get(idx).setGroupName(selectedGroup.getName());
        }
        return passedActualLessons(result);
    }
    private List<Lesson> passedActualLessons (List<Lesson> listLessons) {
        for (int idx = 0; idx < listLessons.size(); idx++) {
            String lessonDate = listLessons.get(idx).getDate();
            if (TimeHelper.getInstance().isFutureDate(lessonDate)) {
                listLessons.remove(listLessons.get(idx));
            }
        }
        return listLessons;
    }
    public List<Lesson> getLessons () {

        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = mContentResolver.query(uri, null, null, null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);
        result = passedActualLessons(result);

        List<Group> groups = getGroups();
        for (int idx = 0; idx < result.size(); idx++) {
            Lesson curLesson = result.get(idx);
            Integer group = curLesson.getGroupId();
            curLesson.setGroupName(groups.get(group).getName());
        }
        return result;
    }
}
