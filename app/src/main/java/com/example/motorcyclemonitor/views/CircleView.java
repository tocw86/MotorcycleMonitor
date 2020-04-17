package com.example.motorcyclemonitor.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.motorcyclemonitor.R;

public class CircleView extends View
{
    Paint paint = null;
    public Integer yLine;
    public Integer xMove;
    public CircleView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleView,
                0, 0);
        try {
            yLine = a.getInteger(R.styleable.CircleView_yLine, 0);
            xMove =  a.getInteger(R.styleable.CircleView_xMove, 0);
        } finally {
            a.recycle();
        }
    }
    public void setxMove(int xMove){
        this.xMove = xMove;
        invalidate();
        requestLayout();
    }
    static class SubCardViewHolder {
        CircleView title;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int xmoved = (canvas.getWidth() / 2) - xMove;
        canvas.drawLine(xmoved,0,canvas.getWidth() / 2, canvas.getHeight(), paint);
     }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}