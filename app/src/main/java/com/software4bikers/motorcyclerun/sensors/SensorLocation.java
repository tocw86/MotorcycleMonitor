package com.software4bikers.motorcyclerun.sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.interval.LocationInterval;
import com.software4bikers.motorcyclerun.interval.SpeedInterval;
import com.software4bikers.motorcyclerun.listeners.GpsListener;
import com.software4bikers.motorcyclerun.models.BikerLocation;
import com.software4bikers.motorcyclerun.models.CLocation;
import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.R;
import com.software4bikers.motorcyclerun.sqlite.RunSessionDataModel;
import com.software4bikers.motorcyclerun.sqlite.RunSessionModel;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class SensorLocation implements LocationListener {

    public MainActivity mainActivity;
    public LocationManager locationManager;
    public TextView txtGpsStatus;
    public TextView txtCurrentSpeed;
    public GpsListener gpsStatus;
    public  ImageView pseudo3dRoad;
    public BikerLocation bikerLocation;
    public SpeedInterval speedInterval;
    public LocationInterval locationInterval;
    public int globalCurrentSpeed = 0;
    public ArrayList<Integer> speedCollection = new ArrayList<Integer>();
    public RunSessionDataModel runSessionDataModel;
    private String actualSpeed;
    public SensorLocation(MainActivity context, ImageView pseudo3dRoad, RunSessionDataModel runSessionDataModel) {
        mainActivity = context;
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        txtCurrentSpeed = mainActivity.findViewById(R.id.txtCurrentSpeed);
        this.pseudo3dRoad = pseudo3dRoad;
        this.runSessionDataModel = runSessionDataModel;

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else{
            gpsStatus = new GpsListener(mainActivity);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
            locationManager.addGpsStatusListener(gpsStatus);
            this.onLocationChanged(null);
        }

       if (locationManager != null) {
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
             String lat = String.valueOf(lastKnownLocationGPS.getLatitude());
              String lng = String.valueOf(lastKnownLocationGPS.getLongitude());
              bikerLocation = new BikerLocation(lat, lng);
            } else {
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
               if(loc != null){
                   String lat = String.valueOf(loc.getLatitude());
                   String lng = String.valueOf(loc.getLongitude());
                   bikerLocation = new BikerLocation(lat, lng);
               }
            }
        }

        speedInterval = new SpeedInterval(this, 3000);
        locationInterval = new LocationInterval(this, 3000);
        locationInterval.start();
    }


    public BikerLocation getBikerLocation() {
        return bikerLocation;
    }

    @Override
    public void onLocationChanged(Location location) {

        txtCurrentSpeed.setText("0");

        if(location != null){
            CLocation myLocation = new CLocation(location, true);
            this.updateSpeed(myLocation);
            bikerLocation = new BikerLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            String ele = String.valueOf(location.getAltitude());
            String tempRoll = String.valueOf(this.mainActivity.sensorRotation.getTempRoll());
            this.runSessionDataModel.create(this.mainActivity.userId, String.valueOf(this.mainActivity.sessionId), bikerLocation.getLat(), bikerLocation.getLng(), ele, tempRoll, this.actualSpeed ,Helper.getDateTime(), Helper.getDateTime());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void checkSpeedAndAdjustGraphics(float nCurrentSpeed){

     if(mainActivity.isDay){
        if(nCurrentSpeed == 0){
            Glide.with(mainActivity).load(R.drawable.road_pixelized_0).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 10 && nCurrentSpeed <= 25){
            Glide.with(mainActivity).load(R.drawable.road_pixelized_25).into(pseudo3dRoad);
        }else if (nCurrentSpeed > 25 && nCurrentSpeed <= 50){
            Glide.with(mainActivity).load(R.drawable.road_pixelized_50).into(pseudo3dRoad);
        }else if (nCurrentSpeed > 50 && nCurrentSpeed <= 75){
            Glide.with(mainActivity).load(R.drawable.road_pixelized_75).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 75 && nCurrentSpeed <= 100){
            Glide.with(mainActivity).load(R.drawable.road_pixelized_100).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 100){
           Glide.with(mainActivity).load(R.drawable.road_pixelized_150).into(pseudo3dRoad);
       }

     }else{
        if(nCurrentSpeed == 0){
            Glide.with(mainActivity).load(R.drawable.road_night_0).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 10 && nCurrentSpeed <= 25){
            Glide.with(mainActivity).load(R.drawable.road_night_25).into(pseudo3dRoad);
        }else if (nCurrentSpeed > 25 && nCurrentSpeed <= 50){
            Glide.with(mainActivity).load(R.drawable.road_night_50).into(pseudo3dRoad);
        }else if (nCurrentSpeed > 50 && nCurrentSpeed <= 75){
            Glide.with(mainActivity).load(R.drawable.road_night_75).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 75 && nCurrentSpeed <= 100){
            Glide.with(mainActivity).load(R.drawable.road_night_100).into(pseudo3dRoad);
        }else if(nCurrentSpeed > 100){
           Glide.with(mainActivity).load(R.drawable.road_night_150).into(pseudo3dRoad);
       }

     }   
     

    }

     private void updateSpeed(CLocation location) {
        // TODO Auto-generated method stub
        float nCurrentSpeed = 0;

        if(location != null)
        {
            location.setUseMetricunits(true);
            nCurrentSpeed = location.getSpeed();
        }
        this.checkSpeedAndAdjustGraphics(nCurrentSpeed);
        this.mainActivity.gameView.setSpeed((int) nCurrentSpeed);

        if((int) nCurrentSpeed > 0){
            this.speedCollection.add((int) nCurrentSpeed);
        }
         this.actualSpeed = String.valueOf(Math.round(nCurrentSpeed));
         this.txtCurrentSpeed.setText(this.actualSpeed);
    }

}
