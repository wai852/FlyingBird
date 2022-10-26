package com.example.flyingbird;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.flyingbird.databinding.ActivityGameBinding;
import com.example.flyingbird.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    private int finalScore;
    private SharedPreferences sharedPreferences;
    private ActivityResultBinding resultBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultBinding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(resultBinding.getRoot());

        finalScore = getIntent().getIntExtra("coins",0);
        resultBinding.textViewResScore.setText(getString(R.string.text_res_score)+""+ finalScore);
        checkHighestScore();
        resultBinding.btnResPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iToGame = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(iToGame);
                finish();
            }
        });
    }
    private void checkHighestScore(){
        //create db and set only this app can access
        sharedPreferences = this.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highest_score",0);
        if(finalScore >= 500){
            resultBinding.textViewResMsg.setText(getString(R.string.text_game_won));
            sharedPreferences.edit().putInt("highest_score",finalScore).apply();
            resultBinding.textViewResHighestScore.setText(getString(R.string.text_res_highest_score)+" "+finalScore);
        }
        else if(finalScore >= highestScore){
            resultBinding.textViewResMsg.setText(getString(R.string.btn_res_lost));
            sharedPreferences.edit().putInt("highest_score",finalScore).apply();
            resultBinding.textViewResHighestScore.setText(getString(R.string.text_res_highest_score)+" "+highestScore);
        }
        else{ // lost the game + don't have highest score record
            resultBinding.textViewResMsg.setText(getString(R.string.btn_res_lost));
            resultBinding.textViewResHighestScore.setText(getString(R.string.text_res_highest_score)+" "+highestScore);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_msg));
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.dialog_n_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAndRemoveTask();
                } else{
                    finishAffinity();
                    System.exit(0);
                }
                /*or move to background only
                moveTaskToBack(true);
                android.os.Process.KillProcess(android.os.Process.myPid());
                System.exit(0);
                 */
            }
        }).setPositiveButton(getString(R.string.dialog_p_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}