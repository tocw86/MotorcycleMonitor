package com.software4bikers.motorcyclerun.helpers;

import android.graphics.Color;
import android.view.View;

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
            mainActivity.txtRoll.setTextColor(Color.BLACK);
            Glide.with(mainActivity).load(R.drawable.cloud1).into(mainActivity.cloud1View);
            Glide.with(mainActivity).load(R.drawable.cloud3).into(mainActivity.cloud3View);
            Glide.with(mainActivity).load(R.drawable.angle).into(mainActivity.angleView);
        }else{
            Glide.with(mainActivity).load(R.drawable.night).into(mainActivity.bgDaylight);
            Glide.with(mainActivity).load(R.drawable.road_night_0).into(mainActivity.pseudo3dRoad);
            mainActivity.txtCurrentSpeed.setTextColor(Color.WHITE);
            mainActivity.txtSpeedLabel.setTextColor(Color.WHITE);
            mainActivity.txtRoll.setTextColor(Color.WHITE);
            Glide.with(mainActivity).load(R.drawable.cloud1night).into(mainActivity.cloud1View);
            Glide.with(mainActivity).load(R.drawable.cloud3night).into(mainActivity.cloud3View);
            Glide.with(mainActivity).load(R.drawable.angle_night).into(mainActivity.angleView);
        }


        if (mainActivity.bgDaylight.getPaddingBottom() == 0) {
            mainActivity.bgDaylight.setPadding(0, 0, 0, mainActivity.pseudo3dRoad.getHeight());
        }
        if (mainActivity.cloud3View.getPaddingTop() == 0) {
            mainActivity.cloud3PosY = mainActivity.gameView.getHeight() / 3;
            mainActivity.cloud3PosX = (int) (mainActivity.gameView.getWidth() * 0.7);
            mainActivity.cloud3View.setPadding(mainActivity.cloud3PosX, mainActivity.cloud3PosY, 0, 0);
            mainActivity.cloud3View.setVisibility(View.VISIBLE);

            mainActivity.cloud2Animation.setLeft(mainActivity.cloud3PosX);
            mainActivity.cloud2Animation.setTop(mainActivity.cloud3PosY);
            mainActivity.cloud2Animation.setPosYChange(10);
            mainActivity.cloud2Animation.start();

        }
        if (mainActivity.cloud1View.getPaddingTop() == 0) {
            mainActivity.cloud1PosY = 140;
            mainActivity.cloud1PosX = -25;
            mainActivity.cloud1View.setPadding(mainActivity.cloud1PosX, mainActivity.cloud1PosY, 0, 0);
            mainActivity.cloud1View.setVisibility(View.VISIBLE);

            mainActivity.cloud1Animation.setLeft(mainActivity.cloud1PosX);
            mainActivity.cloud1Animation.setTop(mainActivity.cloud1PosY);
            mainActivity.cloud1Animation.setPosYChange(5);
            mainActivity.cloud1Animation.start();
        }

    }
}