package com.example.motorcyclemonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.motorcyclemonitor.R;
import com.example.motorcyclemonitor.repositories.GameRepository;

public class GameView  extends View {
    Paint paint = null;
    Context _self;
    int roll;
    int bikerId;
    long lastUpdate;
    public int speed;
    public int posY;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lastUpdate = System.currentTimeMillis();
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
    }

    public void setPosY(int posY) {
        this.posY = posY;
        invalidate();
        requestLayout();
    }

    public void setRoll(int roll) {
        this.roll = roll;
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
        GameRepository.drawGreenPlane(canvas, this);
        GameRepository.drawRedLines(canvas, this);
        GameRepository.drawRoad(canvas, this);
        GameRepository.drawRoadLines(canvas, this);
        GameRepository.drawBiker(canvas, getResources(), this, this.getWidth(), this.roll);

        invalidate();
    }

}
