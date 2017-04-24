package de.uulm.livesensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toGps(View view){
        Intent intent = new Intent(this, GpsActivity.class);
        startActivity(intent);

    }

    public void toAccelerometer(View view){
        Intent intent = new Intent(this, AccelerometerActivity.class);
        startActivity(intent);
    }
}
