package com.example.darthkiler.alarm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Stopwatch extends AppCompatActivity {
    static TextView time;
    Button start,stop,pause;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        time = ((TextView) findViewById(R.id.textView));
        start=((Button) findViewById(R.id.button6));
        stop=((Button) findViewById(R.id.button4));
        stop.setEnabled(false);

        pause=((Button) findViewById(R.id.button5));
        pause.setEnabled(false);
        time.setText("00:00:00");
        handler = new Handler();
        handler.removeCallbacks(runnable);
    }

    public void start(View view) {

        StartTime = SystemClock.uptimeMillis();

        handler.postDelayed(runnable, 0);
        start.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(false);



    }

    public void pause(View view) {
        TimeBuff += MillisecondTime;
        start.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(true);
        handler.removeCallbacks(runnable);
    }

    public void stop(View view) {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;
        handler.removeCallbacks(runnable);
        time.setText("00:00:00");
        stop.setEnabled(false);
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
    @Override
    public void onPause()
    {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}