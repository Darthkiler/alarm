package com.example.darthkiler.alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class Alarms{
    public static ArrayList<Alarms> Alarms;//статическая переменная хранящая все будильники
    public static String daysOfWeek[];//массив названия дней недели
    private static mDbHelper mDBHelper;//обект для работы с базой
    private static SQLiteDatabase mDb;//обьект для работы с базой
    static PendingIntent pendingIntent;
    static AlarmManager alarmManager;

    int id;//ид будильника
    Date d;//дана
    boolean[] days=new boolean[7];//дни недели
    boolean enabled=true;//он/офф
    Alarms() {//будильник на все дни с текущем времинем
        d=new Date();
        for(int i=0;i<days.length;i++)
            days[i]=true;
    }

    Alarms(Date d)//все дни с выбранной датой
    {
        this.d=(Date) d.clone();
        for(int i=0;i<days.length;i++)
            days[i]=true;
    }

    public Alarms (Alarms a)//копирование будильника
    {
        this.d=(Date) a.d.clone();
        System.arraycopy(a.days, 0, this.days, 0, days.length);

    }

    Alarms(Date d,boolean[] days)//дата и дни
    {

        this.d=(Date) d.clone();
        System.arraycopy(days, 0, this.days, 0, days.length);
    }

    Alarms(Date d,boolean[] days,int id)//дата дни и ид в базе данных
    {
        this.id=id;
        this.d=(Date) d.clone();
        System.arraycopy(days, 0, this.days, 0, days.length);
    }

    Alarms(Date d,boolean[] days,boolean enabled)//дата дни и включённость
    {
        this.d=(Date) d.clone();
        System.arraycopy(days, 0, this.days, 0, days.length);
        this.enabled=enabled;
    }

    Alarms(Date d,boolean[] days,boolean enabled,int id)//дата дни включённость и ид в базе данных
    {
        this.enabled=enabled;
        this.id=id;
        this.d=(Date) d.clone();
        System.arraycopy(days, 0, this.days, 0, days.length);
    }

    protected boolean isDays()//проверка наличия активных дней в будильнике
    {
        boolean b=false;
        for(int i=0;i<7;i++)
            if(days[i]!=false)
                b=true;
        return b;
    }

    public String write()//создание строки для вывода
    {
        String t="";
        t+=d.getHours();
        t+=":"+d.getMinutes()+"   ";
        for(int i=0;i<days.length;i++)
            if(days[i])
            t+=daysOfWeek[i]+" ";


        return t;

    }

    static
    {
        mDBHelper = new mDbHelper(MainActivity.ma.getApplicationContext());//работа с базой данных
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();//работа с базой данных
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        daysOfWeek=new String[7];//создание и заполнение массива с днями недели
        daysOfWeek[0]="Пн";
        daysOfWeek[1]="Вт";
        daysOfWeek[2]="Ср";
        daysOfWeek[3]="Чт";
        daysOfWeek[4]="Пт";
        daysOfWeek[5]="Сб";
        daysOfWeek[6]="Вс";
        Alarms=new ArrayList();//создание списка будильников
        refreshAlarms();//обновление будильников и базы данных


    }
    public static void saveAlarm(Alarms a)//сохранение будильника в базе данных
    {

        int hour=a.d.getHours();
        int min=a.d.getMinutes();
        int days=0;
        for(int i=0; i<a.days.length; i++) {
            if(a.days[i])
                days+=Math.pow(2, i);
        }

        mDb.execSQL("insert into alarm(hour,min,days,enabled) values("+hour+","+min+","+days+",1);");
    }
    public static Alarms getAlarmById(String id)
    {
        Alarms a=null;
        Cursor cursor;
        cursor = mDb.rawQuery("select hour,min,days,id,enabled from alarm where id="+id, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            boolean[] bits = new boolean[7];
            for (int i = 6; i >= 0; i--) {
                bits[i] = (cursor.getInt(2) & (1 << i)) != 0;
            }
            Date curr=new Date();
            curr.setSeconds(0);
            curr.setMinutes(cursor.getInt(1));
            curr.setHours(cursor.getInt(0));

            a= new Alarms(curr,bits,cursor.getInt(4)==1?true:false,cursor.getInt(3));
            cursor.moveToNext();
        }
        return a;
    }
    static void editAlarm(Alarms a,String oldId)
    {
        int hour=a.d.getHours();
        int min=a.d.getMinutes();
        int days=0;
        for(int i=0; i<a.days.length; i++) {
            if(a.days[i])
                days+=Math.pow(2, i);
        }

        mDb.execSQL("update alarm set hour="+hour+",min="+min+",days="+days+",enabled=1 where id="+oldId+";");

    }
    public static void deleteAlarm(int id)//удаление будильника
    {

        mDb.execSQL("delete from alarm where id="+id+";");
        refreshAlarms();

    }
    public static Alarms minimum() //нахождение минимального времени из всех будильников
    {
        Alarms a=null;
        for(int i=0;i<Alarms.size();i++) {
            if (Alarms.get(i).isDays() && Alarms.get(i).enabled) {
                Alarms b = new Alarms(Alarms.get(i));
                Calendar c = Calendar.getInstance();
                c.setTime(b.d);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                int r = -1;
                for (int j = 0; j < 7; j++)
                    if (b.days[(j + dayOfWeek+6) % 7]) {
                        if (j == 0) {
                            if (b.d.compareTo(new Date()) > 0) {
                                r = j;
                                break;
                            } else {
                                r = -2;
                            }
                        } else {
                            r = j;
                            break;
                        }
                    }


                if (r == -2)
                    b.d.setDate(b.d.getDate() + 7);
                else if (r != -1)
                    b.d.setDate(b.d.getDate() + r);
                a = b;
            }
        }
        for(int i=0;i<Alarms.size();i++)
        {
            if(Alarms.get(i).isDays()&&Alarms.get(i).enabled)
            {
                Alarms b=new Alarms(Alarms.get(i));

                Calendar c = Calendar.getInstance();
                c.setTime(b.d);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
                int r=-1;
                for(int j=0;j<7;j++)
                    if(b.days[(j+dayOfWeek+6)%7])

                    {

                        if(j==0)
                        {
                            if(b.d.compareTo(new Date())>0)
                            {
                                r=j;
                                break;
                            }
                            else
                            {
                                r=-2;

                            }
                        }

                        else {
                            r=j;
                            break;
                        }
                    }

                if(r==-2)
                    b.d.setDate(b.d.getDate()+7);
                else if(r!=-1)
                    b.d.setDate(b.d.getDate()+r);

                if(a.d.compareTo(b.d)>0)
                    a=new Alarms(b);

            }
        }
        return a;
    }

    static void refreshAlarms()//обновление будильников
    {
        Alarms=new ArrayList();
        Cursor cursor;
        cursor = mDb.rawQuery("select hour,min,days,id,enabled from alarm", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            boolean[] bits = new boolean[7];
            for (int i = 6; i >= 0; i--) {
                bits[i] = (cursor.getInt(2) & (1 << i)) != 0;
            }
            Date curr=new Date();
            curr.setSeconds(0);
            curr.setMinutes(cursor.getInt(1));
            curr.setHours(cursor.getInt(0));

            Alarms.add(new Alarms(curr,bits,cursor.getInt(4)==1?true:false,cursor.getInt(3)));
            cursor.moveToNext();
        }
    }
    static void changeState(int id,boolean value)//вкл/выкл
    {
        if(value)
        mDb.execSQL("update alarm set enabled=1 where id="+id+";");
        else mDb.execSQL("update alarm set enabled=0 where id="+id+";");
        refreshAlarms();

    }
    static void startNewAlarm()//запуск будильника
      {

        try {
            Alarms min = minimum();

            if (min != null) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(min.d);
                MainActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), MainActivity.pendingIntent);
            }
        }
        catch (Exception e){

        }
    }

}