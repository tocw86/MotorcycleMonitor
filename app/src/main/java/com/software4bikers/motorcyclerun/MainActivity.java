package com.software4bikers.motorcyclerun;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import com.software4bikers.motorcyclerun.animations.CloudAnimation;
import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.sensors.SensorLight;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;
import com.software4bikers.motorcyclerun.sensors.SensorRotation;
import com.software4bikers.motorcyclerun.views.GameView;

public class MainActivity extends Activity {
    public SensorRotation sensorRotation;
    public SensorLocation sensorLocation;
    public SensorLight sensorLight;
    public View rootLayout;
    public GameView gameView;
    public int gameHeight;
    public ImageView pseudo3dRoad;
    public ImageView bgDaylight;
    public ImageView cloud3View;
    public ImageView cloud1View;
    public ImageView angleView;
    public TextView txtLux;
    public TextView txtCurrentSpeed;
    public TextView txtSpeedLabel;
    public TextView txtRoll;
    public boolean isDay = true;
    public int cloud3PosY;
    public int cloud3PosX;
    public int cloud1PosY;
    public int cloud1PosX;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startApp();
    }

    public void startGfx(Handler h) {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
               setUpGfx();
            }
        }, 1000);

    }

    public void startApp() {
        txtLux = this.findViewById(R.id.txtLux);
        rootLayout = this.findViewById(R.id.root_layout);
        gameView = this.findViewById(R.id.gameId);
        pseudo3dRoad = this.findViewById(R.id.pseudo3dRoad);
        sensorRotation = new SensorRotation(this, gameView);
        sensorLocation = new SensorLocation(this, pseudo3dRoad);
        sensorLight = new SensorLight(this, txtLux);
        cloud3View = this.findViewById(R.id.cloud3);
        cloud1View = this.findViewById(R.id.cloud1);
        bgDaylight = this.findViewById(R.id.bg_daylight);
        txtCurrentSpeed = this.findViewById(R.id.txtCurrentSpeed);
        txtSpeedLabel = this.findViewById(R.id.txtSpeedLabel);
        angleView = this.findViewById(R.id.angleView);
        txtRoll = this.findViewById(R.id.txtRoll);
        /*load from raw folder*/

        pseudo3dRoad.setBottom(gameView.getHeight());
        gameHeight = gameView.getHeight();
        cloud1Animation = new CloudAnimation(cloud1View, 4000);
        cloud2Animation = new CloudAnimation(cloud3View, 3000);
        startGfx(new Handler());
    }

    private void setUpGfx(){
        if (this.bgDaylight.getPaddingBottom() == 0) {
            this.bgDaylight.setPadding(0, 0, 0, this.pseudo3dRoad.getHeight());
        }
        if (this.cloud3View.getPaddingTop() == 0) {
            this.cloud3PosY = this.gameView.getHeight() / 3;
            this.cloud3PosX = (int) (this.gameView.getWidth() * 0.7);
            this.cloud3View.setPadding(this.cloud3PosX, this.cloud3PosY, 0, 0);
            this.cloud3View.setVisibility(View.VISIBLE);

            this.cloud2Animation.setLeft(this.cloud3PosX);
            this.cloud2Animation.setTop(this.cloud3PosY);
            this.cloud2Animation.setPosYChange(10);
            this.cloud2Animation.start();

        }
        if (this.cloud1View.getPaddingTop() == 0) {
            this.cloud1PosY = 140;
            this.cloud1PosX = -25;
            this.cloud1View.setPadding(this.cloud1PosX, this.cloud1PosY, 0, 0);
            this.cloud1View.setVisibility(View.VISIBLE);

            this.cloud1Animation.setLeft(this.cloud1PosX);
            this.cloud1Animation.setTop(this.cloud1PosY);
            this.cloud1Animation.setPosYChange(5);
            this.cloud1Animation.start();
        }

    }

    public void finish() {
        super.finish();
        System.exit(0);
    }

    public void calibrateSensors(View view) {
        sensorRotation.calibrateSensors();
    }
}
