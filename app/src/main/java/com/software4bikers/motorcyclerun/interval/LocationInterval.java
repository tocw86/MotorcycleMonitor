package com.software4bikers.motorcyclerun.interval;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.software4bikers.motorcyclerun.R;
import com.software4bikers.motorcyclerun.api.OWMApi;
import com.software4bikers.motorcyclerun.factory.RetrofitFactory;
import com.software4bikers.motorcyclerun.helpers.Helper;
import com.software4bikers.motorcyclerun.responses.GeoWeather;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.provider.Settings.System.getString;

public class LocationInterval {

    public int frameRate;
    public double middleSpeed;
    public SensorLocation sensorLocation;

    private Handler frame = new Handler();

    public LocationInterval(SensorLocation sensorLocation, int frameRate) {
        this.frameRate = frameRate;
        this.sensorLocation = sensorLocation;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }


    public void start() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, frameRate);

    }

    private Runnable frameUpdate = new Runnable() {

        @Override
        synchronized public void run() {
            frame.removeCallbacks(frameUpdate);
            //code here
            Log.d("xxx", "62");
            if(sensorLocation.getBikerLocation() != null){
                String lat = String.valueOf(sensorLocation.getBikerLocation().getLat());
                String lon = String.valueOf(sensorLocation.getBikerLocation().getLng());
                Retrofit retrofit = new RetrofitFactory().getRetrofit();
                OWMApi owmApi = retrofit.create(OWMApi.class);
                Call<GeoWeather> call = owmApi.getGeoWeather(lat, lon, "3684486318b1d6c217c43712e17f2d89");
                Log.d("xxx", "69");
                call.enqueue(new Callback<GeoWeather>() {

                    @Override
                    public void onResponse(Call<GeoWeather> call, Response<GeoWeather> response) {
                        Log.d("xxx", response.toString());
                    }

                    @Override
                    public void onFailure(Call<GeoWeather> call, Throwable t) {
                        Log.d("xxx", t.toString());

                    }
                });

            }

            frame.postDelayed(frameUpdate, frameRate);
        }
    };

    synchronized private void init() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }

    public double getMiddleSpeed(){
        return middleSpeed;
    }
}
