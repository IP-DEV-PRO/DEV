package com.devpro.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.R;
import com.devpro.models.Request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.devpro.models.Company;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomePage extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private BottomNavigationView bottomNavigationView;
    private DatabaseReference mDatabase;
    int PERMISSION_ID = 2; //44
    private GoogleMap mMap;
    private Marker marker;
    private LocationManager locationManager;
    private Location user_location;
    static String userId;
    private HashMap<Marker, Pair<String, com.devpro.models.Location>> markerArrayList;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout copy;
    LinearLayout share;
    LinearLayout upload;
    LinearLayout download;
    LinearLayout delete;
    TextView title_text, descr_text, address_text, phone_text, email_text;
    AutoCompleteTextView get_service;
    Button select_button;



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

    private void showBottomSheetDialog() {


        bottomSheetDialog.show();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        userId = getIntent().getStringExtra("key-user");
        System.out.println(userId);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mDatabase = FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies");

        markerArrayList = new HashMap<>();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_edit:
                    changeActiviy(EditAccountActivity.class, userId, null);
                    return true;
                case R.id.bottom_navigation_subscription:
                    changeActiviy(SubscriptionActivity.class, userId, null);
                    return true;
                case R.id.bottom_navigation_requests:
                    changeActiviy(UserViewSentRequests.class, userId, null);
                    return true;
            }
            return false;
        });

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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                user_location = getLastKnownLocation();

                bottomSheetDialog = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet);

                copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
                share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
                upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
                download = bottomSheetDialog.findViewById(R.id.download);
                delete = bottomSheetDialog.findViewById(R.id.delete);

                title_text = bottomSheetDialog.findViewById(R.id.title_marker);
                descr_text = bottomSheetDialog.findViewById(R.id.description_marker);
                address_text = bottomSheetDialog.findViewById(R.id.address_marker);
                phone_text = bottomSheetDialog.findViewById(R.id.phone_marker);
                email_text = bottomSheetDialog.findViewById(R.id.email_marker);
                select_button = bottomSheetDialog.findViewById(R.id.button_marker);
                get_service = bottomSheetDialog.findViewById(R.id.command_service_2);
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
        return bestLocation;
    }

    private void changeActiviy(Class activityClass, String userId, String ownerKey) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        if(ownerKey != null)
            myIntent.putExtra("key-owner",ownerKey);
        startActivity(myIntent);
    }

    private void changeActiviyFromSelect(Class activityClass, String userId, String ownerKey, String selectedService) {
        Intent myIntent = new Intent(this, activityClass);
        myIntent.putExtra("key-user", userId);
        if(ownerKey != null)
            myIntent.putExtra("key-owner",ownerKey);
        myIntent.putExtra("key-service", selectedService);
        startActivity(myIntent);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        requestPermissions();
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                mMap.setMyLocationEnabled(true);
                LatLng user_latlng = new LatLng(user_location.getLatitude(), user_location.getLongitude());
                LatLng now = googleMap.getCameraPosition().target;

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user_latlng, 12.0f));
                locationManager.removeUpdates(this);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //Company company = ds.getValue(Company.class);
                            System.out.println("adiadiadiai " + ds.getKey());
                            //String company_name_s = Objects.requireNonNull(snapshot.child("company_name").getValue()).toString();
                            //owner_first_name_s = Objects.requireNonNull(snapshot.child("owner_first_name").getValue()).toString();
                            for(DataSnapshot location: ds.child("locationList").getChildren())
                            {
                                double latitude =  (Double)location.child("location").child("latitude").getValue();
                                double longitude = (Double)location.child("location").child("longitude").getValue();
                                Marker marker_company = mMap.addMarker(new MarkerOptions()
                                        .position(new com.google.android.gms.maps.model.LatLng(latitude, longitude))
                                        .title(ds.getKey())
                                        .draggable(true));
                                com.devpro.models.Location loc = location.getValue(com.devpro.models.Location.class);
                                markerArrayList.put(marker_company, new Pair(ds.getKey(), loc));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mMap.setOnMarkerClickListener(marker -> {
                    bottomSheetDialog.show();
                    //showBottomSheetDialog();
                    Pair<String, com.devpro.models.Location> loc =  markerArrayList.get(marker);
                    title_text.setText(loc.first);
                    descr_text.setText(loc.second.getDescription());
                    address_text.setText(loc.second.getLine1() + " " + loc.second.getLine2());
                    phone_text.setText(loc.second.getPhone());
                    email_text.setText(loc.second.getEmail());

                    FirebaseDatabase.getInstance("https://devpro-c3528-default-rtdb.europe-west1.firebasedatabase.app/").getReference("companies")
                            .child(loc.first).child("locationList").child(loc.second.getOwner()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("services").exists()) {
                                //String[]array = new String[];
                                ArrayList<String> array = new ArrayList<>();
                                for (DataSnapshot service_name : snapshot.child("services").getChildren()) {
                                    array.add(Objects.requireNonNull(service_name.getValue()).toString());
                                }

                                if (array.size() > 0) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(UserHomePage.this, R.layout.list_item, array);
                                    get_service.setAdapter(adapter);
                                } else {
                                    array.add("");
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(UserHomePage.this, R.layout.list_item, array);
                                    get_service.setAdapter(adapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    select_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            bottomSheetDialog.cancel();
                            String service =  get_service.getText().toString();
                            changeActiviyFromSelect(MakeReservationActivity.class, userId, loc.second.getOwner(), service);
                        }
                    });

                    CircleImageView imageView;
                    imageView = download.findViewById(R.id.profile_image);

                    StorageReference storageRef =
                            FirebaseStorage.getInstance("gs://devpro-c3528.appspot.com/").getReference();
                    assert loc != null;
//                    System.out.println("images/" + company.getUsername() + "/" + "profile.jpeg");
                    storageRef.child("images/" + loc.first + "/" + loc.second.getOwner() + "/profile").getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                Picasso.get().load(uri).into(imageView);
                                //System.out.println("CEVA");
                            })
                            .addOnFailureListener(e -> {
                                //handle
                                System.out.println("ALTCEVA");
                            });
                    return false;
                });

//                mMap.setOnCameraMoveStartedListener(i -> {
//                    LatLng now1 = mMap.getCameraPosition().target;
//                    marker.setPosition(now1);
//                });
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


}