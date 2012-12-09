package com.androidgroup.gbp.reminders;

import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    
    // layout
    private ListView _lvTasks;

    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _lvTasks = (ListView) findViewById(R.id.lvTasks);
        
        task_list = new TaskList();
        tasks = task_list.get_tasks();
        tasks.add(new Task("move"));
        tasks.add(new Task("Buy Milk"));
        tasks.add(new Task("add task"));
        tasks.get(0).set_due_time(0, 6, 8, 12, 2012);
        tasks.get(0).set_remind_time(37, 2, 1);
        Log.i("OC", "Time: " + tasks.get(0).get_due_time_string());
        Log.i("OC", "time: " + tasks.get(0).get_remind_time_string());
        String[] names = task_list.get_names();
        Log.i("OC", "name: " + names[0]);        
        
        task_list.sort_by_name();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                                                                android.R.layout.simple_list_item_1, 
                                                                android.R.id.text1, 
                                                                task_list.get_names());
        _lvTasks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    // attributes
    private LinkedList<Task> tasks;
    private TaskList task_list;
}
