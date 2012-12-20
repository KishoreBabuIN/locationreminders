package com.androidgroup.gbp.reminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _tv_name.setText(extras.getString("NAME"));
            _tv_description.setText(extras.getString("DESCRIPTION"));
            _tv_location.setText(extras.getString("LOCATION"));
            if (extras.getInt("HASDUEDATE") == 1) {
                
            }
        }
        
        _bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        
        _bt_edit.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        
        _bt_delete.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
    }

}
