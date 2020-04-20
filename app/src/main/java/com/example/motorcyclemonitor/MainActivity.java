package com.example.motorcyclemonitor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.motorcyclemonitor.sensors.SensorLocation;
import com.example.motorcyclemonitor.sensors.SensorRotation;
import com.example.motorcyclemonitor.views.CircleView;
import com.example.motorcyclemonitor.views.GameView;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public TextView gpsStatus;
    public  View rootLayout;
    private Handler frame = new Handler();
    public GameView gameView;
    public int animationJump = 60;
    public int frameRate = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        rootLayout = (View) this.findViewById(R.id.root_layout);
        gameView = (GameView) this.findViewById(R.id.gameId);
        sensorRotation = new SensorRotation(this, gameView);
        sensorLocation = new SensorLocation(this);
        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override

            public void run() {

                initGfx();

            }

        }, 1000);

    }

    synchronized public void initGfx() {


        //It's a good idea to remove any existing callbacks to keep

        //them from inadvertently stacking up.

        frame.removeCallbacks(frameUpdate);

        frame.postDelayed(frameUpdate, frameRate);

    }
    private Runnable frameUpdate = new Runnable() {

        @Override

        synchronized public void run() {

            frame.removeCallbacks(frameUpdate);

            //make any updates to on screen objects here

            //then invoke the on draw by invalidating the canvas
            int newPosY = gameView.posY;
            int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);


            if((newPosY + paddingTop) >= gameView.getHeight()){
                gameView.setPosY(animationJump);
            }else{
                gameView.setPosY(newPosY + animationJump);

            }
            gameView.invalidate();

            frame.postDelayed(frameUpdate, frameRate);

        }

    };

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
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
