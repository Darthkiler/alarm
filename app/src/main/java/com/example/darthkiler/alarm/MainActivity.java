package com.example.darthkiler.alarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static MainActivity ma;//обьявление статической переменной для контекста
    static Intent i=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        i=new Intent(this,AlarmService.class);
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
    public void goToAlaram(View View)//переход на добавление нового будльника
    {
        Intent dialogIntent = new Intent(this, Alarm.class);
        synchronized (this) {
            try {
                wait(5000);
            } catch (Exception e) {

            }
        }
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialogIntent.addCategory(Intent.ACTION_SCREEN_ON);
        startActivity(dialogIntent);
        this.finish();//закрытие текущего активити
    }
}
