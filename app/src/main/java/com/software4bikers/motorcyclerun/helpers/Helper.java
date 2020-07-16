package com.software4bikers.motorcyclerun.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Helper {

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void store(MainActivity mainActivity, int modePrivate, String key, String content) {
        SharedPreferences sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, content);
        editor.commit();
    }

    public static String read(MainActivity mainActivity, int modePrivate, String dane) {
        SharedPreferences sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("dane", "");
    }

    public static double median(List<Integer> values) {
        return (values.get(values.size() / 2) + values.get(values.size() / 2 - 1)) / 2;
    }

    public static double getMedian(List<Integer> numArray) {
        double sum = 0;
        for (Integer d : numArray)
            sum += d;
        return sum / numArray.size();
    }

    public static void themeRefresh(MainActivity mainActivity) {

        if (mainActivity.isDay) {
            mainActivity.bgDaylight.setImageResource(R.drawable.daylight);
            Glide.with(mainActivity).load(R.drawable.road_pixelized_0).into(mainActivity.pseudo3dRoad);
            mainActivity.txtCurrentSpeed.setTextColor(Color.BLACK);
            mainActivity.txtSpeedLabel.setTextColor(Color.BLACK);
            mainActivity.txtRoll.setTextColor(Color.BLACK);
            mainActivity.weatherText.setTextColor(Color.BLACK);
            mainActivity.cloud1View.setImageResource(R.drawable.cloud1);
            mainActivity.cloud3View.setImageResource(R.drawable.cloud3);
            mainActivity.angleView.setImageResource(R.drawable.angle);
        } else {

            mainActivity.bgDaylight.setImageResource(R.drawable.night);
            Glide.with(mainActivity).load(R.drawable.road_night_0).into(mainActivity.pseudo3dRoad);
            mainActivity.txtCurrentSpeed.setTextColor(Color.WHITE);
            mainActivity.txtSpeedLabel.setTextColor(Color.WHITE);
            mainActivity.txtRoll.setTextColor(Color.WHITE);
            mainActivity.weatherText.setTextColor(Color.WHITE);
            mainActivity.cloud1View.setImageResource(R.drawable.cloud1night);
            mainActivity.cloud3View.setImageResource(R.drawable.cloud3night);
            mainActivity.angleView.setImageResource(R.drawable.angle_night);

        }

      /*  mainActivity.bgDaylight.setMinimumWidth(mainActivity.gameView.getWidth());
        mainActivity.bgDaylight.setMinimumHeight(800);*/
        mainActivity.gameView.setDay(mainActivity.isDay);
    }

    public static String parseMetersToKm(double distance) {

        if(distance > 0 && distance > 1000){
            String strDouble = String.format("%.2f", distance / 1000);
            return strDouble + " km";
        }else{
            return String.valueOf(Math.round(distance)) + " m";
        }

    }
}