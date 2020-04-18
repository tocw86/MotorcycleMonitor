package com.example.motorcyclemonitor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.motorcyclemonitor.sensors.SensorLocation;
import com.example.motorcyclemonitor.sensors.SensorRotation;
import com.example.motorcyclemonitor.views.CircleView;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public TextView gpsStatus;
    public TextView txtLat;
    public TextView txtLng;
    public ImageView imageView;
    public CircleView circleView;
    public  View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        rootLayout = (View) this.findViewById(R.id.root_layout);


        //rootLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.main_bg_sunset));
        //txtLat = (TextView) this.findViewById(R.id.txtLat);
        //txtLng = (TextView) this.findViewById(R.id.txtLng);
        //sensorGravity = new SensorGravity(this);
        //sensorAccelerometr = new SensorAccelerometr(this);

        View cv = (View) this.findViewById(R.id.cirlce_layout);
        CircleView xm = (CircleView) cv.findViewById(R.id.cvView);

        sensorRotation = new SensorRotation(this, xm);
        sensorLocation = new SensorLocation(this);



    }
    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    public void calibrateSensors(View view) {
        sensorRotation.calibrateSensors();
    }

}
