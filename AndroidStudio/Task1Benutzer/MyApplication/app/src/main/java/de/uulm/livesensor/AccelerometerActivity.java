package de.uulm.livesensor;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    EditText xview;
    EditText yview;
    EditText zview;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0 ;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        xview = (EditText) findViewById(R.id.numx);
        yview = (EditText) findViewById(R.id.numy);
        zview = (EditText) findViewById(R.id.numz);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("permission", Integer.toString(permissionCheck));

    }
    @Override
    public void onSensorChanged(SensorEvent event){
        Sensor mySensor = event.sensor;

        if(mySensor.getType () == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/diffTime *10000;
                if(speed > SHAKE_THRESHOLD){

                }
                last_x = x;
                last_y = y;
                last_z = z;
                xview.setText(Float.toString(x));
                yview.setText(Float.toString(y));
                zview.setText(Float.toString(z));
            }

        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    protected void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
