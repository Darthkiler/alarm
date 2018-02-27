package com.example.darthkiler.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class AddAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> objects=new ArrayList<>();
        for(int i=0;i<24;i++)
            objects.add(String.valueOf(i));
        ArrayAdapter adapter = new ArrayAdapter(
                this,android.R.layout.simple_list_item_1 ,objects.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //установка информации на спинер

        spinner = (Spinner)findViewById(R.id.spinner2);
        for(int i=24;i<60;i++)
            objects.add(String.valueOf(i));
        adapter = new ArrayAdapter(
                this,android.R.layout.simple_list_item_1 ,objects.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //установка информации на спинер

    }
    public void addNewAlarm(View View)//добавление нового будильника
    {
        Date curr=new Date();//новая переменная типа даты
        curr.setSeconds(0);//секунды=0
        curr.setMinutes(Integer.valueOf(((Spinner)findViewById(R.id.spinner2)).getSelectedItem().toString()));//минуты=значение из спинера
        curr.setHours(Integer.valueOf(((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString()));//часы=значение из спинера
        boolean days[]=new boolean[7];//обьявление массива для дней
        days[0]=((CheckBox)(findViewById(R.id.checkBox7))).isChecked();//отмеченность понедельника
        days[1]=((CheckBox)(findViewById(R.id.checkBox6))).isChecked();
        days[2]=((CheckBox)(findViewById(R.id.checkBox5))).isChecked();
        days[3]=((CheckBox)(findViewById(R.id.checkBox4))).isChecked();
        days[4]=((CheckBox)(findViewById(R.id.checkBox3))).isChecked();
        days[5]=((CheckBox)(findViewById(R.id.checkBox2))).isChecked();
        days[6]=((CheckBox)(findViewById(R.id.checkBox))).isChecked();//отмеченность воскресенья
        Alarms newAlarm=new Alarms(curr,days);//создение нового обьекта будильник по текущей дате и выбранным дням
        Alarms.saveAlarm(newAlarm);//сохранение в базе данных нового будильника
        Alarms.refreshAlarms();//обновление будильников
        Alarms.startNewAlarm();
        startActivity(new Intent(this,MainActivity.class));//создание нового главного активити
        this.finish();//завершение текущего активити



    }
}
