package com.software4bikers.motorcyclerun.api;

import com.software4bikers.motorcyclerun.responses.GeoWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OWMApi {
    @GET("/data/2.5/weather?lang=en&units=metric")
    @Headers({
            "Content-Type: application/json",
    })
    Call<GeoWeatherResponse> getGeoWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appId") String appId);
}
