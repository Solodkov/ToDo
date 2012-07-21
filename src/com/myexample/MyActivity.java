package com.myexample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

public class MyActivity extends Activity{


    Button add, read, date, contact, time;
    TextView textView;
    EditText task, comm;
    DatePicker picker;
    Calendar calendar = Calendar.getInstance();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initViews();    //Инициализация всех элементов Активити
        final Context context = getApplicationContext();

        //****************************устанавливаем обработчики нажатий кнопок******************************************
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MyActivity.this, dateSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MyActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();}
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task_ = task.getText().toString();
                String comm_ = comm.getText().toString();
                long date=calendar.getTimeInMillis();
                Long num;
                num = pushAppointmentsToCalender(context,task_,comm_,"Home",0,date,false,false);
                textView.setText(num.toString());
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriString = "content://com.android.calendar/calendars";
                Log.i("INFO", "Reading content from " + uriString);
                readContent(uriString, context);
                uriString = "content://com.android.calendar/events";
                Log.i("INFO", "Reading content from " + uriString);
                readContent(uriString, context);
            }
        });
        //**************************************************************************************************************
    }

    //******************************функция иницилизации элементов активити*********************************************
    private void initViews(){
        date = (Button) findViewById(R.id.date);
        contact = (Button) findViewById(R.id.contact);
        add = (Button) findViewById(R.id.add);
        read = (Button) findViewById(R.id.read);
        time = (Button) findViewById(R.id.time);

        task = (EditText) findViewById(R.id.task);
        comm = (EditText) findViewById(R.id.comm);

        textView = (TextView) findViewById(R.id.text);
    }
    //******************************************************************************************************************

    //**********************Инициализация диалога выбора даты***********************************************************
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
        }
    };
    //******************************************************************************************************************

    //******************************************************************************************************************
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
        }
    };
    //******************************************************************************************************************


    private void readContent(String uriString, Context mContext) {
        Uri uri = Uri.parse(uriString);
        Cursor cursor = mContext.getContentResolver().query(uri, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String columnNames[] = cursor.getColumnNames();
            String value = "";
            String colNamesString = "";
            do {
                value = "";
                for (String colName : columnNames) {
                    value += colName + " = ";
                    value += cursor.getString(cursor.getColumnIndex(colName))
                            + " ||";
                }
                Log.e("INFO : ", value);
            } while (cursor.moveToNext());
        }
    }

    public static long pushAppointmentsToCalender(Context curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService) {
        /***************** Event: note(without alert) *******************/

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate; // For next 1hr

        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("visibility", 3); // visibility to default (0),
        // confidential (1), private
        // (2), or public (3):
        eventValues.put("transparency", 0); // You can control whether
        // an event consumes time
        // opaque (0) or transparent
        // (1).
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true


        Uri eventUri = curActivity.getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }
        return eventID;
    }
}
