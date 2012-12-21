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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.maps.GeoPoint;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.AlertDialog;
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

    private double latitude;
    private double longitude;
    private GeoPoint location;
    
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
        

        //Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                if (loc != null) {
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    location = new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6));
                    Log.i("LOC", location.toString());
                    LinkedList<Task> list = task_list.find_near_location(location);
                    for (Task task : list) {
                        Log.i("LOC", task.get_name());
                        //new AlertDialog.Builder(getApplicationContext()).setTitle(task.get_name()).setMessage("Watch out!").setNeutralButton("Close", null).show();
                    }
                    //mapCon.setZoom(12);
                    //mapCon.setCenter(point);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        
        _bt_addtask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = _et_addtask.getText().toString();
                _et_addtask.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(_et_addtask.getWindowToken(), 0);
                if (name.length() != 0)
                    tasks.add(new Task(getApplicationContext(), name));                
                update_list();
            }
        });
        
        _bt_edittask.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
                intent.putExtra("TASK", new Task(getApplicationContext(), _et_addtask.getText().toString()));
                startActivityForResult(intent, 0);
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
        Log.i("OR", "ON RESUME MAIN");
        Task temp = getIntent().getParcelableExtra("TASK");
        if (temp != null)
            task_list.set_by_id(temp, temp.get_id());
        update_list();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        Log.i("OS", "ON STOP MAIN");
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
        intent.putExtra("TASK", tasks.get(position));
        startActivityForResult(intent, 0);
    }
    
    public void update_list() {
        if (tasks.size() == 0)
            return;
        int i = 0;
        for(Task task : tasks) {
            task.set_id(i);
            ++i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
                                                                android.R.layout.simple_list_item_1, 
                                                                android.R.id.text1, 
                                                                task_list.get_names());
        _lv_tasks.setAdapter(adapter);
    }
    
    private class updateLocationTask extends TimerTask {
        @Override
        public void run() {
            
        }
        
    }
}
