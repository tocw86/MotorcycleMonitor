package com.example.motorcyclemonitor.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.example.motorcyclemonitor.MainActivity;
import com.example.motorcyclemonitor.R;

import java.security.Timestamp;

public class SensorRotation implements SensorEventListener {
    static final float ALPHA = 0.25f;
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
    private float calibrateRollValue;
    private long lastUpdate;

    public SensorRotation(MainActivity mainActivity) {
        lastUpdate = System.currentTimeMillis();

        context = mainActivity;
        calibrateRollValue = 0;
        txtRoll = (TextView) context.findViewById(R.id.txtRoll);
        sensorManager = (SensorManager)  context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        );
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = lowPass(event.values.clone(), mags);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = lowPass(event.values.clone(), accels);
                break;
        }
        long actualTime = event.timestamp;

        if(actualTime - lastUpdate > 500000000) {

            if (mags != null && accels != null) {
                gravity = new float[9];
                magnetic = new float[9];
                boolean success = SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
                if (success) {
                    float[] outGravity = new float[9];
                    SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
                    SensorManager.getOrientation(outGravity, values);
                    values = lowPass(values, values);
                    azimuth = values[0] * 57.2957795f;
                    pitch =values[1] * 57.2957795f;
                    roll = values[2] * 57.2957795f;
                    if(calibrateRollValue != 0){
                        if(calibrateRollValue < 0 && roll < 0){
                            roll = roll + (calibrateRollValue * -1);
                        }else if(calibrateRollValue > 0 && roll < 0){
                            roll = roll + calibrateRollValue;
                        }else if(calibrateRollValue < 0 && roll > 0){
                            roll = roll - (calibrateRollValue * -1);
                        }else{
                            roll = roll - calibrateRollValue;
                        }
                    }
                    mags = null;
                    accels = null;
                    txtRoll.setText(String.valueOf(Math.round(roll)));
                    lastUpdate = actualTime;
                }

            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void calibrateSensors() {
        calibrateRollValue = roll;
    }

    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
}