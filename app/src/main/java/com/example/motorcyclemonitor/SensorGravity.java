package com.example.motorcyclemonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class SensorGravity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometr;
    public Context context;
    public TextView txtGravityX;
    public TextView txtGravityY;
    public TextView txtGravityZ;
    public boolean sensorExists;

    public SensorGravity(MainActivity context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            accelerometr = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            sensorManager.registerListener(this, accelerometr, SensorManager.SENSOR_DELAY_NORMAL);
            txtGravityX = (TextView) context.findViewById(R.id.txtGravityX);
            txtGravityY = (TextView) context.findViewById(R.id.txtGravityY);
            txtGravityZ = (TextView) context.findViewById(R.id.txtGravityZ);
            sensorExists = true;
        } else {
            sensorExists = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(sensorExists == true){
            txtGravityX.setText(Float.toString(event.values[0]));
            txtGravityY.setText(Float.toString(event.values[1]));
            txtGravityZ.setText(Float.toString(event.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
