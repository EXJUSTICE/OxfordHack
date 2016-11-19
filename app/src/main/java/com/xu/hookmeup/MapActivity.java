package com.xu.hookmeup;

import android.content.pm.PackageManager;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//basic map activity, still needs to fetch coordinates from somewhere- DB?SharedPref?
//http://stackoverflow.com/questions/34582370/how-can-i-show-current-location-on-a-google-map-on-android-marshmallow

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap map;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()){

            buildGoogleApiClient();
            mGoogleApiClient.connect();

        }

        if (map == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap retMap) {

        map = retMap;

        setUpMap();

    }

    public void setUpMap(){

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        checkLocationPermission();
        map.setMyLocationEnabled(true);

    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mGoogleApiClient != null) {
            checkLocationPermission();
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this,"onConnected", Toast.LENGTH_SHORT).show();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //mLocationRequest.setSmallestDisplacement(0.1F);
        checkLocationPermission();

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        //remove previous current location Marker
        if (marker != null){
            marker.remove();
        }

        double dLatitude = mLastLocation.getLatitude();
        double dLongitude = mLastLocation.getLongitude();
        marker = map.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude))
                .title("My Location").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //set location and zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 10
        ));

    }

    //FOR CHRIS- CALL THIS TO MAKE MARKER AT LOCATION
    //TO USE: Find the latitude and longitude via getlatitude +getlongitude, then make a point
    //i.e. new LatLng(latitude, longitude), then call method
    public void addMarker(LatLng point){

        map.addMarker(new MarkerOptions().position(point)
                .title("Place of Interest").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    //required to check for when the user doesnt allow permission
    public static final int MY_PERMISSIONS_REQUEST_LOCATION=99;
    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            //No permission allowed, force user to give one
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        }else {
            return true;
        }

    }


}


