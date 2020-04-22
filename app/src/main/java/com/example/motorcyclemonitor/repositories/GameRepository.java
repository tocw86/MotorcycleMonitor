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

import java.util.Random;

public class GameRepository {

    public static void drawBiker(Canvas canvas, Resources resources, GameView gameView, int width, int roll) {
        int paddingTop = (int) Math.round(gameView.getHeight() * 0.7);
        Bitmap bitmapOrg = BitmapFactory.decodeResource(resources, R.drawable.biker5);
        Matrix matrix = new Matrix();
        if(roll > 7 || roll < -7){
            matrix.setRotate(roll, bitmapOrg.getWidth() /2, bitmapOrg.getHeight());
        }else{
            matrix.setRotate(0, bitmapOrg.getWidth() /2, bitmapOrg.getHeight());
        }
        if(gameView.speed > 20){
            Random rand = new Random();
            int randX = rand.nextInt(5);
            rand = new Random();
            int randY = rand.nextInt(15);

            rand = new Random();
            int unsigned = rand.nextInt(2);
            if(unsigned == 2){
                randX = randX * -1;
            }else{
                randY = randY * -1;
            }

            rand = new Random();
            int canMove = rand.nextInt(20);
            if(canMove == 1){
                matrix.postTranslate(((width / 2) - (bitmapOrg.getWidth() / 2)) + randX, (float) (paddingTop - (bitmapOrg.getHeight() * 0.8)) + randY);
            }else{
                matrix.postTranslate(((width / 2) - (bitmapOrg.getWidth() / 2)), (float) (paddingTop - (bitmapOrg.getHeight() * 0.8)));
            }
        }else{
            matrix.postTranslate(((width / 2) - (bitmapOrg.getWidth() / 2)), (float) (paddingTop - (bitmapOrg.getHeight() * 0.8)));
        }
        canvas.drawBitmap(bitmapOrg, matrix, null);
    }


}
