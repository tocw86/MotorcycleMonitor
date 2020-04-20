package com.example.motorcyclemonitor.repositories;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.example.motorcyclemonitor.R;
import com.example.motorcyclemonitor.views.GameView;

public class GameRepository {

    public static final int darkGreen = Color.rgb(1,103,1);
    public static final int red = Color.RED;
    public static final int darkGray = Color.rgb(103, 103, 103);
    public static final int lightGreen = Color.rgb(0,197,0);

//#676767
    public static void drawGreenPlane(Canvas canvas, GameView gameView) {
        Paint paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(darkGreen);
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        canvas.drawRect(0,paddingTop, gameView.getWidth(), gameView.getHeight(), paint);
    }

    public static void drawBiker(Canvas canvas, Resources resources, GameView gameView, int width, int roll) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        Bitmap bitmapOrg = BitmapFactory.decodeResource(resources, R.drawable.biker4);
        Matrix matrix = new Matrix();
        matrix.setRotate(roll, bitmapOrg.getWidth() /2, bitmapOrg.getHeight());
        matrix.postTranslate(((width / 2) - (bitmapOrg.getWidth() / 2)), (float) (paddingTop - (bitmapOrg.getHeight() * 0.8)));
        canvas.drawBitmap(bitmapOrg, matrix, null);
    }

    public static void drawRedLines(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        int paddingLeft = (int) Math.round(gameView.getWidth() * 0.2);
        int paddingRight = (int) Math.round(gameView.getWidth() * 0.8) - 20;

        Paint paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(red);

        Path leftPath = new Path();
        leftPath.moveTo(paddingLeft,paddingTop);
        leftPath.lineTo(0,gameView.getHeight() - 35);
        leftPath.lineTo(0,gameView.getHeight());
        leftPath.lineTo(paddingLeft,paddingTop);

        Path rightPath = new Path();
        leftPath.moveTo(paddingRight,paddingTop);
        leftPath.lineTo( gameView.getWidth(),gameView.getHeight() - 35);
        leftPath.lineTo( gameView.getWidth(),gameView.getHeight());
        leftPath.lineTo(paddingRight,paddingTop);


        canvas.drawPath(leftPath, paint);
        canvas.drawPath(rightPath, paint);

    }

    public static void drawRoad(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        int paddingLeft = (int) Math.round(gameView.getWidth() * 0.2);
        int paddingRight = (int) Math.round(gameView.getWidth() * 0.8) - 20;

        Paint paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(darkGray);

        Path path = new Path();
        path.moveTo(paddingLeft,paddingTop);
        path.lineTo(0, gameView.getHeight());
        path.lineTo(gameView.getWidth(), gameView.getHeight());
        path.lineTo(paddingRight, paddingTop);

        canvas.drawPath(path, paint);

    }

    public static void drawRoadLines(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(0);
        linePaint.setColor(Color.WHITE);

        Paint grassPaint = new Paint();
        grassPaint.setStrokeWidth(0);
        grassPaint.setColor(lightGreen);

        canvas.drawRect(gameView.getWidth() / 2 - 10, (paddingTop + gameView.posY), gameView.getWidth() / 2 + 5 , ((paddingTop + 100) + gameView.posY), linePaint);

    }
}
