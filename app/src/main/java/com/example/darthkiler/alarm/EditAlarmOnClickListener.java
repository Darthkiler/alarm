package com.example.darthkiler.alarm;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class EditAlarmOnClickListener implements View.OnClickListener {
    int id;
    public EditAlarmOnClickListener(int id)
    {
        this.id=id;
    }
    public void onClick(View v)
    {
        Intent i = new Intent(MainActivity.ma,EditAlarm.class);
        i.putExtra("id", String.valueOf(id));
        MainActivity.ma.startActivity(i);
        MainActivity.ma.finish();
    }
} 
