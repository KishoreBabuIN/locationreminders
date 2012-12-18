package com.androidgroup.gbp.reminders;

import java.util.LinkedList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ListActivity  {
    
    // attributes
    private LinkedList<Task> tasks;
    private TaskList task_list;
    
    // layout
    private ListView    _lvTasks        = null;
    private EditText    _et_addtask     = null;
    private Button      _bt_addtask     = null;
    private Button      _bt_edittask    = null;

    
    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _lvTasks = getListView();
        _et_addtask = (EditText) findViewById(R.id.et_addtask);
        _bt_addtask = (Button) findViewById(R.id.bt_addtask);
        _bt_edittask = (Button) findViewById(R.id.bt_edittask);
        
        task_list = new TaskList();
        tasks = task_list.get_tasks();
        for (int i = 0; i < 15; ++i) {
            tasks.add(new Task("Task" + i));
        } 
        tasks.get(2).set_due_time(00, 4, 1, 1, 2013);
        tasks.get(2).set_description("Do Stuff");
        tasks.get(2).set_location_name("Home");
        
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
//                                                                android.R.layout.simple_list_item_1, 
//                                                                android.R.id.text1, 
//                                                                task_list.get_names());
//        _lvTasks.setAdapter(adapter);
        update_list();
        
        _bt_addtask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tasks.add(new Task(_et_addtask.getText().toString()));
                _et_addtask.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_et_addtask.getWindowToken(), 0);
                update_list();
            }
        });
        
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                                                                android.R.layout.simple_list_item_1, 
                                                                android.R.id.text1, 
                                                                task_list.get_names());
        _lvTasks.setAdapter(adapter);
    }
}
