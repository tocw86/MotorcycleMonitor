package com.example.motorcyclemonitor;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.example.motorcyclemonitor.sensors.SensorLocation;
import com.example.motorcyclemonitor.sensors.SensorRotation;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public TextView gpsStatus;
    public TextView txtLat;
    public TextView txtLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        txtLat = (TextView) this.findViewById(R.id.txtLat);
        txtLng = (TextView) this.findViewById(R.id.txtLng);
        //sensorGravity = new SensorGravity(this);
        //sensorAccelerometr = new SensorAccelerometr(this);
        sensorRotation = new SensorRotation(this);
        sensorLocation = new SensorLocation(this);
    }
    public void finish()
    {
        super.finish();
        System.exit(0);
    }
}
