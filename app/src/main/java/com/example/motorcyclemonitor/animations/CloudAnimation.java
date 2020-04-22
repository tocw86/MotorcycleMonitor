package com.example.motorcyclemonitor.animations;

import android.os.Handler;
import android.widget.ImageView;

public class CloudAnimation {
    public int frameRate;
    public int left = 0;
    public int top = 0;
    public int right = 0;
    public int bottom= 0;
    public int posYChange = 0;
    public boolean moveCloudVector = false;
    public ImageView cloudView;
    private Handler frame = new Handler();

    public CloudAnimation(ImageView cloudView, int frameRate) {
        this.frameRate = frameRate;
        this.cloudView = cloudView;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setPosYChange(int posYChange) {
        this.posYChange = posYChange;
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
            if(moveCloudVector){
                cloudView.setPadding(left,  top + posYChange, right, bottom);
            }else{
                cloudView.setPadding(left,  top, right, bottom);
            }
            moveCloudVector = !moveCloudVector;
            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void initGfx() {

        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);

    }
}
