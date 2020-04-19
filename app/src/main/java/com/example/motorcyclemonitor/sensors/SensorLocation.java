package com.example.motorcyclemonitor.sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.motorcyclemonitor.listeners.GpsListener;
import com.example.motorcyclemonitor.models.CLocation;
import com.example.motorcyclemonitor.MainActivity;
import com.example.motorcyclemonitor.R;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class SensorLocation implements LocationListener {

    public MainActivity mainActivity;
    public LocationManager locationManager;
    public TextView txtGpsStatus;
    public TextView txtLat;
    public TextView txtLng;
    public TextView txtCurrentSpeed;
    public GpsListener gpsStatus;
    public SensorLocation(MainActivity context) {
        mainActivity = context;
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        txtGpsStatus = (TextView) mainActivity.findViewById(R.id.txtGpsStatus);
        txtLat = (TextView) mainActivity.findViewById(R.id.txtLat);
        txtLng = (TextView) mainActivity.findViewById(R.id.txtLng);
        txtCurrentSpeed = (TextView) mainActivity.findViewById(R.id.txtCurrentSpeed);

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
                txtLat.setText(String.valueOf(lastKnownLocationGPS.getLatitude()));
               txtLng.setText(String.valueOf(lastKnownLocationGPS.getLongitude()));
            } else {
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
               if(loc != null){
                   txtLat.setText(String.valueOf(loc.getLatitude()));
                   txtLng.setText(String.valueOf(loc.getLongitude()));
               }
            }
        } else {
            Log.i("xxx", "null");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("xxx", String.valueOf(80));

        txtCurrentSpeed.setText("0");

        if(location != null){
            CLocation myLocation = new CLocation(location, true);
             txtLat.setText(String.valueOf(location.getLatitude()));
            txtLng.setText(String.valueOf(location.getLongitude()));
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        txtGpsStatus.setText(String.valueOf(status));
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

     private void updateSpeed(CLocation location) {
        // TODO Auto-generated method stub
        float nCurrentSpeed = 0;

        if(location != null)
        {
            location.setUseMetricunits(true);
            nCurrentSpeed = location.getSpeed();
        }
        Log.d("xxx", String.valueOf(nCurrentSpeed));
        txtCurrentSpeed.setText(String.valueOf(Math.round(nCurrentSpeed)));
    }
}
