package com.example.edgarnurullin.tp_schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GroupChooser extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chooser);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.pull_groups);

        String[] groups = {"АПО-11", "АПО-12", "АПО-13", "АПО-21", "АПО-22", "АПО-31", "АПО-41"};

        for (int i = 0; i < groups.length; i++) {
            Button groupNode = new Button(this);
            groupNode.setText(groups[i]);
            groupNode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("лол, ты выбрал другую группу");
                    finish();
                }
            });
            linearLayout.addView(groupNode);
        }
    }

}
