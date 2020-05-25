package com.software4bikers.motorcyclerun.interval;

import android.os.Handler;
import android.widget.TextView;

import com.software4bikers.motorcyclerun.MainActivity;
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
    public MainActivity mainActivity;

    public LightInterval(int frameRate, MainActivity mainActivity) {
        this.frameRate = frameRate;
        this.luxCollection = new ArrayList<Integer>();
        this.mainActivity = mainActivity;

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
                mainActivity.txtLux.setText(String.valueOf(mediana));
                luxCollection = new ArrayList<Integer>();
            }
            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void initGfx() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }


}