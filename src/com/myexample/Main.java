package com.myexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.*;

public class Main extends Activity {

    Calendar calendar = Calendar.getInstance();
    int date;
    long time;
    List<MyEvent> list = new ArrayList<MyEvent>();
    List<MyEvent> before = new ArrayList<MyEvent>();
    List<MyEvent> after = new ArrayList<MyEvent>();

    ListView lvTop, lvBottom;
    Button add;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        date = calendar.get(Calendar.DATE);
        time = calendar.getTimeInMillis();

        readContent("content://com.android.calendar/events", this);

        lvTop = (ListView) findViewById(R.id.lvTop);
        lvBottom = (ListView) findViewById(R.id.lvBottom);
        add = (Button) findViewById(R.id.add_task);

        EventAdapter adapterTop = new EventAdapter(this,R.layout.listitem,before);
        lvTop.setAdapter(adapterTop);

   //     EventAdapter adapterBottom = new EventAdapter(this, R.layout.listitem,after);
   //     lvBottom.setAdapter(adapterBottom);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        });
    }






    private void readContent(String uriString, Context mContext) {
        Uri uri = Uri.parse(uriString);
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if ( cursor.moveToFirst() ) {
                do {
                    String task = cursor.getString(1);
                    String comment = cursor.getString(22);
                    long time = cursor.getLong(15);
                    long id = cursor.getLong(52);
                    calendar.setTimeInMillis(time);
                    if (calendar.get(Calendar.DATE)==date){
                        list.add(new MyEvent(time,task,comment,id));
                    }
                } while (cursor.moveToNext());
            }
            sort();
        }
    }

    private void sort(){
        Collections.sort(list,new EventCompare());
        Iterator iterator = list.iterator();
        MyEvent temp;
        while (iterator.hasNext()){
            temp = (MyEvent) iterator.next();
            if (temp.time < time){
                before.add(temp);
            }
            else {
                after.add(temp);
            }
        }
    }


}