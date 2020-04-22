package com.example.motorcyclemonitor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import android.view.View;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import com.example.motorcyclemonitor.animations.CloudAnimation;
import com.example.motorcyclemonitor.sensors.SensorLocation;
import com.example.motorcyclemonitor.sensors.SensorRotation;
import com.example.motorcyclemonitor.views.GameView;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public View rootLayout;
    public GameView gameView;
    public int gameHeight;
    public ImageView pseudo3dRoad;
    public ImageView bgDaylight;
    public ImageView cloud3View;
    public ImageView cloud1View;
    int cloud3PosY;
    int cloud3PosX;

    int cloud1PosY;
    int cloud1PosX;

    public CloudAnimation cloud1Animation;
    public CloudAnimation cloud2Animation;

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

        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) > 22) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            } else {
                startApp();
            }
        } else {
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

    public void startApp() {
        rootLayout = this.findViewById(R.id.root_layout);
        gameView = this.findViewById(R.id.gameId);
        pseudo3dRoad = this.findViewById(R.id.pseudo3dRoad);
        sensorRotation = new SensorRotation(this, gameView);
        sensorLocation = new SensorLocation(this, pseudo3dRoad);
        cloud3View = this.findViewById(R.id.cloud3);
        cloud1View = this.findViewById(R.id.cloud1);
        bgDaylight = this.findViewById(R.id.bg_daylight);
        /*load from raw folder*/
        pseudo3dRoad.setBottom(gameView.getHeight());
        Glide.with(this).load(R.drawable.road_pixelized_0).into(pseudo3dRoad);
        gameHeight = gameView.getHeight();
        cloud1Animation = new CloudAnimation(cloud1View, 4000);
        cloud2Animation = new CloudAnimation(cloud3View, 3000);
        bgDaylight = this.findViewById(R.id.bg_daylight);
        startGfx(new Handler());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startApp();
    }

    public void startGfx(Handler h) {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bgDaylight.getPaddingBottom() == 0) {
                    bgDaylight.setPadding(0, 0, 0, pseudo3dRoad.getHeight());
                }
                if (cloud3View.getPaddingTop() == 0) {
                    cloud3PosY = gameView.getHeight() / 3;
                    cloud3PosX = (int) (gameView.getWidth() * 0.7);
                    cloud3View.setPadding(cloud3PosX, cloud3PosY, 0, 0);
                    cloud3View.setVisibility(View.VISIBLE);

                    cloud2Animation.setLeft(cloud3PosX);
                    cloud2Animation.setTop(cloud3PosY);
                    cloud2Animation.setPosYChange(10);
                    cloud2Animation.start();

                }
                if (cloud1View.getPaddingTop() == 0) {
                    cloud1PosY = 140;
                    cloud1PosX = -25;
                    cloud1View.setPadding(cloud1PosX, cloud1PosY, 0, 0);
                    cloud1View.setVisibility(View.VISIBLE);

                    cloud1Animation.setLeft(cloud1PosX);
                    cloud1Animation.setTop(cloud1PosY);
                    cloud1Animation.setPosYChange(5);
                    cloud1Animation.start();

                }
            }
        }, 1000);

    }


    public void finish() {
        super.finish();
        System.exit(0);
    }

    public void calibrateSensors(View view) {
        sensorRotation.calibrateSensors();
    }
}
