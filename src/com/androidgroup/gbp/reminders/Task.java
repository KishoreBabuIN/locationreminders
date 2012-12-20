// Task.java
// Gregory Paton
// 8 December 2012
// class for holding task information

package com.androidgroup.gbp.reminders;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

public class Task implements Parcelable {

    // attributes
    private String name;                // name that describes task
    private String description;         // describes the task in further detail
    private Date due_time;              // time when task is due
    private Boolean has_due_time;       // true if there is a set due time, else false
    private String location_name;       // name of location of task
    private GeoPoint location;          // geopoint location of task
    private long remind_time;           // amount of time in milliseconds within due time that user should be reminded 
    private float remind_distance;      // distance in meters within task location user should be reminded
    private int task_id;                 // unique identifier for task
    private Context context;

    private static final long serialVersionUID = -2091150675452666853L;
    private static final String file_path = "/data/data/com.androidgroup.gbp.reminders/files/tasks";
    
    
    // methods
    public Task(Context _context) {
        name = "";
        description = "";
        due_time = new Date();
        has_due_time = false;
        location_name = "";
        location = new GeoPoint(0, 0);
        remind_time = 0;
        remind_distance = 0;
        task_id = -1;
        context = _context;
    }
    
    public Task(Context _context, String _name) {
        name = _name;
        description = "";
        due_time = new Date();
        has_due_time = false;
        location_name = "";
        location = new GeoPoint(0, 0);
        remind_time = 0;
        remind_distance = 0;
        task_id = -1;
        context = _context;
    }
    
    public Task(Parcel in) {
        context = null;
        name = in.readString();
        description = in.readString();
        due_time = (Date) in.readSerializable();
        if (in.readInt() == 1)
            has_due_time = true;
        else 
            has_due_time = false;
        location_name = in.readString();
        int lat = in.readInt();
        int lon = in.readInt();
        location = new GeoPoint(lat, lon);
        remind_time = in.readLong();
        remind_distance = in.readFloat();
        task_id = in.readInt();
    }
    
    public void set_context(Context _context) {
        context = _context;
    }
    
    public String get_name() {
        return name;
    }
    
    public void set_name(String _name) {
        name = _name;
    }
    
    public String get_description() {
        return description;
    }
    
    public void set_description(String _description) {
        description = _description;
    }
    
    public long get_due_time() {
        return due_time.getTime();
    }
    
    public String get_due_time_string() {
        return due_time.toString();
    }
    
    public void set_due_time(int min, int hour, int date, int month, int year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month - 1, date, hour, min, 0);
        due_time = cal.getTime();
        has_due_time = true;
    }
    
    public boolean has_duetime() {
        return has_due_time;
    }
    
    public String get_location_name() {
        return location_name;
    }
    
    public void set_location_name(String _location_name) {
        location_name = _location_name;
        new GetAddressLocationTask().execute(location_name);
    }
    
    public GeoPoint get_location_gp() {
        return location;
    }
    
//    public void set_location_gp(GeoPoint gp) {
//        location = gp;
//    }
    
    public long get_remind_time() {
        return due_time.getTime() - remind_time;
    }
    
    public String get_remind_time_string() {
        Date date = due_time;
        date.setTime(date.getTime() - remind_time);
        return date.toString();
    }
    
    public boolean set_remind_time(int mins, int hours, int days) {
        if (mins >= 0 && hours >= 0 && days >= 0) {
            // convert to milliseconds
            remind_time = (mins * 60 * 1000) 
                        + (hours * 60 * 60 * 1000) 
                        + (days * 24 * 60 * 60 * 1000);
            return true;
        }
        return false;
    }
    
    public float get_remind_distance() {
        return remind_distance;
    }
    
    // input: distance (in meters) 
    public boolean set_remind_distance(float distance) {
        if (distance >= 0) {
            remind_distance = distance;
            return true;
        }
        return false;
    }
    
    public int get_id() {
        return task_id;
    }
    
    public void set_id(int id) {
        task_id = id;
    }
    
    private class GetAddressLocationTask extends AsyncTask<String, Void, GeoPoint> {
        @Override
        protected GeoPoint doInBackground(String... _address) {
            if (context == null)
                return null;
            if (_address.length == 0)
                return null;
            Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
            GeoPoint gp = null;
            Address ad = null;
            try {
                List<Address> addresses = geoCoder.getFromLocationName(_address[0], 1);
                for(Address address : addresses){
                    gp = new GeoPoint((int)(address.getLatitude() * 1E6), (int)(address.getLongitude() * 1E6));
                    address.getAddressLine(1);
                    ad = address;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            location = gp;
            return gp;
        }
        
        @Override
        protected void onPostExecute(GeoPoint gp) {
            if (gp == null) {
                location_name = "";
                //Toast.makeText(context, "Unable to find location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeSerializable(due_time);
        if (has_due_time)
            dest.writeInt(1);
        else
            dest.writeInt(0);
        dest.writeString(location_name);
        dest.writeInt(location.getLatitudeE6());
        dest.writeInt(location.getLongitudeE6());
        dest.writeLong(remind_time);
        dest.writeFloat(remind_distance);
        dest.writeInt(task_id);
    }
    
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
