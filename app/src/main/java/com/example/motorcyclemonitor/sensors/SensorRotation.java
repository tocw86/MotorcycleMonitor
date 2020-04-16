package com.example.motorcyclemonitor.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.example.motorcyclemonitor.MainActivity;
import com.example.motorcyclemonitor.R;

public class SensorRotation implements SensorEventListener {
    public MainActivity context;
    public SensorManager sensorManager;
    public TextView txtRoll;
    // Gravity rotational data
    private float gravity[];
    // Magnetic rotational data
    private float magnetic[]; //for magnetic rotational data
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float[] values = new float[3];

    // azimuth, pitch and roll
    private float azimuth;
    private float pitch;
    private float roll;
    public SensorRotation(MainActivity mainActivity) {
        context = mainActivity;
        txtRoll = (TextView) context.findViewById(R.id.txtRoll);
        sensorManager = (SensorManager)  context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = event.values.clone();
                break;
        }

        if (mags != null && accels != null) {
            gravity = new float[9];
            magnetic = new float[9];
            SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
            float[] outGravity = new float[9];
            SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
            SensorManager.getOrientation(outGravity, values);

            azimuth = values[0] * 57.2957795f;
            pitch =values[1] * 57.2957795f;
            roll = values[2] * 57.2957795f;
            mags = null;
            accels = null;
            txtRoll.setText(String.valueOf(Math.round(roll)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
