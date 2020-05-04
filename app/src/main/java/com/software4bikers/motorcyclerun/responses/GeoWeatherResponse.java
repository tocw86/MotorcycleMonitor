package com.software4bikers.motorcyclerun.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeoWeatherResponse {
//{"coord":{"lon":20.58,"lat":53.73},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],
// "base":"stations",
// "main":{"temp":9,"feels_like":6.66,"temp_min":9,"temp_max":9,"pressure":1014,"humidity":81,"sea_level":1014,"grnd_level":1000},
// "wind":{"speed":2,"deg":159},"rain":{"1h":0.22},"clouds":{"all":63},"dt":1588619613,
// "sys":{"country":"PL","sunrise":1588560927,"sunset":1588615999},"timezone":7200,"id":6619358,
// "name":"Powiat olsztyński","cod":200}

    public List<Weather> weather = new ArrayList<Weather>();
    public Coord coord;
    public String base;
    public Main main;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public String dt;
    public String timezone;
    public String id;
    public String name;
    public String cod;

    public Weather getFirstItem(){
        return weather.get(0);
    }

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Coord {
        public String lon;
        public String lat;
    }

    public class Main {
        public String temp;
        public String feels_like;
        public String temp_min;
        public String temp_max;
        public String pressure;
        public String humidity;
        public String sea_level;
        public String grnd_level;
    }
    public class Wind {
        public String speed;
        public String deg;
    }
    public class Rain{
        @SerializedName("1h")
        @Expose
        public String oneHour;
    }
    public class Clouds {
        public String all;
    }

    public class Sys {
      public String country;
      public String sunrise;
      public String sunset;
    }
}
