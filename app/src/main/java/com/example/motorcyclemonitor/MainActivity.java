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
    public int animationJump = 40;
    private static final int FRAME_RATE = 500;
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

        frame.postDelayed(frameUpdate, FRAME_RATE);

    }
    private Runnable frameUpdate = new Runnable() {

        @Override

        synchronized public void run() {

            frame.removeCallbacks(frameUpdate);

            //make any updates to on screen objects here

            //then invoke the on draw by invalidating the canvas
            int newSpeed = gameView.speed;
            int newSpeed2 = gameView.speed2;
            int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);

            if(newSpeed > 120 || newSpeed2 > animationJump){
                gameView.setSpeed2(newSpeed2 + animationJump);
            }else{
                gameView.setSpeed2(animationJump);
            }

            if((newSpeed + paddingTop) >= gameView.getHeight()){
                gameView.setSpeed(animationJump);
            }else{
                gameView.setSpeed(newSpeed + animationJump);

            }
            gameView.invalidate();

            frame.postDelayed(frameUpdate, FRAME_RATE);

        }

    };
    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    public void calibrateSensors(View view) {
        sensorRotation.calibrateSensors();
    }

}
