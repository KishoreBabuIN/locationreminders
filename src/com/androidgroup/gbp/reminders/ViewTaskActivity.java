package com.androidgroup.gbp.reminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTaskActivity extends Activity {
    
    private TextView _tv_name;
    private TextView _tv_description;
    private TextView _tv_location;
    private TextView _tv_duedate;
    private Button _bt_back;
    
    
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
        _bt_back =          (Button) findViewById(R.id.bt_back);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _tv_name.setText(extras.getString("NAME"));
            _tv_description.setText(extras.getString("DESCRIPTION"));
            _tv_location.setText(extras.getString("LOCATION"));
            _tv_duedate.setText(extras.getString("DUEDATE"));
        }
        
        _bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
