package com.software4bikers.motorcyclerun.helpers;

import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.software4bikers.motorcyclerun.MainActivity;
import com.software4bikers.motorcyclerun.R;

import java.util.Arrays;
import java.util.List;

public class Helper {
    public static double median(List<Integer> values) {
         return (values.get(values.size()/2) + values.get(values.size()/2 - 1))/2;
    }

    public static void themeRefresh(MainActivity mainActivity){

        if(mainActivity.isDay){
            Glide.with(mainActivity).load(R.drawable.daylight).into(mainActivity.bgDaylight);
            Glide.with(mainActivity).load(R.drawable.road_pixelized_0).into(mainActivity.pseudo3dRoad);
            mainActivity.txtCurrentSpeed.setTextColor(Color.BLACK);
            mainActivity.txtSpeedLabel.setTextColor(Color.BLACK);

        }else{
            Glide.with(mainActivity).load(R.drawable.night).into(mainActivity.bgDaylight);
            Glide.with(mainActivity).load(R.drawable.road_night_0).into(mainActivity.pseudo3dRoad);
            mainActivity.txtCurrentSpeed.setTextColor(Color.WHITE);
            mainActivity.txtSpeedLabel.setTextColor(Color.WHITE);
        }

    }
}