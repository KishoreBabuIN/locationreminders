package com.androidgroup.gbp.reminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTaskActivity extends Activity {
    
    private TextView _tv_name           = null;
    private TextView _tv_description    = null;
    private TextView _tv_location       = null;
    private TextView _tv_duedate        = null;
    private Button   _bt_back           = null;
    private Button   _bt_edit           = null;
    private Button   _bt_delete         = null;
    
    private Task task = null;
    
    
    public ViewTaskActivity() {
        // TODO Auto-generated constructor stub
    }
    
    // called when activity is created
    // sets up the view task activity 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        
        _tv_name =          (TextView) findViewById(R.id.tv_name);
        _tv_description =   (TextView) findViewById(R.id.tv_description);
        _tv_location =      (TextView) findViewById(R.id.tv_location);
        _tv_duedate =       (TextView) findViewById(R.id.tv_duedate);
        _bt_back =          (Button)   findViewById(R.id.bt_back);
        _bt_edit =          (Button)   findViewById(R.id.bt_edit);
        _bt_delete =        (Button)   findViewById(R.id.bt_delete);
        
        Log.i("OC", "VIEWTASK");
        
        Task temp = getIntent().getParcelableExtra("TASK");
        if (temp != null)
            task = temp;

        update();
        
        _bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("TASK", task);
                startActivityForResult(intent, 0);
            }
        });
        
        _bt_edit.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
                intent.putExtra("TASK", task);
                startActivityForResult(intent, 0);
            }
        });
        
        _bt_delete.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
    }
    
    public void update() {
        if (task == null)
            return;
        _tv_name.setText(task.get_name());
        _tv_description.setText(task.get_description());
        if (task.has_loc()) 
            _tv_location.setText(task.get_location_name());
        else 
            _tv_location.setText("");
        if (task.has_duetime())
            _tv_duedate.setText(task.get_due_time_string());
        else
            _tv_duedate.setText("");
    }
    
    @Override
    protected void onResume() {
        super.onRestart();
        Log.i("OR", "ON RESUME VIEW");
        Task temp = getIntent().getParcelableExtra("TASK");
        if (temp != null)
            task = temp;
        update();
    }
}
