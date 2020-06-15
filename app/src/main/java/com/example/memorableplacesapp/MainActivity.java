package com.example.memorableplacesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static ListView listView;
    static ArrayList<String> places;
    static ArrayList<Location> locations;
    static ArrayAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        places = new ArrayList<String>();
        locations = new ArrayList<Location>();
        listView = findViewById(R.id.listView);

        Location dummyLocation = new Location(LocationManager.GPS_PROVIDER);
        dummyLocation.setLongitude(34);
        dummyLocation.setLatitude(45);

        places.add("Add a new place!");
        locations.add(dummyLocation);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);

        listView.setAdapter(arrayAdapter);


        final Intent intent = new Intent(getApplicationContext(), MapsActivity.class);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("locationIndex", position);
                startActivity(intent);

            }
        });


    }
}