package com.software4bikers.motorcyclerun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.software4bikers.motorcyclerun.sensors.SensorRotation;
import com.software4bikers.motorcyclerun.sensors.SensorRotationCalibrate;

public class StartActivity extends AppCompatActivity implements SensorRotationCalibrate.Listener {
    int PERMISSION_ALL = 1;
    private int calibrateValue = 0;
    public SensorRotationCalibrate sensorRotation;
    boolean startApp = false;
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
        setContentView(R.layout.activity_start);

        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) > 22) {
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                startApp = true;
            } else {
                startApp = true;
            }
        } else {
            startApp = true;
        }

        sensorRotation = new SensorRotationCalibrate(this);
        sensorRotation.startListening(this);
    }

    public void startMainActivity(View view) {
        Log.d("xxxx", String.valueOf(startApp));
        if(startApp){
            sensorRotation.unregister();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("calibrateValue", String.valueOf(this.calibrateValue));
            startActivity(intent);
            finish();
            return;
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

    public void exitApplication(View view) {
        StartActivity.this.finish();
        System.exit(0);
    }

    public void setCalibrate(View view) {
        this.calibrateValue = sensorRotation.getRoll();
        Toast.makeText(this, "Calibrated", Toast.LENGTH_LONG).show();;
    }

    @Override
    public void onOrientationChanged(float pitch, float roll) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}