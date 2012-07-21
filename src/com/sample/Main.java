package com.sample;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity
{

    DBHelper dbHelper;
    Button add;
    ToDoApp toDoApp;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        add = (Button) findViewById(R.id.add);         //кнопка вызова активити для добавления новой задачи
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Addition.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);                 //инициализация базы данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        toDoApp = (ToDoApp) getApplication();          //экспорт базы данных в аппликейшн
        toDoApp.setDb(db);
    }


}
