package com.example.darthkiler.alarm;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    static MainActivity ma;//обьявление статической переменной для контекста
    Intent i=null;
    static PendingIntent  pendingIntent;
    static AlarmManager alarmManager;
    @Override
    protected void onResume()
    {
        super.onResume();
        if(alarmManager!=null)
            alarmManager.cancel(pendingIntent);
        Alarms.startNewAlarm();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        i=new Intent(this,AlarmReceiver.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //if(AlarmService.as==null)
        //startService(i);
        pendingIntent =PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
        //Calendar calendar = Calendar.getInstance();

        //calendar.setTime(Alarms.minimum().d);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+5000, pendingIntent);
        //if(AlarmService.as!=null)
            //AlarmService.as.reStartAlram();
        //присваивание переменной текущего активити
        ma=this;
        for(int i=0;i<Alarms.Alarms.size();i++) {//обход списка всех будильников
            LinearLayout l=new LinearLayout(this);//создвание новой области для будильника
            TextView t=new TextView(this);//создание текстового поля бля хранения информации о будильнике
            t.setOnClickListener(new EditAlarmOnClickListener(Alarms.Alarms.get(i).id));
            Button b=new Button(this);//созание новой кнопки для удаления
            b.setText("X");//установка текста на кнопку
            b.setOnClickListener(new DeleteRowOnClickListener(Alarms.Alarms.get(i).id));//прикрепление к кнопки лиснера для удаления из базы данных
            l.addView(b);//добавление кнопки удаления
            CheckBox c=new CheckBox(this);
            if(Alarms.Alarms.get(i).enabled)
            c.setChecked(true);
            else c.setChecked(false);
            l.addView(c);//добавление кнопки включения/выключения
            c.setOnClickListener(new ChangeStateOnClickListener(Alarms.Alarms.get(i).id));
            t.setText(Alarms.Alarms.get(i).write());//добавление текста с информацией о будильнике в текстовую область
            l.setOrientation(LinearLayout.HORIZONTAL);//установка выравнивания элементов
            l.addView(t);//добавление текстового поля в область бля будильника

            ((LinearLayout) findViewById(R.id.q1)).addView(l);//добавление области на экран
        }


    }
    public void goToAddAlaram(View View)//переход на добавление нового будльника
    {

        startActivity(new Intent(this,AddAlarm.class));//открытие нового активити для добавления
        this.finish();//закрытие текущего активити

    }

}
