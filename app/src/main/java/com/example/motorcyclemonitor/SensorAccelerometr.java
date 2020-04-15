package com.example.motorcyclemonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class SensorAccelerometr implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometr;
    public Context context;
    public TextView txtAccelerometrX;

    public boolean sensorExists;

    public SensorAccelerometr(MainActivity context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometr = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometr, SensorManager.SENSOR_DELAY_NORMAL);
            txtAccelerometrX = (TextView) context.findViewById(R.id.txtAccelerometrX);
            sensorExists = true;
        } else {
            sensorExists = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        txtAccelerometrX.setText(Float.toString(accelationSquareRoot));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
