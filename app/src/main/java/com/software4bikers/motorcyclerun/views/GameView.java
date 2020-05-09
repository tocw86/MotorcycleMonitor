package com.software4bikers.motorcyclerun.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.software4bikers.motorcyclerun.R;
import com.software4bikers.motorcyclerun.repositories.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameView  extends View {
    Paint paint = null;
    Context _self;
    int roll;
    int bikerId;
    long lastUpdate;
    public int speed;
    public int posY;
    public int maximumRoll = 0;
    public List<Integer> rollArray;
    private Handler frame = new Handler();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lastUpdate = System.currentTimeMillis();
        rollArray = new ArrayList<Integer>();
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GameView,
                0, 0);
        try {
            roll = a.getInteger(R.styleable.GameView_roll, 0);
            speed = a.getInteger(R.styleable.GameView_speed, 0);
            posY = a.getInteger(R.styleable.GameView_posY, 0);
        } finally {
            a.recycle();
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calculateRoll();
            }
        },1000);
    }

    public int getMaximumRoll() {
        return maximumRoll;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        invalidate();
        requestLayout();
    }

    public void setRoll(int roll) {
        this.roll = roll;
        rollArray.add(roll);
        invalidate();
        requestLayout();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GameRepository.drawBiker(canvas, getResources(), this, this.getWidth(), this.maximumRoll);
        invalidate();
    }
    synchronized private void calculateRoll() {

        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, 1000);

    }

    private Runnable frameUpdate = new Runnable() {

        @Override
        synchronized public void run() {
            frame.removeCallbacks(frameUpdate);

            if(rollArray.size() > 0){

                int max = Collections.max(rollArray);
                int min = Collections.min(rollArray);

                if(min < 0 && max <=5){
                    min = min * -1;
                    maximumRoll =  min;
                }else{
                    maximumRoll = max;
                }

                rollArray = new ArrayList<Integer>();

            }

            frame.postDelayed(frameUpdate, 1000);
        }
    };
}
