package com.example.darthkiler.alarm;

import android.view.View;
import android.widget.LinearLayout;

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
        MainActivity.ma.stopService(MainActivity.i);
        MainActivity.ma.startService(MainActivity.i);
    }
} 
