package com.software4bikers.motorcyclerun.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.interval.LightInterval;

public class SensorLight implements SensorEventListener {
    public MainActivity context;
    public SensorManager sensorManager;
    public TextView txtLux;
    LightInterval lightInterval;

    public SensorLight(MainActivity mainActivity, TextView txtLux) {
        this.context = mainActivity;
        this.txtLux = txtLux;
        lightInterval = new LightInterval(3000, this.txtLux);
        sensorManager = (SensorManager)  context.getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        if(lightSensor != null){
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            lightInterval.luxCollection.add(Math.round(sensorEvent.values[0]));
            txtLux.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
