package com.devpro.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.devpro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RegisterCompanyActivityWithMap extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    int PERMISSION_ID = 1; //44
    private Location user_location;
    private Marker marker;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_company_choose_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add your location");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff0BCF5C));

        requestPermissions();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                /*FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);;
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        latitudeTextView.setText(location.getLatitude() + "");
                        longitTextView.setText(location.getLongitude() + "");
                    }
                });*/
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                user_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        user_location = location;
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        // getMenuInflater().inflate(R.layout.activity_bar_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        requestPermissions();
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                //user_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mMap.setMyLocationEnabled(true);
                LatLng user_latlng = new LatLng(user_location.getLatitude(), user_location.getLongitude());
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
                LatLng now = googleMap.getCameraPosition().target;

                marker = mMap.addMarker(new MarkerOptions()
                        .position(user_latlng)
                        .title("Mark your location")
                        .draggable(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user_latlng, 12.0f));

                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_meu:
                setMarkerExtractLocation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMarkerExtractLocation() {
        LatLng location_final = marker.getPosition();
        System.out.println(FirebaseDatabase.getInstance().getReference().push().getKey());

        //mDatabase = FirebaseDatabase.getInstance().getReference().child("companies").orderByChild("host").equalTo("Mike 22");
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LatLng now = mMap.getCameraPosition().target;
        marker.setPosition(now);

        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
