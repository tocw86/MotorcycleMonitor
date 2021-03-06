package com.example.motorcyclemonitor;

import java.util.Formatter;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    public SensorGravity sensorGravity;
    public SensorAccelerometr sensorAccelerometr;
    public SensorLocation sensorLocation;
    public TextView gpsStatus;
    public TextView txtLat;
    public TextView txtLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        txtLat = (TextView) this.findViewById(R.id.txtLat);
        txtLng = (TextView) this.findViewById(R.id.txtLng);
        sensorGravity = new SensorGravity(this);
        sensorAccelerometr = new SensorAccelerometr(this);
        sensorLocation = new SensorLocation(this);

        /*LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            this.updateSpeed(null);
            return;
        }

        if(locationManager != null){
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocationGPS != null){
                Log.d("xxx", String.valueOf(lastKnownLocationGPS.getAltitude()));
                gpsStatus.setText(lastKnownLocationGPS.toString());
            }else{
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                Log.d("xxx", String.valueOf(loc.getAltitude()));
                gpsStatus.setText(loc.toString());
            }

        }
*/
    }

    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    /*private void updateSpeed(CLocation location) {
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

        TextView txtCurrentSpeed = (TextView) this.findViewById(R.id.txtCurrentSpeed);
        txtCurrentSpeed.setText(strCurrentSpeed + " " + strUnits);
    }*/

   /* @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if(location != null)
        {
            CLocation myLocation = new CLocation(location, true);
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

        //gpsStatus.setText(String.valueOf(status));

    }

    @Override
    public void onGpsStatusChanged(int event) {
        // TODO Auto-generated method stub

       // gpsStatus.setText(String.valueOf(event));
    }*/



}
