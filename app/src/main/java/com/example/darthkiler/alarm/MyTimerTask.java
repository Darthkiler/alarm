package com.example.darthkiler.alarm;

import android.media.MediaPlayer;

import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    MyTimer m=null;
    MyTimerTask(MyTimer m)
    {
        this.m=m;
    }
    @Override
    public void run() {
        final MediaPlayer mp = MediaPlayer.create(MainActivity.ma.getApplicationContext(), R.raw.tix);
        mp.start();
        Alarms.current.cancel();
        Alarms.startNewAlarm();

    }
}
