package com.example.motorcyclemonitor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.motorcyclemonitor.sensors.SensorLocation;
import com.example.motorcyclemonitor.sensors.SensorRotation;
import com.example.motorcyclemonitor.views.CircleView;
import com.example.motorcyclemonitor.views.GameView;

import java.util.Random;

import io.sentry.core.Sentry;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public TextView gpsStatus;
    public View rootLayout;
    private Handler frame = new Handler();
    public GameView gameView;
    public int animationJump;
    public int frameRate = 10000;
    public int gameHeight;
    public ImageView pseudo3dRoad;
    public ImageView bgDaylight;
    public ImageView cloud3View;
    public ImageView cloud1View;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        if(Integer.valueOf(android.os.Build.VERSION.SDK_INT) > 22){
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }else{
                startApp();
            }
        }else{
            startApp();
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void startApp(){
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        gpsStatus = (TextView) this.findViewById(R.id.txtGpsStatus);
        rootLayout = (View) this.findViewById(R.id.root_layout);
        gameView = (GameView) this.findViewById(R.id.gameId);
        pseudo3dRoad = (ImageView) this.findViewById(R.id.pseudo3dRoad);
        sensorRotation = new SensorRotation(this, gameView);
        sensorLocation = new SensorLocation(this, pseudo3dRoad);
        cloud3View = (ImageView) this.findViewById(R.id.cloud3);
        cloud1View = (ImageView) this.findViewById(R.id.cloud1);
        bgDaylight = (ImageView) this.findViewById(R.id.bg_daylight);
        /*load from raw folder*/
        pseudo3dRoad.setBottom(gameView.getHeight());
        Glide.with(this).load(R.drawable.road_pixelized_0).into(pseudo3dRoad);
        gameHeight = gameView.getHeight();
        animationJump = gameView.animationJump;
        Handler h = new Handler();
        bgDaylight = (ImageView) this.findViewById(R.id.bg_daylight);
         h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(bgDaylight.getPaddingBottom() == 0){
                    bgDaylight.setPadding(0,0,0,pseudo3dRoad.getHeight());
                }
                if(cloud3View.getPaddingTop() == 0){
                    cloud3View.setPadding((int) (gameView.getWidth() * 0.7), gameView.getHeight() / 3, 0, 0);
                    cloud3View.setVisibility(View.VISIBLE);
                }
                if(cloud1View.getPaddingTop() == 0){
                    cloud1View.setPadding(-25, 50, 0, 0);
                    cloud1View.setVisibility(View.VISIBLE);
                }
                initGfx();
            }
        }, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startApp();
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


            if ((newPosY + paddingTop) >= gameView.getHeight()) {
                gameView.setPosY(animationJump);
            } else {
                gameView.setPosY(newPosY + animationJump);

            }
            gameView.invalidate();
            frame.postDelayed(frameUpdate, frameRate);
        }

    };

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public void finish() {
        super.finish();
        System.exit(0);
    }

    public void calibrateSensors(View view) {
        sensorRotation.calibrateSensors();
    }
 }
