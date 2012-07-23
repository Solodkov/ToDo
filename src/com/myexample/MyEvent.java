package com.myexample;

import java.util.Calendar;
import java.util.Comparator;

public class MyEvent {
    long time;
    long id;
    String task, comment;

    public MyEvent(){
        super();
    }

    public MyEvent(long t, String task, String comment, long id){
        super();
        time =t;
        this.task = task;
        this.comment = comment;
        this.id = id;
    }
    public long getTime (){
        return time;
    }

    public String getStringTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        StringBuilder stringBuilder = new StringBuilder();

        if (calendar.get(Calendar.HOUR_OF_DAY)<10){
            stringBuilder.append("0").append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
        }
        else {
            stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
        }
        if (calendar.get(Calendar.MINUTE)<10){
            stringBuilder.append("0").append(calendar.get(Calendar.MINUTE));
        }
        else {
            stringBuilder.append(calendar.get(Calendar.MINUTE));
        }
        return stringBuilder.toString();
    }
}

class EventCompare implements Comparator<MyEvent>{
    @Override
    public int compare(MyEvent myEvent, MyEvent myEvent1) {
        long result = myEvent.getTime() - myEvent1.getTime();

        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
