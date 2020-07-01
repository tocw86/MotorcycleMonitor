package com.software4bikers.motorcyclerun.interval;

import android.location.Location;
import android.os.Handler;
import android.widget.ImageView;

import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;

import java.security.Timestamp;
import java.util.Collections;
import java.util.Date;

public class SqlInterval {

    public int frameRate;
    public SensorLocation sensorLocation;

    private Handler frame = new Handler();

    public SqlInterval(SensorLocation sensorLocation, int frameRate) {
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
            if(sensorLocation.hasActualLocation() && Integer.parseInt(sensorLocation.getActualSpeed()) > 0){
                Location tmpLocation = sensorLocation.getActualLocation();
                String ele = String.valueOf(tmpLocation.getAltitude());
                String speed = sensorLocation.getActualSpeed();
                String tempRoll = String.valueOf(sensorLocation.mainActivity.sensorRotation.getTempRoll());
                String lat = String.valueOf(tmpLocation.getLatitude());
                String lon = String.valueOf(tmpLocation.getLongitude());
                sensorLocation.runSessionDataModel.create(sensorLocation.mainActivity.userId, String.valueOf(sensorLocation.mainActivity.sessionId), lat, lon, ele, tempRoll, speed ,Helper.getDateTime(), Helper.getDateTime());
            }

            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void initGfx() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }
 
}
