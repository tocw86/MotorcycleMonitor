package com.software4bikers.motorcyclerun.interval;

import android.os.Handler;
import android.widget.ImageView;

import com.software4bikers.motorcyclerun.sensors.SensorLocation;

import java.security.Timestamp;
import java.util.Date;

public class SpeedInterval {

    public int frameRate;
    public SensorLocation sensorLocation;

    private Handler frame = new Handler();

    public SpeedInterval(SensorLocation sensorLocation, int frameRate) {
        this.frameRate = frameRate;
        this.sensorLocation = sensorLocation;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }


    public void start() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGfx();
            }
        }, frameRate);

    }

    private Runnable frameUpdate = new Runnable() {

        @Override
        synchronized public void run() {
            frame.removeCallbacks(frameUpdate);
            //code here

            long actualTimestamp = System.currentTimeMillis() / 1000;
            if(sensorLocation.globalCurrentSpeed > 0){

            }
            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void initGfx() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }
}
