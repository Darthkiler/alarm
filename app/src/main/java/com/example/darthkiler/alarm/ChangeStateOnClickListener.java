package com.example.darthkiler.alarm;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class ChangeStateOnClickListener implements View.OnClickListener{
    int id;

    public ChangeStateOnClickListener(int id)
    {
        this.id=id;

    }
    public void onClick(View v)
    {
        Alarms.changeState(id,((CheckBox)v).isChecked());//изменение вкл/выкл
        //MainActivity.ma.stopService(MainActivity.i);
        //MainActivity.ma.startService(MainActivity.i);
    }
} 
