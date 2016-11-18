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

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;


public class dbApi {
    ContentResolver mContentResolver;
    public dbApi(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }
    public List<Group> getGroups () {
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/GroupsTable");
        Cursor cursor = mContentResolver.query(uri, null, null, null, null);
        return GroupsTable.listFromCursor(cursor);
    }
    public List<Lesson> getLessons (Group selectedGroup) {
        //запрос за занятиями конкретной группы
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = mContentResolver.query(uri, null, " group_id = " + selectedGroup.getId(), null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);

        //проставляем название группы
        for (int idx = 0; idx < result.size(); idx++) {
            result.get(idx).setGroupName(selectedGroup.getName());
        }

        return passedActualLessons(result);
    }
    private List<Lesson> passedActualLessons (List<Lesson> listLessons) {
        List<Lesson> result = new ArrayList<>();
        for (int idx = 0; idx < listLessons.size(); idx++) {
            String lessonDate = listLessons.get(idx).getDate();
            if (TimeHelper.getInstance().isFutureDate(lessonDate)) {
                result.add(listLessons.get(idx));
            }
        }
        return result;
    }
    public List<Lesson> getLessons () {
        //занятия всех групп
        Uri uri = Uri.parse("content://com.example.edgarnurullin.tp_schedule/LessonsTable");
        Cursor cursor2 = mContentResolver.query(uri, null, null, null, " date ASC, time ASC");
        List<Lesson> result = LessonsTable.listFromCursor(cursor2);

        result = passedActualLessons(result);

        //проставляем название группы
        List<Group> groups = getGroups();
        for (int idx = 0; idx < result.size(); idx++) {
            Lesson curLesson = result.get(idx);
            Integer group = curLesson.getGroupId();
            curLesson.setGroupName(groups.get(group).getName());
        }
        return result;
    }
}