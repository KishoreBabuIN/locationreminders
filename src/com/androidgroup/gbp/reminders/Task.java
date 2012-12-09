// Task.java
// Gregory Paton
// 8 December 2012
// class for holding task information

package com.androidgroup.gbp.reminders;

import java.util.Date;
import java.util.GregorianCalendar;
import com.google.android.maps.GeoPoint;

public class Task {

    // methods
    public Task() {
        name = "";
        due_time = new Date();
        has_due_time = false;
        remind_time = 0;
        remind_distance = 0;
    }
    
    public Task(String _name) {
        name = _name;
        due_time = new Date();
        remind_time = 0;
        remind_distance = 0;
    }
    
    public String get_name() {
        return name;
    }
    
    public void set_name(String _name) {
        name = _name;
    }
    
    public long get_due_time() {
        return due_time.getTime();
    }
    
    public String get_due_time_string() {
        return due_time.toString();
    }
    
    public void set_due_time(int min, int hour, int date, int month, int year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, date, hour, min, 0);
        due_time = cal.getTime();
    }
    
    public GeoPoint get_location() {
        return location;
    }
    
    public void set_location(GeoPoint gp) {
        location = gp;
    }
    
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

    
    
    // attributes
    private String name;                // name that describes task
    private Date due_time;              // time when task is due
    private boolean has_due_time;       // true if there is a set due time, else false
    private GeoPoint location;          // location of task
    private long remind_time;           // amount of time in milliseconds within due time that user should be reminded 
    private float remind_distance;      // distance in meters within task location user should be reminded
}
