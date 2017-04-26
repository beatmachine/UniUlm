package de.uulm.livesensor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static de.uulm.livesensor.R.id.home;

public class MainActivity extends AppCompatActivity {

  Button permbut;
  Button button2;
  static Context mContext;

  final int REQUEST_PERMISSIONS = 2;

  /*
  The onCreate Method in this Activity is simple. It only finds the Buttons and start a Method
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    button2 = (Button) findViewById(R.id.button2);
    permbut = (Button) findViewById(R.id.butper);
    mContext = getApplicationContext();
    requestGPS();
  }

  /*
  This method will request the GPS use from the user and will set on a Button to request it again.
   */
  public void requestGPS() {
        /*
        First 'if' is to check if permission isn't already granted
         */
    if (ContextCompat.checkSelfPermission(MainActivity.this,
        //TODO: Maybe remove ACCES_COARSE_LOCATION
        Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) +
        ContextCompat
            .checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            /*
            Second 'if' is to check if permission isn't granted also but now it will display a message from android to ask the user to give the permission
             it will also set a button visible to repeat the request until the request is granted.
             */
      if (ActivityCompat.shouldShowRequestPermissionRationale
          //TODO: Maybe remove ACCES_COARSE_LOCATION
              (MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
          ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
              Manifest.permission.ACCESS_COARSE_LOCATION) ||
          ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
              Manifest.permission.READ_EXTERNAL_STORAGE)) {
        findViewById(R.id.butper).setVisibility(View.VISIBLE);
        findViewById(R.id.butper).setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                    //TODO: Maybe remove ACCES_COARSE_LOCATION
                    new String[]{Manifest.permission
                        .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS);
              }
            });
      } else {
                /*
                Another try to get the Permission.
                 */
        ActivityCompat.requestPermissions(MainActivity.this,
            new String[]{Manifest.permission
                //TODO: Maybe remove ACCES_COARSE_LOCATION
                .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            REQUEST_PERMISSIONS);
      }
    } else {
            /*
            This Method is setting the GPS Button to Visibile if the Permission is granted.
             */
      button2.setVisibility(View.VISIBLE);
    }
  }

  /*
  This Method will be called when the Permission is granted or not. If so the GPSLocation Button will be Activated
  If not the the button will be disabled.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode,
      @NonNull String permissions[],
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case REQUEST_PERMISSIONS: {
        //TODO: Check if second permission is really necessary
        if ((grantResults.length > 0) && (grantResults[0] +
            grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
          button2.setVisibility(View.VISIBLE);

        } else {
          button2.setVisibility(View.INVISIBLE);
        }
        return;
      }
    }
  }

  /*
  Method to the GPS activity
   */
  public void toGps(View view) {
    Intent intent = new Intent(this, GpsActivity.class);
    startActivity(intent);

  }

  /*
  Method to the Accelormeter activity
   */
  public void toAccelerometer(View view) {
    Intent intent = new Intent(this, AccelerometerActivity.class);
    startActivity(intent);
  }

  public static Context getContext() {
    return mContext;
  }

}
