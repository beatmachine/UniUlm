package de.uulm.livesensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.Date;

/*
This class is for the GpsActivity it will display the longitude and latitude on the Android Device.
Created by Tim Mend at the University of Ulm.

 */
public class GpsActivity extends AppCompatActivity implements LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    OnMapReadyCallback {

  TextView longi;
  TextView lat;

  //TODO: Remove if unnecessary in the end
  private LocationManager locationManager;
  private GoogleApiClient mGoogleApiClient;
  private Location mLastLocation;
  private String mLastUpdateTime;
  private Location mCurrentLocation;
  LocationRequest mLocationRequest;
  private GoogleMap googleMap;


  /*
  This Method will instantiated all necessary Variables.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setContentView(R.layout.activity_gps);
    //The two text Viewes seen in the gui get instantiated
    longi = (TextView) findViewById(R.id.gps_long);

    lat = (TextView) findViewById(R.id.gps_lat);
    //the locationManager is getting instantiated
    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        /*
        Instantiate the GoogleApiClient for Connection Updates.
         */
    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(this)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();

    }

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

  }

  /*
  If connected to the Google Api this Method will called and start the Location Updates
   */
  @Override
  public void onConnected(Bundle connectionHint) {
    if (ActivityCompat
        .checkSelfPermission((Activity) GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }
    //TODO: Remove if unnecessary in the end
    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    startLocationUpdates();

        /*
        this will get our first location and set it to the text in the UI
         */
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (mLastLocation != null) {
      lat.setText(String.valueOf(mLastLocation.getLatitude()));
      longi.setText(String.valueOf(mLastLocation.getLongitude()));
    }


  }

  /*
  Start the Location Updates
   */
  protected void startLocationUpdates() {
    createLocationRequest();
        /*
        test like always if rights are given.
         */
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
    }
    LocationServices.FusedLocationApi
        .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  /*
  This creates a LocationRequest which will be used to request a Location in a specfic Interval,
  set the Fastest Interval which will be posible and that the Location ist highly accurate.
   */
  protected void createLocationRequest() {
    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  @Override
    /*
    The onLocationChanged Method is called when movement is detected by the System. After that
    the Location will set.
     */
  public void onLocationChanged(Location location) {
    mCurrentLocation = location;
    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
    updateUI();
  }

  /*
  Will Update the GUI and set the position of the Marker of the GoogleMap and also the Values
  of the text seen in the GUI
   */
  private void updateUI() {
    lat.setText(String.valueOf(mCurrentLocation.getLatitude()));
    longi.setText(String.valueOf(mCurrentLocation.getLongitude()));
    setPosition(this.googleMap);
  }

  /*
  This will stop the Location Updates if the Activity is out of Fokus
   */
  protected void onPause() {
    super.onPause();
    stopLocationUpdates();
  }

  /*
  Is called by onPause() to stop the Location Updates
   */
  protected void stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
  }

  /*
  Will connect to the GooleApiClient on start of the Activity
   */
  protected void onStart() {
    mGoogleApiClient.connect();
    super.onStart();
  }

  /*
  Will disconnect from the GoogleaApiClient when Activity stops
   */
  protected void onStop() {
    mGoogleApiClient.disconnect();
    super.onStop();
  }

  /*
  This Method is called from the updateUI to set the Marker of the GoogleMap
   */
  protected void setPosition(GoogleMap googleMap) {
    LatLng position = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    googleMap.addMarker(new MarkerOptions().position(position).title("You are here"));
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
  }

  //TODO: Do something
  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
  }

  /*
  This method is called by the system and will instantiate a Variable need later
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
  }
}
