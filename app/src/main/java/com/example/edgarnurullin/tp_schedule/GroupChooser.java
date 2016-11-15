package com.example.edgarnurullin.tp_schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.edgarnurullin.tp_schedule.content.Group;

import java.util.List;

public class GroupChooser extends AppCompatActivity {

    private com.example.edgarnurullin.tp_schedule.db.dbApi dbApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chooser);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.pull_groups);

        List<Group> all_groups = dbApi.getGroups();

        for (Group current_group: all_groups) {
            Button groupNode = new Button(this);
            groupNode.setText(current_group.getName());
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
