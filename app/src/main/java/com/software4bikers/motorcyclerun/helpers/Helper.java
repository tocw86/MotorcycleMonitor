package com.software4bikers.motorcyclerun.helpers;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helper {
    public static double median(List<Integer> values) {
         return (values.get(values.size()/2) + values.get(values.size()/2 - 1))/2;
    }

    public static double getMedian(List<Integer> numArray){
        double sum = 0;
        for(Integer d : numArray)
            sum += d;
        return sum / numArray.size();
    }

    public static void themeRefresh(MainActivity mainActivity){

        if(mainActivity.isDay){
            mainActivity.bgDaylight.setImageResource(R.drawable.daylight);
            mainActivity.pseudo3dRoad.setImageResource(R.drawable.road_pixelized_0);
            mainActivity.txtCurrentSpeed.setTextColor(Color.BLACK);
            mainActivity.txtSpeedLabel.setTextColor(Color.BLACK);
            mainActivity.txtRoll.setTextColor(Color.BLACK);
            mainActivity.weatherText.setTextColor(Color.BLACK);
            mainActivity.cloud1View.setImageResource(R.drawable.cloud1);
            mainActivity.cloud3View.setImageResource(R.drawable.cloud3);
            mainActivity.angleView.setImageResource(R.drawable.angle);
        }else{

            mainActivity.bgDaylight.setImageResource(R.drawable.night);
            mainActivity.pseudo3dRoad.setImageResource(R.drawable.road_night_0);
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
}