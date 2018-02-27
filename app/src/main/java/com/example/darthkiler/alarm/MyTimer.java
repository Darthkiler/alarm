package com.example.darthkiler.alarm;

import android.media.MediaPlayer;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer extends Timer{

    public void run() {
        try {

                Date d=Alarms.minimum().d;//минимальная дата в будильнике
                this.schedule(new MyTimerTask(this) ,d);//запуск таймера до указанной даты

        } catch (Exception e) {

        }
    }

}