package com.software4bikers.motorcyclerun.listeners;

import android.location.GpsStatus;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.R;

public class GpsListener implements GpsStatus.Listener {
    public MainActivity mainActivity;
    public ImageView gpsStatusView;
    public TextView txtGpsStatus;
    public GpsListener(MainActivity context) {
        mainActivity = context;
        gpsStatusView = (ImageView) mainActivity.findViewById(R.id.gpsStatusView);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event){
            case GpsStatus.GPS_EVENT_STARTED:
                gpsStatusView.setImageResource(R.drawable.gps_status_circle_yellow);
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                gpsStatusView.setImageResource(R.drawable.gps_status_circle_red);
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                gpsStatusView.setImageResource(R.drawable.gps_status_cirlce_green);
                break;
        }

        gpsStatusView.invalidate();
        gpsStatusView.requestLayout();
    }
}
