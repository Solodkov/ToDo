package com.sample;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ToDoApp extends Application {
    SQLiteDatabase db;

    public void setDb (SQLiteDatabase db){
        this.db = db;
    }

    public void setData(String task, String comment){
        ContentValues cv = new ContentValues();
        cv.put("name",task);
        cv.put("comment",comment);
        long rows = db.insert("tasks",null,cv);
        Log.d("mytag","Rows inserted " + rows);
    }

}
