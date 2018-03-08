package com.example.darthkiler.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

public class EditAlarm extends AppCompatActivity {
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        id=(getIntent().getStringExtra("id"));
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        Alarms curr=Alarms.getAlarmById(id);
        ArrayList<String> objects=new ArrayList<>();
        for(int i=0;i<24;i++)
            objects.add(String.valueOf(i));
        ArrayAdapter adapter = new ArrayAdapter(
                this,android.R.layout.simple_list_item_1 ,objects.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //установка информации на спинер
        spinner.setSelection(curr.d.getHours());
        spinner = (Spinner)findViewById(R.id.spinner2);
        for(int i=24;i<60;i++)
            objects.add(String.valueOf(i));
        adapter = new ArrayAdapter(
                this,android.R.layout.simple_list_item_1 ,objects.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //установка информации на спинер
        spinner.setSelection(curr.d.getMinutes());


    }

    public void editAlarm(View View)//добавление нового будильника
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
        Alarms.editAlarm(newAlarm,id);//перезапись будильника
        Alarms.refreshAlarms();//обновление будильников

        startActivity(new Intent(this,MainActivity.class));//создание нового главного активити
        this.finish();//завершение текущего активити



    }
    public void backToMain(View View)
    {
        startActivity(new Intent(this,MainActivity.class));
        this.finish();//закрытие текущего активити
    }
}
