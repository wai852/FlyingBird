package com.example.flyingbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.flyingbird.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Boolean status = false;
    private MediaPlayer mediaPlayer;
    private Animation animation;
    private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        setUpAnimation();

        mainBinding.buttonGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.acoustic_comedy);
        mediaPlayer.start();
        mainBinding.imageViewVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!status){
                    mediaPlayer.setVolume(0,0);
                    mainBinding.imageViewVolume.setImageResource(R.drawable.ic_baseline_volume_off);
                    status = true;
                }
                else{
                    mediaPlayer.setVolume(1,1);
                    mainBinding.imageViewVolume.setImageResource(R.drawable.ic_baseline_volume_up);
                    status = false;
                }
            }
        });
        mainBinding.buttonGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.reset();
                mainBinding.imageViewVolume.setImageResource(R.drawable.ic_baseline_volume_up);
                Intent iTGame = new Intent(MainActivity.this,GameActivity.class);
                startActivity(iTGame);
                finish();
            }
        });
    }

    private void setUpAnimation(){
        animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_scale);
        mainBinding.imageViewPlayer.setAnimation(animation);
        mainBinding.imageViewGrayBee.setAnimation(animation);
        mainBinding.imageViewGreenBee.setAnimation(animation);
        mainBinding.imageViewRedBee.setAnimation(animation);
        mainBinding.imageViewCoin.setAnimation(animation);
    }
}