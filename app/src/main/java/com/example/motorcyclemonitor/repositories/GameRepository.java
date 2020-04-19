package com.example.motorcyclemonitor.repositories;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.motorcyclemonitor.R;
import com.example.motorcyclemonitor.views.GameView;

public class GameRepository {
//#676767
    public static void drawGreenPlane(Canvas canvas, GameView gameView) {
        Paint paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(Color.rgb(1,103,1));
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        canvas.drawRect(0,paddingTop, gameView.getWidth(), gameView.getHeight(), paint);
    }

    public static void drawBiker(Canvas canvas, Resources resources, int roll, int width) {
        Bitmap bitmapOrg = BitmapFactory.decodeResource(resources, R.drawable.biker4);
        Matrix matrix = new Matrix();
        matrix.setRotate(roll, bitmapOrg.getWidth() /2, bitmapOrg.getHeight());
        matrix.postTranslate(((width / 2) - (bitmapOrg.getWidth() / 2)), (bitmapOrg.getHeight() /2));
        canvas.drawBitmap(bitmapOrg, matrix, null);
    }

    public static void drawRedLines(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        int paddingLeft = (int) Math.round(gameView.getWidth() * 0.3);
        int paddingRight = (int) Math.round(gameView.getWidth() * 0.7) - 20;

        Paint paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
        canvas.drawLine(paddingLeft, paddingTop, 0, gameView.getHeight(), paint);
        canvas.drawLine(paddingRight, paddingTop, gameView.getWidth(), gameView.getHeight(), paint);


    }

    public static void drawRoad(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        int paddingLeft = (int) Math.round(gameView.getWidth() * 0.3);
        int paddingRight = (int) Math.round(gameView.getWidth() * 0.7) - 20;

        Paint paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(Color.rgb(103, 103, 103));

        Path path = new Path();
        path.moveTo(paddingLeft,paddingTop);
        path.lineTo(0, gameView.getHeight());
        path.lineTo(gameView.getWidth(), gameView.getHeight());
        path.lineTo(paddingRight, paddingTop);

        canvas.drawPath(path, paint);

    }

    public static void drawRoadLines(Canvas canvas, GameView gameView) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);

        Paint paint = new Paint();
        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);

       // canvas.drawRect(gameView.getWidth() / 2,paddingTop, (gameView.getWidth() / 2) + 100 , gameView.getHeight(), paint);
        canvas.drawRect(gameView.getWidth() / 2 - 10, (paddingTop + gameView.speed), gameView.getWidth() / 2 + 5 , gameView.getHeight() - (300 - gameView.speed), paint);

    }
}
