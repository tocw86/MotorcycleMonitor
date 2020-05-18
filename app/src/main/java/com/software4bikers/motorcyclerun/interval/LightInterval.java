package com.software4bikers.motorcyclerun.interval;

import android.os.Handler;
import android.widget.TextView;

import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LightInterval {
    public int frameRate;
    public List<Integer> luxCollection;
    public TextView txtLux;
    private Handler frame = new Handler();

    public LightInterval(int frameRate, TextView txtLux) {
        this.frameRate = frameRate;
        this.txtLux = txtLux;
        this.luxCollection = new ArrayList<Integer>();
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

            if(luxCollection.size() > 0){
                double mediana = Helper.median(luxCollection);
                txtLux.setText(String.valueOf(mediana));
            }
            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void initGfx() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }


}
