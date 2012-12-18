package com.androidgroup.gbp.reminders;

import java.util.Comparator;
import java.util.LinkedList;

import com.google.android.maps.GeoPoint;

public class TaskList {

    public TaskList() {
        tasks = new LinkedList<Task>();
    }
    
    public LinkedList<Task> get_tasks() {
        return tasks;
    }
    
    public String[] get_names() {
        int size = tasks.size();
        if (size == 0)
            return null;
        String[] names = new String[size];
        for (int i = 0; i < size; ++i)
            names[i] = tasks.get(i).get_name();
        return names;
    }
    
    public void sort_by_due_time() {
        int size = tasks.size();
        if (size == 0)
            return;
        Comparator<Task> comp = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return (int)(task1.get_due_time() - task2.get_due_time());
            }
        };
        java.util.Collections.sort(tasks, comp);
    }
    
    public void sort_by_distance(GeoPoint gp) {
        final GeoPoint loc = gp;
        int size = tasks.size();
        if (size == 0)
            return;
        Comparator<Task> comp = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                float[] dist1 = new float[1];
                float[] dist2 = new float[1];
                double task1_lat = task1.get_location_gp().getLatitudeE6();
                double task1_long = task1.get_location_gp().getLongitudeE6();
                double task2_lat = task2.get_location_gp().getLatitudeE6();
                double task2_long = task2.get_location_gp().getLongitudeE6();
                android.location.Location.distanceBetween(loc.getLatitudeE6(), 
                                                          loc.getLongitudeE6(), 
                                                          task1_lat, 
                                                          task1_long, 
                                                          dist1);
                android.location.Location.distanceBetween(loc.getLatitudeE6(), 
                                                          loc.getLatitudeE6(), 
                                                          task2_lat, 
                                                          task2_long, 
                                                          dist2);
                return (int)(dist1[0] - dist2[0]);
            }
        };
        java.util.Collections.sort(tasks, comp);
    }
    
    public void sort_by_name() {
        int size = tasks.size();
        if (size == 0)
            return;
        Comparator<Task> comp = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return task1.get_name().compareToIgnoreCase(task2.get_name());
            }
        };
        java.util.Collections.sort(tasks, comp);
    }

    private LinkedList<Task> tasks;
}
