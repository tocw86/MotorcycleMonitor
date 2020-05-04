package com.software4bikers.motorcyclerun.api;

import com.software4bikers.motorcyclerun.models.CityWeather;
import com.software4bikers.motorcyclerun.responses.GeoWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface OWMApi {
    @GET("api.openweathermap.org/data/2.5/weather?q={city}&appid={appId}&units=metric")
    @Headers({
            "Content-Type: application/json",
    })
    Call<CityWeather> getCityWeather(@Path("city") String city, @Path("appId") String appId);

    @GET("api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={appId}&lang=en&units=metric")
    @Headers({
            "Content-Type: application/json",
    })
    Call<GeoWeather> getGeoWeather(@Path("lat") String lat, @Path("lon") String lon, @Path("appId") String appId);
}
