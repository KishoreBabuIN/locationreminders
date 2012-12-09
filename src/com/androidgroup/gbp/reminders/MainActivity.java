package com.androidgroup.gbp.reminders;

import java.util.LinkedList;

import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity  {
    
    // layout
    private ListView _lvTasks;

    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _lvTasks = getListView();
        
        task_list = new TaskList();
        tasks = task_list.get_tasks();
        for (int i = 0; i < 15; ++i) {
            tasks.add(new Task("Task" + i));
        } 
        
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
    
    // called when list item is clicked
    // position is the index of the view clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("OL", "Item clicked: " + position);
    }
    
    
    // attributes
    private LinkedList<Task> tasks;
    private TaskList task_list;
}
