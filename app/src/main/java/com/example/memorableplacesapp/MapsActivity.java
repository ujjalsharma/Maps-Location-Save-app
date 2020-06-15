package com.example.memorableplacesapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int locationIndex;


    public void getAddress(Location location){

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> listAddress= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (listAddress != null && listAddress.size() > 0){
                String address = "";

                if (listAddress.get(0).getThoroughfare() != null){
                    address += listAddress.get(0).getThoroughfare() + " ";
                }

                if (listAddress.get(0).getLocality() != null){
                    address += listAddress.get(0).getLocality() + " ";
                }

                if (listAddress.get(0).getPostalCode() != null){
                    address += listAddress.get(0).getPostalCode() + " ";
                }

                if (listAddress.get(0).getAdminArea() != null){
                    address += listAddress.get(0).getAdminArea();
                }

                if (address==""){
                    address = String.valueOf(new Date().getTime());
                }

                MainActivity.places.add(address);
                MainActivity.locations.add(location);
                MainActivity.listView.setAdapter(MainActivity.arrayAdapter);
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title(address));




            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        locationIndex = intent.getIntExtra("locationIndex", 0);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Location newLocation = MainActivity.locations.get(locationIndex);
        LatLng userLocation = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLocation).title(MainActivity.places.get(locationIndex)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                Location pressedLocation = new Location(LocationManager.GPS_PROVIDER);
                pressedLocation.setLatitude(latLng.latitude);
                pressedLocation.setLongitude(latLng.longitude);
                getAddress(pressedLocation);

            }
        });





    }
}