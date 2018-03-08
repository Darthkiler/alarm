package com.example.darthkiler.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class Alarm extends AppCompatActivity {

    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        time=0;
        time=Long.valueOf(getIntent().getStringExtra("time"));
        new Thread(new Tr(this)).start();
        new Thread(new mp3(this)).start();

        Button b=new Button(this);
        b.setOnClickListener(new Lis(this));
        b.setText("X");
        TextView t=new TextView(this);
        t.setText(new Date(time).getHours()+" "+new Date(time).getMinutes());
        ((LinearLayout) findViewById(R.id.q2)).addView(t);
        ((LinearLayout) findViewById(R.id.q2)).addView(b);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    class Tr implements Runnable
    {
        Alarm a;
        Tr(Alarm a)
        {
            this.a=a;
        }
        @Override
        public void run() {
            synchronized (this) {
                try {
                    wait(50000);
                    a.finish();

                } catch (Exception e) {

                }
            }
        }

    }
    class mp3 implements Runnable
    {
        Alarm a;
        mp3(Alarm a)
        {
            this.a=a;
        }
        @Override
        public void run() {

                    final MediaPlayer mp = MediaPlayer.create(a, R.raw.tix);
                    while(!a.isFinishing())
                    mp.start();


        }

    }
    class Lis implements  View.OnClickListener {
        Alarm a;
        Lis(Alarm a)
        {
            this.a=a;
        }
        @Override
        public void onClick(View view) {
            a.finish();
        }
    }
}
