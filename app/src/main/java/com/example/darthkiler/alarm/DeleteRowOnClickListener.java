package com.example.darthkiler.alarm;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DeleteRowOnClickListener implements View.OnClickListener {
    int id;
    public DeleteRowOnClickListener(int id)
    {
        this.id=id;
    }
    public void onClick(View v)
    {
        ((LinearLayout)v.getParent()).setVisibility(View.GONE);
        Alarms.deleteAlarm(id);
        if(MainActivity.alarmManager!=null)
            MainActivity.alarmManager.cancel(MainActivity.pendingIntent);
        Alarms.startNewAlarm();
    }
} 
