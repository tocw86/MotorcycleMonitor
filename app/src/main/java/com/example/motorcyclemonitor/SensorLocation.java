package com.example.motorcyclemonitor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.Formatter;
import java.util.Locale;

public class SensorLocation implements LocationListener {

    public Context context;
    public LocationManager locationManager;
    public TextView gpsStatus;
    public TextView txtLat;
    public TextView txtLng;
    public TextView txtCurrentSpeed;

    public SensorLocation(MainActivity context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = (TextView) context.findViewById(R.id.txtGpsStatus);
        txtLat = (TextView) context.findViewById(R.id.txtLat);
        txtLng = (TextView) context.findViewById(R.id.txtLng);
        txtCurrentSpeed = (TextView) context.findViewById(R.id.txtCurrentSpeed);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
        if(location != null){
            Log.i("xxx", "68");

            txtLat.setText(String.valueOf(location.getLatitude()));
            txtLng.setText(String.valueOf(location.getLongitude()));
            CLocation myLocation = new CLocation(location, true);
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        gpsStatus.setText(String.valueOf(status));

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
            if(nCurrentSpeed > 0){
                nCurrentSpeed = (float) (nCurrentSpeed * 3.6);
            }
        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(' ', '0');

        String strUnits = "km/h";
        txtCurrentSpeed.setText(strCurrentSpeed + " " + strUnits);
    }
}
