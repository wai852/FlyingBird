package com.example.flyingbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.flyingbird.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity {
    //position
    private int birdX, birdY, beeGrX, beeGrY, beeRX, beeRY, beeGeX, beeGeY,
            coin1X, coin1Y, coin2X, coin2Y;

    // dimensions of screen;
    private int screenWidth;
    private int screenHeight;

    //remaining hearts
    int hearts = 3;
    //collected coins
    int coins = 0;

    private Handler handler, handler2;
    private Runnable runnable, runnable2;
    private boolean beginControl = false;
    private boolean touchControl = false;
    private ActivityGameBinding gameBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameBinding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(gameBinding.getRoot());

        gameBinding.constraintLayoutGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gameBinding.textViewGameMsg.setVisibility(View.GONE);
                if (!beginControl) { //first time touch
                    beginControl = true;
                    screenWidth = (int) gameBinding.constraintLayoutGame.getWidth();
                    screenHeight = (int) gameBinding.constraintLayoutGame.getHeight();

                    birdX = (int) gameBinding.imageViewGamePlayer.getX();
                    birdY = (int) gameBinding.imageViewGamePlayer.getY();

                    handler = new Handler();
                    runnable = () -> {
                        moveController();
                        beeController();
                        collisionController();
                    };
                    handler.post(runnable);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)//start
                    {
                        touchControl = true;
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) //finish
                    {
                        touchControl = false;
                    }
                }

                return true;
            }
        });
    }

    private void moveController() {
        if (touchControl) {
            birdY = birdY - (screenHeight / 50);
        } else {
            birdY = birdY + (screenHeight / 50);
        }
        if (birdY <= 0) {
            birdY = 0;
        }
        //keep the bird inside the screen
        if (birdY >= (screenHeight - gameBinding.imageViewGamePlayer.getHeight())) {
            birdY = (screenHeight - gameBinding.imageViewGamePlayer.getHeight());
        }
        gameBinding.imageViewGamePlayer.setY(birdY);
    }

    private void beeController() {
        gameBinding.imageViewGameGreenBee.setVisibility(View.VISIBLE);
        gameBinding.imageViewGameRedBee.setVisibility(View.VISIBLE);
        gameBinding.imageViewGameGrayBee.setVisibility(View.VISIBLE);
        gameBinding.imageViewGameCoin.setVisibility(View.VISIBLE);
        gameBinding.imageViewGameCoin2.setVisibility(View.VISIBLE);

        //speed
        beeGeX = beeGeX - (screenWidth / 150); //move units depend on the screen width
        beeGeX = speedUpController(beeGeX);
        if (beeGeX < 0) { //when soon out of screen
            beeGeX = screenWidth + 200; //move it back to screen
            // random: 0 - less than 1,
            //then multiply to screen Height => get new position
            beeGeY = (int) Math.floor(Math.random() * screenHeight);
            //prevent it goes off from screen
            if (beeGeY <= 0) {
                beeGeY = 0;
            }
            //keep the bird inside the screen
            if (beeGeY >= (screenHeight - gameBinding.imageViewGameGreenBee.getHeight())) {
                beeGeY = (screenHeight - gameBinding.imageViewGameGreenBee.getHeight());
            }
        }
        gameBinding.imageViewGameGreenBee.setX(beeGeX);
        gameBinding.imageViewGameGreenBee.setY(beeGeY);
        beeGrX = beeGrX - (screenWidth / 140);
        beeGrX = speedUpController(beeGrX);
        if (beeGrX < 0) {
            beeGrX = screenWidth + 200;
            beeGrY = (int) Math.floor(Math.random() * screenHeight);
            if (beeGeY <= 0) {
                beeGeY = 0;
            }
            if (beeGrY >= (screenHeight - gameBinding.imageViewGameGrayBee.getHeight())) {
                beeGrY = (screenHeight - gameBinding.imageViewGameGrayBee.getHeight());
            }
        }
        gameBinding.imageViewGameGrayBee.setX(beeGrX);
        gameBinding.imageViewGameGrayBee.setY(beeGrY);
        beeRX = beeRX - (screenWidth / 120);
        beeRX = speedUpController(beeRX);
        if (beeRX < 0) {
            beeRX = screenWidth + 200;
            beeRY = (int) Math.floor(Math.random() * screenHeight);
            if (beeRY <= 0) {
                beeRY = 0;
            }
            if (beeRY >= (screenHeight - gameBinding.imageViewGameRedBee.getHeight())) {
                beeRY = (screenHeight - gameBinding.imageViewGameRedBee.getHeight());
            }
        }
        gameBinding.imageViewGameRedBee.setX(beeRX);
        gameBinding.imageViewGameRedBee.setY(beeRY);

        coin1X = coin1X - (screenWidth / 120);
        if (coin1X < 0) {
            coin1X = screenWidth + 200;
            coin1Y = (int) Math.floor(Math.random() * screenHeight);
            if (coin1Y <= 0) {
                coin1Y = 0;
            }
            if (coin1Y >= (screenHeight - gameBinding.imageViewGameCoin.getHeight())) {
                coin1Y = (screenHeight - gameBinding.imageViewGameCoin.getHeight());
            }
        }
        gameBinding.imageViewGameCoin.setX(coin1X);
        gameBinding.imageViewGameCoin.setY(coin1Y);

        coin2X = coin2X - (screenWidth / 120);
        if (coin2X < 0) {
            coin2X = screenWidth + 200;
            coin2Y = (int) Math.floor(Math.random() * screenHeight);
            if (coin2Y <= 0) {
                coin2Y = 0;
            }
            if (coin2Y >= (screenHeight - gameBinding.imageViewGameCoin2.getHeight()))
            {
                coin2Y = (screenHeight - gameBinding.imageViewGameCoin2.getHeight());
            }
        }
        gameBinding.imageViewGameCoin2.setX(coin2X);
        gameBinding.imageViewGameCoin2.setY(coin2Y);
    }
    public void  collisionController()
    {
        int centerBeeGeX = beeGeX + gameBinding.imageViewGameGreenBee.getWidth() / 2;
        int centerBeeGeY = beeGeY + gameBinding.imageViewGameGreenBee.getHeight() / 2;

        if (centerBeeGeX >= birdX && centerBeeGeX <= (birdX + gameBinding.imageViewGamePlayer.getWidth())
                && centerBeeGeY >= birdY && centerBeeGeY <= (birdX + gameBinding.imageViewGamePlayer.getHeight())
        )
        {
            beeGeX = screenWidth + 200;
            hearts--;
            gameBinding.ratingBarGameLife.setRating(hearts);
        }

        int centerBeeGrX = beeGrX + gameBinding.imageViewGameGrayBee.getWidth() / 2;
        int centerBeeGrY = beeGrY + gameBinding.imageViewGameGrayBee.getHeight() / 2;

        if (centerBeeGrX >= birdX && centerBeeGrX <= (birdX + gameBinding.imageViewGamePlayer.getWidth())
                && centerBeeGrY >= birdY && centerBeeGrY <= (birdY + gameBinding.imageViewGamePlayer.getHeight())
        )
        {
            beeGrX = screenWidth + 200;
            hearts--;
            gameBinding.ratingBarGameLife.setRating(hearts);
        }

        int centerBeeRedX = beeRX + gameBinding.imageViewGameRedBee.getWidth() / 2;
        int centerBeeRedY = beeRY + gameBinding.imageViewGameRedBee.getHeight() / 2;

        if (centerBeeRedX >= birdX && centerBeeRedX <= (birdX + gameBinding.imageViewGamePlayer.getWidth())
                &&  centerBeeRedY >= birdY &&  centerBeeRedY <= (birdY + gameBinding.imageViewGamePlayer.getHeight())
        )
        {
            beeRX = screenWidth + 200;
            hearts--;
            gameBinding.ratingBarGameLife.setRating(hearts);
        }

        int centerCoin1X = coin1X +  gameBinding.imageViewGameCoin.getWidth() / 2;
        int centerCoin1Y = coin1Y + gameBinding.imageViewGameCoin.getHeight() / 2;

        if (centerCoin1X >= birdX && centerCoin1X <= (birdX + gameBinding.imageViewGamePlayer.getWidth())
                && centerCoin1Y >= birdY && centerCoin1Y <= (birdY + gameBinding.imageViewGamePlayer.getHeight())
        )
        {
            coin1X = screenWidth + 200;
            coins = coins  + 10;
            gameBinding.textViewScore.setText(coins+"/250");
        }

        int centerCoin2X = coin2X + gameBinding.imageViewGameCoin2.getWidth() / 2;
        int centerCoin2Y = coin2Y + gameBinding.imageViewGameCoin2.getHeight() / 2;

        if (centerCoin2X >= birdX && centerCoin2X <= (birdX + gameBinding.imageViewGamePlayer.getWidth())
                && centerCoin2Y >= birdY && centerCoin2Y <= (birdY + gameBinding.imageViewGamePlayer.getHeight())
        )
        {
            coin2X = screenWidth + 200;
            coins = coins  + 10;
            gameBinding.textViewScore.setText(coins+"/250");
        }

        if (hearts > 0 && coins < 250)
        {
            handler.postDelayed(runnable,20);
        }
        else if (coins >= 250)
        {
            handler.removeCallbacks(runnable);
            gameBinding.constraintLayoutGame.setEnabled(false);
            gameBinding.textViewGameMsg.setVisibility(View.VISIBLE);
            gameBinding.textViewGameMsg.setText(getString(R.string.text_game_won));
            gameBinding.imageViewGameGreenBee.setVisibility(View.INVISIBLE);
            gameBinding.imageViewGameGrayBee.setVisibility(View.INVISIBLE);
            gameBinding.imageViewGameRedBee.setVisibility(View.INVISIBLE);
            gameBinding.imageViewGameCoin.setVisibility(View.INVISIBLE);
            gameBinding.imageViewGameCoin2.setVisibility(View.INVISIBLE);

            //make the bird fly off from the screen
            handler2 = new Handler();
            runnable2 = () -> {
                birdX = birdX + (screenWidth / 300);
                gameBinding.imageViewGamePlayer.setX(birdX);
                gameBinding.imageViewGamePlayer.setY(screenHeight / 2.0f); //fly middle of the screen
                if (birdX <= screenWidth)
                {   //move the bird on x-axis
                    handler2.postDelayed(runnable2,20);
                }
                else
                {
                    handler2.removeCallbacks(runnable2);
                    Intent iTRes = new Intent(GameActivity.this,ResultActivity.class);
                    iTRes.putExtra("coins",coins);
                    startActivity(iTRes);
                    finish();
                }
            };
            handler2.post(runnable2);
        }
        else if (hearts == 0)
        {
            handler.removeCallbacks(runnable);
            Intent intent = new Intent(GameActivity.this,ResultActivity.class);
            intent.putExtra("coins",coins);
            startActivity(intent);
            finish();
        }
    }
    private int speedUpController(int speedX){
        if(coins >= 50 && coins <100){
            speedX = speedX - (screenWidth / 130);
        }
        if(coins >= 100 && coins < 200){
            speedX = speedX - (screenWidth / 125);
        }
        if(coins >= 200){
            speedX = speedX - (screenWidth / 105);
        }
        return speedX;
    }
}