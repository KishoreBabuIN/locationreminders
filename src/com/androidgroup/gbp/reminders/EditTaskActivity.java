package com.androidgroup.gbp.reminders;


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
    private AlertDialog location_dialog;
    
    EditText    _et_name        = null;
    EditText    _et_description = null;
    EditText    _et_location    = null;
    TimePicker  _tp_time        = null;
    DatePicker  _dp_date        = null;
    Button      _bt_done        = null;
    Button      _bt_cancel      = null;
    CheckBox    _cb_due_date    = null;
    CheckBox    _cb_location    = null; 
    
    private Task task = null;
    private Context context;

    public EditTaskActivity() {
        // TODO Auto-generated constructor stub
    }

    public void onCreate(Bundle savedInstanceState) {
        Log.i("oc", "here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Log.i("oc", "here");
        
        _et_name =          (EditText)   findViewById(R.id.et_name);
        _et_description =   (EditText)   findViewById(R.id.et_description);
        _et_location =      (EditText)   findViewById(R.id.et_location);
        _tp_time =          (TimePicker) findViewById(R.id.tp_time);
        _dp_date =          (DatePicker) findViewById(R.id.dp_date);
        _bt_done =          (Button)     findViewById(R.id.bt_done);
        _bt_cancel =        (Button)     findViewById(R.id.bt_cancel);
        _cb_due_date =      (CheckBox)   findViewById(R.id.cb_due_date);
        _cb_location =      (CheckBox)   findViewById(R.id.cb_location);
        

        Task temp = getIntent().getParcelableExtra("TASK");
        if (temp != null) {
            task = temp;
            _et_name.setText(task.get_name());
            _et_description.setText(task.get_description());
            _et_location.setText(task.get_location_name());
        }
                        
        if (task.has_duetime()) {
            _cb_due_date.setChecked(true);
            _dp_date.setVisibility(View.VISIBLE);
            _tp_time.setVisibility(View.VISIBLE);
        }
        else {
            _cb_due_date.setChecked(false);
            _dp_date.setVisibility(View.INVISIBLE);
            _tp_time.setVisibility(View.INVISIBLE);
        }
        
        if (task.has_loc()) {
            _cb_location.setChecked(true);
            _et_location.setVisibility(View.VISIBLE);
        }
        else {
            _cb_location.setChecked(false);
            _et_location.setVisibility(View.INVISIBLE);
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
        
        _cb_location.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_cb_due_date.getWindowToken(), 0);
                if (_cb_location.isChecked()) 
                    _et_location.setVisibility(View.VISIBLE);
                else
                    _et_location.setVisibility(View.INVISIBLE);
            }
        });
        
        _bt_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewTaskActivity.class);
                task.set_name(_et_name.getText().toString());
                task.set_description(_et_description.getText().toString());
                if (_cb_location.isChecked()) {
                    task.set_location(_et_location.getText().toString(), v.getContext());
                }
                else {
                    task.set_location("", v.getContext());
                }
                if (_cb_due_date.isChecked()) {
                    task.set_has_due_time(true);
                    int min   = _tp_time.getCurrentMinute();
                    int hour  = _tp_time.getCurrentHour();
                    Log.i("hour", String.valueOf(hour));
                    int date  = _dp_date.getDayOfMonth();
                    int month = _dp_date.getMonth();
                    int year  = _dp_date.getYear();
                    task.set_due_time(min, hour, date, month, year);
                }
                else {
                    task.set_has_due_time(false);
                }
                intent.putExtra("TASK", task);
                startActivityForResult(intent, 0);
            }
        });
        
        _bt_cancel.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                show_cancel_dialog(EditTaskActivity.this);
            }
        });
        
        _et_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(_et_location.getWindowToken(), 0);
                    // check if location is valid
                    Task temp = new Task(v.getContext());
                    if(temp.set_location(_et_location.getText().toString(), v.getContext()) == false) {
                        show_location_dialog(v.getContext());
                        _et_location.setText("");
                    }
                    return true;
                }
                return false;
            }
            
        });
        
//        _tp_time.setOnClickListener(new View.OnClickListener() {            
//            public void onClick(View v) {
//                Log.i("TP", "ON CLICK");
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(_tp_time.getWindowToken(), 0);
//            }
//        });
        
//        _dp_date.setOnClickListener(new View.OnClickListener() {            
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(_dp_date.getWindowToken(), 0);
//            }
//        });
    }  

    private void show_cancel_dialog(Context _context) {
            if(cancel_dialog != null && cancel_dialog.isShowing()) 
                return;
            if (_context == null)
                return;
            context = _context;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cancel");
            builder.setMessage("Are you sure you want to cancel?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        cancel_dialog.dismiss();
                        Intent intent = new Intent(context, ViewTaskActivity.class);
                        intent.putExtra("TASK", task);
                        startActivityForResult(intent, 0);
                    }});
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {                
                public void onClick(DialogInterface dialog, int which) {
                    cancel_dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            cancel_dialog = builder.create();
            cancel_dialog.show();
    }

    private void show_location_dialog(Context _context) {
            if(location_dialog != null && location_dialog.isShowing()) 
                return;
            if (_context == null)
                return;
            context = _context;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Invalid Location");
            builder.setMessage("Please set a valid location");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        cancel_dialog.dismiss();
                    }});
            builder.setCancelable(false);
            cancel_dialog = builder.create();
            cancel_dialog.show();
    }

    private void show_change_location_dialog(Context _context) {
            if(location_dialog != null && location_dialog.isShowing()) 
                return;
            if (_context == null)
                return;
            context = _context;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Invalid Location");
            builder.setMessage("Set a valid location?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        cancel_dialog.dismiss();
                    }});
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {                
                public void onClick(DialogInterface dialog, int which) {
                    cancel_dialog.dismiss();
                    Intent intent = new Intent(context, ViewTaskActivity.class);
                    intent.putExtra("TASK", task);
                    startActivityForResult(intent, 0);
                }
            });
            builder.setCancelable(false);
            cancel_dialog = builder.create();
            cancel_dialog.show();
    }
}