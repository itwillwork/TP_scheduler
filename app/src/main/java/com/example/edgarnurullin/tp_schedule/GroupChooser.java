package com.example.edgarnurullin.tp_schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class GroupChooser extends AppCompatActivity {


    private Item scheduleItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chooser);
       // LinearLayout linearLayout = (LinearLayout)findViewById(R.id.pull_groups);

        String[] groups = {"АПО-11", "АПО-12", "АПО-13", "АПО-21", "АПО-22", "АПО-31", "АПО-41"};

        ViewGroup activity_group_chooser = (ViewGroup) findViewById(R.id.activity_group_chooser);

        for (final String cur_group : groups) {
            Button button = new Button(this);
            button.setText(cur_group.toString());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scheduleItem.group = cur_group;
                    Intent service_intent = new Intent(GroupChooser.this, ScheduleItemService.class);
                    startService(service_intent);
                    finish();
                }
            });
            activity_group_chooser.addView(button);
        }

    }

}
