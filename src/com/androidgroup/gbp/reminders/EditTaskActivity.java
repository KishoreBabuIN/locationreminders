package com.androidgroup.gbp.reminders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditTaskActivity extends Activity {
    
    EditText    _et_name        = null;
    EditText    _et_description = null;
    EditText    _et_location    = null;
    TimePicker  _tp_time        = null;
    DatePicker  _dp_date        = null;
    Button      _bt_done        = null;
    CheckBox    _cb_due_date    = null;
    private static final String file_path = "/data/data/com.androidgroup.gbp.reminders/files/tasks";

    public EditTaskActivity() {
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
        _et_name = (EditText) findViewById(R.id.et_name);
        _et_description = (EditText) findViewById(R.id.et_description);
        _et_location = (EditText) findViewById(R.id.et_location);
        _tp_time = (TimePicker) findViewById(R.id.tp_time);
        _dp_date = (DatePicker) findViewById(R.id.dp_date);
        _bt_done = (Button) findViewById(R.id.bt_done);
        _cb_due_date = (CheckBox) findViewById(R.id.cb_due_date);
        
        _cb_due_date.setChecked(false);
        _dp_date.setVisibility(View.INVISIBLE);
        _tp_time.setVisibility(View.INVISIBLE);
        
        _cb_due_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (_cb_due_date.isChecked()) {
                    _dp_date.setVisibility(View.VISIBLE);
                    _tp_time.setVisibility(View.VISIBLE);  
                }
                else {
                    _dp_date.setVisibility(View.INVISIBLE);
                    _tp_time.setVisibility(View.INVISIBLE);  
                }
            }
        });
        
        _bt_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewTaskActivity.class);
                intent.putExtra("NAME", _et_name.getText().toString());
                intent.putExtra("DESCRIPTION", _et_description.getText().toString());
                intent.putExtra("LOCATION", _et_description.getText().toString());
                if (_cb_due_date.isChecked() == false) 
                    intent.putExtra("HASDUEDATE", 0);
                else {
                    intent.putExtra("HASDUEDATE", 1);
                    intent.putExtra("DATE", _dp_date.getDayOfMonth());
                    intent.putExtra("Month", _dp_date.getMonth());
                    intent.putExtra("YEAR", _dp_date.getYear());
                    intent.putExtra("HOUR", _tp_time.getCurrentHour());
                    intent.putExtra("MINUTE", _tp_time.getCurrentMinute());   
                }
                startActivityForResult(intent, 0);
            }
        });
    }   
}