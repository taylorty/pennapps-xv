package com.catmylife.android.catmylife;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SleepPage extends AppCompatActivity {
    ProgressBar progressBar;
    Button start_timer, stop_timer;
    MyCountDownTimer myCountDownTimer;
    ImageView normalCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        normalCat = (ImageView) findViewById(R.id.catImageView);
        if (Cat.weight == 1) {
            switchToSuperThinCat();
        } else if (Cat.weight == 2) {
            switchToThinCat();
        } else if (Cat.weight == 3) {
            switchToNormalCat();
        } else if (Cat.weight == 4) {
            switchToFatCat();
        }
        AnimationDrawable normalCatAnimation = (AnimationDrawable) normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        start_timer = (Button) findViewById(R.id.button);
        stop_timer = (Button) findViewById(R.id.button3);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCountDownTimer = new MyCountDownTimer(10000, 1000);
                myCountDownTimer.start();

            }
        });

        stop_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.cancel();

            }
        });

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            progressBar.setProgress(progressBar.getMax() - progress);
        }

        @Override
            public void onFinish() {
            finish();
        }
    }

    private void switchToFatCat() {
        normalCat.setImageResource(R.drawable.sleep_fat_animation);
        AnimationDrawable normalCatAnimation = (AnimationDrawable) normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }

    private void switchToNormalCat() {
        normalCat.setImageResource(R.drawable.sleep_normal_animation);
        AnimationDrawable normalCatAnimation = (AnimationDrawable) normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }

    private void switchToThinCat() {
        normalCat.setImageResource(R.drawable.sleep_thin_animation);
        AnimationDrawable normalCatAnimation = (AnimationDrawable) normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }

    private void switchToSuperThinCat() {
        normalCat.setImageResource(R.drawable.sleep_super_thin_animation);
        AnimationDrawable normalCatAnimation = (AnimationDrawable) normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();

    }
}


