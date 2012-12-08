// Task.java
// Gregory Paton
// 8 December 2012
// class for holding task information

package com.androidgroup.gbp.reminders;

import java.util.Date;
import com.google.android.maps.GeoPoint;

public class Task {

    // methods
    public Task() {
        // TODO Auto-generated constructor stub
        remind_time = 0;
        remind_distance = 0;
    }
    
    public long get_due_time() {
        return due_time.getTime();
    }
    
    public void set_due_time() {
        
    }
    
    public GeoPoint get_location() {
        return location;
    }
    
    public void set_location() {
        
    }
    
    public long get_remind_time() {
        return remind_time;
    }
    
    // input: time (in minutes)
    public boolean set_remind_time(long time) {
        if (time >= 0) {
            remind_time = time * 60 * 1000;     // convert minutes to milliseconds
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
    private Date due_time;              // time when task is due
    private GeoPoint location;          // location of task
    private long remind_time;           // amount of time in milliseconds within due time that user should be reminded 
    private float remind_distance;      // distance in meters within task location user should be reminded
}
