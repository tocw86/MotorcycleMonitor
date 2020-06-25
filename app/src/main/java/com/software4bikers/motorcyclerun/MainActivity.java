package com.software4bikers.motorcyclerun;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import com.software4bikers.motorcyclerun.animations.CloudAnimation;
import com.software4bikers.motorcyclerun.app.App;
import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.sensors.SensorRotation;
import com.software4bikers.motorcyclerun.sensors.SensorLight;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;
import com.software4bikers.motorcyclerun.sqlite.RunSessionDataModel;
import com.software4bikers.motorcyclerun.sqlite.RunSessionModel;
import com.software4bikers.motorcyclerun.views.GameView;

public class MainActivity extends Activity implements SensorRotation.Listener {
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
    public TextView weatherText;
    public boolean isDay = true;
    public int cloud3PosY;
    public int cloud3PosX;
    public int cloud1PosY;
    public int cloud1PosX;
    public long sessionId;
    public String userId;
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
        startApp();
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
        this.userId = ((App) getApplication()).getUserMenager().getUserId();

        if(!this.userId.isEmpty()){
            RunSessionModel runSessionModel = new RunSessionModel(this);
            this.sessionId = runSessionModel.create(userId, Helper.getDateTime(), Helper.getDateTime());
        }

        RunSessionDataModel runSessionDataModel = new RunSessionDataModel(this);

        txtLux = this.findViewById(R.id.txtLux);
        weatherText = this.findViewById(R.id.weatherText);
        rootLayout = this.findViewById(R.id.root_layout);
        gameView = this.findViewById(R.id.gameId);
        pseudo3dRoad = this.findViewById(R.id.pseudo3dRoad);
        //sensorRotation = new SensorRotation(this, gameView);

        Bundle extras = getIntent().getExtras();
        String calibrateValueString =  extras.getString("calibrateValue");

        sensorLocation = new SensorLocation(this, pseudo3dRoad, runSessionDataModel);
        sensorRotation = new SensorRotation(this, gameView);
        if(calibrateValueString != null && !calibrateValueString.isEmpty()){
            Log.d("zzzz", calibrateValueString);

            sensorRotation.setCalibrateValue(Integer.parseInt(calibrateValueString));
        }
        sensorRotation.startListening(this);
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
        Glide.with(this).load(R.drawable.road_pixelized_0).into(pseudo3dRoad);
        gameHeight = gameView.getHeight();
        cloud1Animation = new CloudAnimation(cloud1View, 4000);
        cloud2Animation = new CloudAnimation(cloud3View, 3000);
        bgDaylight = this.findViewById(R.id.bg_daylight);
        startGfx(new Handler());

    }

    private void setUpGfx(){
        if (this.bgDaylight.getPaddingBottom() == 0) {
            this.bgDaylight.setPadding(0, 0, 0, this.pseudo3dRoad.getHeight() - 10);
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

        Helper.themeRefresh(this);


    }

    public void finish() {
        super.finish();
        System.exit(0);
    }
    @Override
    public void onOrientationChanged(float pitch, float roll) {

    }
}
