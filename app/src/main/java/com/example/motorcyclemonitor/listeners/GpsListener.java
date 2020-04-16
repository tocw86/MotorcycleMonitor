package com.example.motorcyclemonitor.listeners;

import android.location.GpsStatus;
import android.widget.TextView;

import com.example.motorcyclemonitor.MainActivity;
import com.example.motorcyclemonitor.R;

public class GpsListener implements GpsStatus.Listener {
    public MainActivity mainActivity;
    public TextView txtGpsStatus;
    public GpsListener(MainActivity context) {
        mainActivity = context;
        txtGpsStatus = (TextView) mainActivity.findViewById(R.id.txtGpsStatus);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event){
            case GpsStatus.GPS_EVENT_STARTED:
                txtGpsStatus.setText("GPS Searching");
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                txtGpsStatus.setText("GPS Stopped");
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                txtGpsStatus.setText("GPS Locked");
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                txtGpsStatus.setText("GPS Ok");
                break;
        }
    }
}
