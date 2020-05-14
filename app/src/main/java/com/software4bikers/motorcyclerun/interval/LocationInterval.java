package com.software4bikers.motorcyclerun.interval;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.software4bikers.motorcyclerun.api.OWMApi;
import com.software4bikers.motorcyclerun.factory.RetrofitFactory;
import com.software4bikers.motorcyclerun.helpers.DistanceCalculator;
import com.software4bikers.motorcyclerun.models.BikerLocation;
import com.software4bikers.motorcyclerun.responses.GeoWeatherResponse;
import com.software4bikers.motorcyclerun.sensors.SensorLocation;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.software4bikers.motorcyclerun.R;
public class LocationInterval {

    public int frameRate;
    public double middleSpeed;
    public SensorLocation sensorLocation;
    public BikerLocation lastLocation;
    public ImageView weatherIcon;
    public TextView weatherText;
    public RelativeLayout weatherLayout;
    private Handler frame = new Handler();

    public LocationInterval(SensorLocation sensorLocation, int frameRate) {
        this.frameRate = frameRate;
        this.sensorLocation = sensorLocation;
        this.weatherIcon = sensorLocation.mainActivity.findViewById(R.id.weather_icon);
        this.weatherText = sensorLocation.mainActivity.findViewById(R.id.weatherText);
        this.weatherLayout = sensorLocation.mainActivity.findViewById(R.id.weatherLayout);
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
            boolean getWeatherFlag = false;
            //code here
            if(sensorLocation.getBikerLocation() != null){
                if(lastLocation == null){
                    getWeatherFlag = true;
                }else{
                    double bikerLat = Double.parseDouble(sensorLocation.getBikerLocation().getLat());
                    double bikerLng = Double.parseDouble(sensorLocation.getBikerLocation().getLng());
                    double lastLat = Double.parseDouble(lastLocation.getLat());
                    double lastLng = Double.parseDouble(lastLocation.getLng());
                    double distance = DistanceCalculator.distance(bikerLat, bikerLng, lastLat, lastLng, "K");

                    int formatedDist = (int) Math.round(distance);

                    //if distance is more than 10km
                    if(formatedDist > 10){
                        getWeatherFlag = true;
                    }
                }
                ConnectivityManager cm =
                        (ConnectivityManager) sensorLocation.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if(getWeatherFlag && isConnected){
                    String lat = String.valueOf(sensorLocation.getBikerLocation().getLat());
                    String lon = String.valueOf(sensorLocation.getBikerLocation().getLng());
                    Retrofit retrofit = new RetrofitFactory().getRetrofit();
                    OWMApi owmApi = retrofit.create(OWMApi.class);
                    Call<GeoWeatherResponse> call = owmApi.getGeoWeather(lat, lon, "3684486318b1d6c217c43712e17f2d89");
                    call.enqueue(new Callback<GeoWeatherResponse>() {

                        @Override
                        public void onResponse(Call<GeoWeatherResponse> call, Response<GeoWeatherResponse> response) {
                            GeoWeatherResponse.Weather weather = response.body().getFirstItem();
                            GeoWeatherResponse geoWeatherResponse = response.body();

                            if(weather != null && String.valueOf(weather.icon) != null){
                                Glide.with(sensorLocation.mainActivity).load("http://openweathermap.org/img/wn/"+weather.icon+"@2x.png").into(weatherIcon);
                                weatherIcon.setVisibility(View.VISIBLE);
                                //setPosition(weatherIcon);
                               // setLayoutPosition(weatherLayout);
                                long temp = Math.round(Double.parseDouble(geoWeatherResponse.main.temp));
                                weatherText.setText(String.valueOf(temp) + " °C");
                            }

                            lastLocation = new BikerLocation(sensorLocation.getBikerLocation().getLat(), sensorLocation.getBikerLocation().getLng());
                        }
                        @Override
                        public void onFailure(Call<GeoWeatherResponse> call, Throwable t) {
                        }
                    });
                }
            }
            frame.postDelayed(frameUpdate, frameRate);
        }
    };
private  void setLayoutPosition(RelativeLayout weatherLayout){
    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    lp.setMargins(sensorLocation.mainActivity.gameView.getWidth() - weatherIcon.getWidth(), 0, 0, 0);
    weatherLayout.setLayoutParams(lp);
}
    private void setPosition(ImageView weatherIcon) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(sensorLocation.mainActivity.gameView.getWidth() - weatherIcon.getWidth(), 0, 0, 0);
        weatherIcon.setLayoutParams(lp);
    }

    synchronized private void init() {
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, frameRate);
    }

    public double getMiddleSpeed(){
        return middleSpeed;
    }
}
