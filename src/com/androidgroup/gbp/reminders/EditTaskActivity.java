package com.androidgroup.gbp.reminders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;

public class EditTaskActivity extends Activity {

    private AlertDialog cancel_dialog;
    private boolean cancel_dialog_result;
    
    EditText    _et_name        = null;
    EditText    _et_description = null;
    EditText    _et_location    = null;
    TimePicker  _tp_time        = null;
    DatePicker  _dp_date        = null;
    Button      _bt_done        = null;
    Button      _bt_cancel      = null;
    CheckBox    _cb_due_date    = null;

    public EditTaskActivity() {
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
        _et_name =          (EditText)   findViewById(R.id.et_name);
        _et_description =   (EditText)   findViewById(R.id.et_description);
        _et_location =      (EditText)   findViewById(R.id.et_location);
        _tp_time =          (TimePicker) findViewById(R.id.tp_time);
        _dp_date =          (DatePicker) findViewById(R.id.dp_date);
        _bt_done =          (Button)     findViewById(R.id.bt_done);
        _bt_cancel =        (Button)     findViewById(R.id.bt_cancel);
        _cb_due_date =      (CheckBox)   findViewById(R.id.cb_due_date);
        
        _cb_due_date.setChecked(false);
        _dp_date.setVisibility(View.INVISIBLE);
        _tp_time.setVisibility(View.INVISIBLE);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _et_name.setText(extras.getString("NAME"));
            _et_description.setText(extras.getString("DESCRIPTION"));
            _et_location.setText(extras.getString("LOCATION"));
            //_et_due_date.setText(extras.getString("DUEDATE"));
        }
        
        _cb_due_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_cb_due_date.getWindowToken(), 0);
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
                intent.putExtra("LOCATION", _et_location.getText().toString());
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
        
        _bt_cancel.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                if (show_cancel_dialog(EditTaskActivity.this)) {
                    
                }
            }
        });
        
        _et_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(_et_location.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
            
        });
        
        _tp_time.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                Log.i("TP", "ON CLICK");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_tp_time.getWindowToken(), 0);
            }
        });
        
        _dp_date.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_dp_date.getWindowToken(), 0);
            }
        });
    }  

    private boolean show_cancel_dialog(Context context) {
            if(cancel_dialog != null && cancel_dialog.isShowing()) 
                return false;
            cancel_dialog_result = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cancel");
            builder.setMessage("Are you sure you want to cancel?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        cancel_dialog_result = true;
                        cancel_dialog.dismiss();
                    }});
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {                
                public void onClick(DialogInterface dialog, int which) {
                    cancel_dialog_result = false;
                    cancel_dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            cancel_dialog = builder.create();
            cancel_dialog.show();
            return cancel_dialog_result;
    }
}