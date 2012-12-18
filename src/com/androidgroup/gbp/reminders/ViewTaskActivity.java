package com.androidgroup.gbp.reminders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewTaskActivity extends Activity {
    
    private Button _bt_back;

    public ViewTaskActivity() {
        // TODO Auto-generated constructor stub
    }
    
    // called when activity is created
    // sets up the view task activity 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        
        _bt_back = (Button) findViewById(R.id.bt_back);
        
        _bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
