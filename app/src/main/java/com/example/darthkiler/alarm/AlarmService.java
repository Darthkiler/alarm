package com.example.darthkiler.alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends IntentService {
    public AlarmService() {
        super("AlarmService");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
        Toast.makeText(this, Alarms.minimum().d.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "нема будильников", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /*while(Alarms.minimum()!=null)
        {
            long endTime=0;
            try {
                endTime = Alarms.minimum().d.getTime();
            }
            catch (Exception e){}

            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());

                        Intent dialogIntent = new Intent(this, Alarm.class);
                        dialogIntent.putExtra("time",String.valueOf(endTime));
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        dialogIntent.addCategory(Intent.ACTION_SCREEN_ON);
                        startActivity(dialogIntent);
                        stopSelf();
                    } catch (Exception e) {

                    } catch (Throwable throwable) {

                    }
                }
            }
        }*/

    }


}
