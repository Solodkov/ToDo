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
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }
}

class EventCompare implements Comparator<MyEvent>{
    @Override
    public int compare(MyEvent myEvent, MyEvent myEvent1) {
        int diff;
        diff = (int) ((int) myEvent.getTime() - myEvent1.getTime());
        return diff;
    }
}
