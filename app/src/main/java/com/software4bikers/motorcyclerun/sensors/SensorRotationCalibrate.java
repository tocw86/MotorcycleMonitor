package com.software4bikers.motorcyclerun.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import com.software4bikers.motorcyclerun.R;

import androidx.annotation.Nullable;

import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.StartActivity;
import com.software4bikers.motorcyclerun.views.GameView;

public class SensorRotationCalibrate implements SensorEventListener {

    public StartActivity startActivity;
    public TextView calibrateRollValue;
    public float roll;
    public float pitch;
    public float a = 0.1f;

    public int getRoll() {
        return (int) roll;
    }

    public void unregister() {
        mSensorManager.unregisterListener(this);
    }

    public interface Listener {
        void onOrientationChanged(float pitch, float roll);
    }

    private static final int SENSOR_DELAY_MICROS = 16 * 1000; // 16ms

    private final WindowManager mWindowManager;

    private final SensorManager mSensorManager;

    @Nullable
    private final Sensor mRotationSensor;

    private int mLastAccuracy;
    private SensorRotationCalibrate.Listener mListener;


    public SensorRotationCalibrate(StartActivity startActivity) {
        this.startActivity = startActivity;
        mWindowManager =  this.startActivity.getWindow().getWindowManager();
        this.calibrateRollValue = this.startActivity.findViewById(R.id.calibrateRollValue);
        mSensorManager = (SensorManager)  this.startActivity.getSystemService(Activity.SENSOR_SERVICE);
        // Can be null if the sensor hardware is not available
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void startListening(SensorRotationCalibrate.Listener listener) {
        if (mListener == listener) {
            return;
        }
        mListener = listener;
        if (mRotationSensor == null) {
            return;
        }
        mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY_MICROS);
    }

    public void stopListening() {
        mSensorManager.unregisterListener(this);
        mListener = null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (mLastAccuracy != accuracy) {
            mLastAccuracy = accuracy;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener == null) {
            return;
        }
        if (mLastAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        if (event.sensor == mRotationSensor) {
            if(event.values.length > 0){
                updateOrientation(event.values);
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void updateOrientation(float[] rotationVector) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

        final int worldAxisForDeviceAxisX;
        final int worldAxisForDeviceAxisY;

        // Remap the axes as if the device screen was the instrument panel,
        // and adjust the rotation matrix for the device orientation.
        switch (mWindowManager.getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
            default:
                worldAxisForDeviceAxisX = SensorManager.AXIS_X;
                worldAxisForDeviceAxisY = SensorManager.AXIS_Z;
                break;
            case Surface.ROTATION_90:
                worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;
                break;
            case Surface.ROTATION_180:
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X;
                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z;
                break;
            case Surface.ROTATION_270:
                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z;
                worldAxisForDeviceAxisY = SensorManager.AXIS_X;
                break;
        }

        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
                worldAxisForDeviceAxisY, adjustedRotationMatrix);

        // Transform rotation matrix into azimuth/pitch/roll
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        if(orientation.length > 0){
            // Convert radians to degrees

            pitch = lowPass(orientation[1] * -57, pitch);
            roll = lowPass(orientation[2] * -57, roll);

            int rollRounded = (int) roll;

            calibrateRollValue.setText(String.valueOf(rollRounded));

            mListener.onOrientationChanged(pitch, roll);
        }

    }
    public float lowPass(float current, float last){
        return  last * (1.0f - a) + current * a;
    }
}
