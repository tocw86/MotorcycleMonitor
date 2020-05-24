package com.software4bikers.motorcyclerun.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import com.software4bikers.motorcyclerun.R;

import androidx.annotation.Nullable;

import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.views.GameView;

import static java.lang.StrictMath.asin;
import static java.lang.StrictMath.atan2;

public class SensorRotation implements SensorEventListener {

    public MainActivity mainActivity;
    public GameView gameView;
    public TextView txtRoll;
    public float roll;
    public float pitch;
    public float a = 0.1f;
    public interface Listener {
        void onOrientationChanged(float pitch, float roll);
    }

    private static final int SENSOR_DELAY_MICROS = 16 * 1000; // 16ms

    private final WindowManager mWindowManager;

    private final SensorManager mSensorManager;

    @Nullable
    private final Sensor mRotationSensor;

    private int mLastAccuracy;
    private Listener mListener;

    public SensorRotation(MainActivity mainActivity, GameView gameView) {
        mWindowManager = mainActivity.getWindow().getWindowManager();
        mSensorManager = (SensorManager) mainActivity.getSystemService(Activity.SENSOR_SERVICE);
        this.gameView = gameView;
        this.txtRoll = mainActivity.findViewById(R.id.txtRoll);
        // Can be null if the sensor hardware is not available
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void startListening(Listener listener) {
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
            updateOrientation(event.values);
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

        // Convert radians to degrees

        pitch = lowPass(orientation[1] * -57, pitch);
        roll = lowPass(orientation[2] * -57, roll);

        Log.d("xxxpitch", String.valueOf(pitch));

        if(pitch <= -60){
            roll = 0;
        }

        if(roll != 0 && (roll <= -5 || roll >= 5 )){
            gameView.setRoll((int) roll);
            txtRoll.setText(parseRoll(roll));
        }else{
            gameView.setRoll(0);
            txtRoll.setText("0° N");
        }

        mListener.onOrientationChanged(pitch, roll);
    }
    public float lowPass(float current, float last){
        return  last * (1.0f - a) + current * a;
    }

    private String parseRoll(float roll) {
        String side;
        int tmpRoll = (int) roll;
        if(tmpRoll == 0){
            side = "";
        } else if(tmpRoll < 0){
            side = "R";
            tmpRoll = tmpRoll * -1;
        }else{
            side = "L";
        }
        return tmpRoll + "°" + " " + side;
    }
}
