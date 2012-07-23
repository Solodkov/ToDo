package com.myexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<MyEvent> {

    Context context;
    int layoutResourceId;
    List<MyEvent> data;

    public EventAdapter(Context context, int layoutResourceId, List<MyEvent> data) {
        super(context, layoutResourceId, data);
        this.context= context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventHolder holder;

        if(row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);

            holder = new EventHolder();
            holder.task = (TextView) row.findViewById(R.id.title_lv);
            holder.comm = (TextView) row.findViewById(R.id.comm_lv);
            holder.time = (TextView) row.findViewById(R.id.time_lv);
            row.setTag(holder);
        }
        else {
            holder = (EventHolder) row.getTag();
        }

        MyEvent myEvent = data.get(position);
        holder.task.setText(myEvent.task);
        holder.comm.setText(myEvent.comment);

        holder.time.setText(myEvent.getStringTime());

        return row;
    }

    static class EventHolder
    {
        TextView task;
        TextView comm;
        TextView time;
    }
}
