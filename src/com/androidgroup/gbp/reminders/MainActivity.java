package com.androidgroup.gbp.reminders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity  {
    
    // attributes
    private LinkedList<Task> tasks;
    private TaskList task_list;
    
    // layout
    private ListView    _lv_tasks       = null;
    private EditText    _et_addtask     = null;
    private Button      _bt_addtask     = null;
    private Button      _bt_edittask    = null;

    private static final String file_path = "/data/data/com.androidgroup.gbp.reminders/files/tasks.txt";

    
    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _lv_tasks = getListView();
        _et_addtask = (EditText) findViewById(R.id.et_addtask);
        _bt_addtask = (Button) findViewById(R.id.bt_addtask);
        _bt_edittask = (Button) findViewById(R.id.bt_edittask);
        
        task_list = new TaskList();
        tasks = task_list.get_tasks();
        
        update_list();
        
        _bt_addtask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = _et_addtask.getText().toString();
                _et_addtask.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_et_addtask.getWindowToken(), 0);
                if (name.length() != 0)
                    tasks.add(new Task(getApplicationContext(), name));                
                tasks.add(new Task(getApplicationContext(), "HELLO"));
                tasks.get(0).set_location_name("77 Delafield St New Brunswick, NJ 08901");
                update_list();
            }
        });
        
        _bt_edittask.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {

            }
        });
        
        _et_addtask.setOnEditorActionListener(new TextView.OnEditorActionListener() {            
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String name = _et_addtask.getText().toString();
                    _et_addtask.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(_et_addtask.getWindowToken(), 0);
                    if (name.length() != 0)
                        tasks.add(new Task(getApplicationContext(), name));
                    update_list();
                    return true;
                }
                return false;
            }
        });
        
    }
    
    @Override
    protected void onResume() {
        super.onRestart();
        Log.i("OR", "ON RESUME");
    }
    
    @Override
    public void onStop() {
        super.onStop();
        Log.i("OS", "ON STOP");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    // called when list item is clicked
    // position is the index of the view clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("OL", "Item clicked: " + position);
        //tasks.get(position).saveObject(tasks.get(position), position, getApplicationContext());
        Intent intent = new Intent(v.getContext(), ViewTaskActivity.class);
        intent.putExtra("NAME", tasks.get(position).get_name());
        intent.putExtra("DESCRIPTION", tasks.get(position).get_description());
        intent.putExtra("LOCATION", tasks.get(position).get_location_name());
        if (tasks.get(position).has_duetime())
            intent.putExtra("DUEDATE", tasks.get(position).get_due_time_string());
        else
            intent.putExtra("DUEDATE", "");
        startActivityForResult(intent, 0);
    }
    
    public void update_list() {
        if (tasks.size() == 0)
            return;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                                                                android.R.layout.simple_list_item_1, 
                                                                android.R.id.text1, 
                                                                task_list.get_names());
        _lv_tasks.setAdapter(adapter);
    }
}
