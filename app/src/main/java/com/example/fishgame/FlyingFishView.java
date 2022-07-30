package com.example.fishgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int redX, redY, redSpeed = 16;
    private Paint redPaint = new Paint();

    private Bitmap background;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    private boolean touch = false;

    private int score, FishLifeCounter;

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);


        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(60);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        FishLifeCounter = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minFishY = fish[0].getHeight(); // 74-83 : putting movement boundries to fish
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        }

        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if (touch) {                        // 85-91 when screen touch , change to fish 2 when not change to fish 1
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;

        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

//yellow ball
        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY)) { // if ball touch then add score
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0) {                  //random circle location
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

//green ball
        greenX = greenX - greenSpeed * 11/10;

        if (hitBallChecker(greenX, greenY)) { // if ball touch then add score
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0) {                  //random circle location
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);

//red ball
        redX = redX - redSpeed * 10/11;

        if (hitBallChecker(redX, redY)) { // if ball touch then add score
            redX = -100;
            FishLifeCounter--;

            if (FishLifeCounter == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
    //game over
                Intent gameOverIntent = new Intent(getContext() , GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0) {                  //random circle location
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 35, redPaint);

        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        for (int i=0;i<3;i++){
            int x = (int) (380 + life[0].getWidth() * 1.5 * i );
            int y = 30;

            if (i < FishLifeCounter){

                canvas.drawBitmap(life[0],x,y,null);
            }
            else{
                canvas.drawBitmap(life[1],x,y,null);

            }
        }


//        canvas.drawBitmap(life[0], 380, 10, null);
//        canvas.drawBitmap(life[0], 480, 10, null);
//        canvas.drawBitmap(life[0], 580, 10, null);

    }

    public boolean hitBallChecker(int x, int y) { //see if ball and fish make contact
        return fishX < x &&
                x < (fishX + fish[0].getWidth()) &&
                fishY < y &&
                y < (fishY + fish[0].getHeight());
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) { //see if screen touched
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
