package com.example.safestreets;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SafeStreetMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String TAG = "SafeStreetMapActivity";
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 4321;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private ArrayList<Marker> SafeStreetsMarker = new ArrayList<>();

    int AUTOCOMPLETE_REQUEST_CODE = 1;

    //widgets
    private EditText mSearchText;
    private ImageView mGps;
    private ImageView mAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_safe_street_map);
        mSearchText = (EditText) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        mAutoComplete = (ImageView) findViewById(R.id.ic_autoComplete);
        getLocationPermission();




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    private void init(){
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == keyEvent.KEYCODE_ENTER){

                    //search method execute
                    geoLocate();

                }
                return false;
            }
        });


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_API_key), Locale.ENGLISH);
        }



        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gps icon clicked");
                getDeviceLocation();
            }
        });

        mAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: autocomplete list icon clicked");
                autocompleteIntent();
            }
        });



        hideSoftKeyboard();

    }


    //@Override
    private boolean onAutocompleteSelected(MenuItem item) {
        //super.onAutocompleteSelected(item);
        switch (item.getItemId()) {
            case R.id.ic_autoComplete:
                autocompleteIntent();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void autocompleteIntent(){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                String searchPlace = place.getName();

                Toast.makeText(SafeStreetMap.this, "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();

                Geocoder geocoder = new Geocoder(SafeStreetMap.this);
                List<Address> list = new ArrayList<>();

                try{
                    list = geocoder.getFromLocationName(searchPlace, 1);
                }
                catch (IOException e){
                    Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                }

                Address address = list.get(0);

                moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                        address.getAddressLine(0));

            }

            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());

                Toast.makeText(SafeStreetMap.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }

            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private void geoLocate() {
        Log.d(TAG, "geoLocate: geoLocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(SafeStreetMap.this);
        List<Address> list = new ArrayList<>();

        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }
        catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if (list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }


    /*    private void getAutoCompleteResult(){
            final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

            autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

            autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    final LatLng latLng = place.getLatLng();

                    Log.i(TAG, "onPlaceSelected: " + latLng.latitude + "\n" + latLng.longitude);
                }

                @Override
                public void onError(@NonNull Status status) {
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }
    */
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: retrieving current devices location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Location Found");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()), DEFAULT_ZOOM,
                                    "My Location");
                        }
                        else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(SafeStreetMap.this, "Unable to find your location",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }

    }

    private void moveCamera(LatLng LatLng, float zoom, String title){
        Log.d(TAG, "moveCamera: Moving to " + LatLng.latitude + "," + LatLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng, zoom));

        if(!title.equals("My Location")){
            //clearMarkers();
            //MarkerOptions
            mMap.clear();
            MarkerOptions options = new MarkerOptions()
                    .position(LatLng)
                    .title(title);
            mMap.addMarker(options);
        }


    }

    /*public void clearMarkers(){
      for (Marker marker: SafeStreetsMarker){
            marker.remove();
          }
    }

     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");

        if (mLocationPermissionGranted){
            getDeviceLocation();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(false);

            init();
        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney=new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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


    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: Retrieving location permissions");
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permissions granted");
                    mLocationPermissionGranted = true;
                    initMap();

                }

        }
    }


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
