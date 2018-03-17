package com.example.darthkiler.alarm;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChangeStateOnClickListener implements View.OnClickListener{
    int id;

    public ChangeStateOnClickListener(int id)
    {
        this.id=id;

    }
    public void onClick(View v)
    {
        Alarms.changeState(id,((CheckBox)v).isChecked());//изменение вкл/выкл
        if(MainActivity.alarmManager!=null)
            MainActivity.alarmManager.cancel(MainActivity.pendingIntent);
        Alarms.startNewAlarm();
    }
} 
