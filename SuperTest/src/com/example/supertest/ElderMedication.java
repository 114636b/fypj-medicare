package com.example.supertest;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ElderMedication extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cgmedicine);
         
        TabHost tabHost = getTabHost();
         
        // Tab for Current
        TabSpec medspec = tabHost.newTabSpec("Medication Info");
        // setting Title and Icon for the Tab
        medspec.setIndicator("Current Reminder", getResources().getDrawable(R.drawable.icon_medicine_tab));
        Intent medIntent = new Intent(this, EMedicineTab.class);
        medspec.setContent(medIntent);
         
        // Tab for History
        TabSpec hisspec = tabHost.newTabSpec("Past Medication");        
        hisspec.setIndicator("Past Reminder", getResources().getDrawable(R.drawable.ic_whats_hot));
        Intent histIntent = new Intent(this, EMedicineHistoryTab.class);
        hisspec.setContent(histIntent);
         
       
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(medspec); // Adding MED tab 
        tabHost.addTab(hisspec); // Adding history tab
    
       
    }
}