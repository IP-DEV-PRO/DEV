package com.devpro.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.devpro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.devpro.models.*;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.devpro.models.Company;

public class RegisterCompanyActivityWithMap extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    int PERMISSION_ID = 1; //44
    private Location user_location;
    private Marker marker;
    private DatabaseReference mDatabase;
    private String companyKey;
    private String name, cui, first, last, phone;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static final String[] PERMISSIONS_API = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    public static void requestPermissionLocation(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q)
            ActivityCompat.requestPermissions(activity, PERMISSIONS, requestCode);
        else
            ActivityCompat.requestPermissions(activity, PERMISSIONS_API, requestCode);
    }

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

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        cui = intent.getStringExtra("cui");
        first = intent.getStringExtra("first");
        last = intent.getStringExtra("last");
        phone = intent.getStringExtra("phone");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add your location");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff0BCF5C));

        //requestPermissions();
        requestPermissionLocation(this, PERMISSION_ID);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkPermissions()) {
            System.out.println("A INTRAT");
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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                user_location = getLastKnownLocation();
                System.out.println("LOCATIE ->> " + user_location);
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {


            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }

        System.out.println("OHOHOHO");
        return bestLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("UITE 134");
        user_location = location;
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }

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
                com.google.android.gms.maps.model.LatLng now = googleMap.getCameraPosition().target;
                com.google.android.gms.maps.model.LatLng user_latlng_google = new com.google.android.gms.maps.model.LatLng(user_latlng.getLatitude(), user_latlng.getLongitude());

                marker = mMap.addMarker(new MarkerOptions()
                        .position(user_latlng_google)
                        .title("Mark your location")
                        .draggable(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user_latlng_google, 12.0f));

                mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        com.google.android.gms.maps.model.LatLng now = mMap.getCameraPosition().target;
                        marker.setPosition(now);
                    }
                });

//                mMap.setOnCameraMoveStartedListener(i -> {
//                    LatLng now1 = mMap.getCameraPosition().target;
//                    marker.setPosition(now1);
//                });
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
        com.google.android.gms.maps.model.LatLng location_final = marker.getPosition();

        List<com.devpro.models.Location> locationList = new ArrayList<>();
        locationList.add(new com.devpro.models.Location(new LatLng(location_final.latitude, location_final.longitude), "no-street", "no-city", "no-country", "no-number"));

        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies");

        Company registeredCompany = new Company(name, null, cui, last,
                first, phone, locationList);
        mDatabase.child(name).setValue(registeredCompany);
        //FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies").child(companyKey).child("locationList").setValue(locationList);
        // mDatabase.child(username).setValue(registeredUser);
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("companies").orderByChild("host").equalTo("Mike 22");
    }


    private void changeActiviy(Class activityClass, String key) {
        //Intent myIntent = new Intent(this, activityClass);
        // myIntent.putExtra("key-company", key);
        //  startActivity(myIntent);
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        com.google.android.gms.maps.model.LatLng now = mMap.getCameraPosition().target;
        marker.setPosition(now);
        System.out.println("UITE" + now);
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("UITE2");

        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        System.out.println("UITE3");


        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        System.out.println("UITE4");


        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        System.out.println("UITE5");

        super.onPointerCaptureChanged(hasCapture);
    }
}
