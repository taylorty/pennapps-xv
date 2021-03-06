package com.catmylife.android.catmylife;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FocusPage extends AppCompatActivity {
    TextView timer;
    TextView title;
    long startTime;
    long countUp;
    Chronometer stopWatch;
    ImageView normalCat;
    ProgressBar progressBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_page);
        stopWatch = (Chronometer) findViewById(R.id.chrono);
        startTime = SystemClock.elapsedRealtime();
        timer = (TextView) findViewById(R.id.timerTextView);
        title = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                String asText = (countUp / 60) + ":" + (countUp % 60);
                Cat.study_time++;
                progressBar.setProgress(Cat.study_time);
                if (Cat.study_time > Cat.next_level){
                    Cat.level_up();
                    Cat.study_time = 0;
                }
                timer.setText(asText);
                title.setText(Cat.title);
            }
        });
        stopWatch.start();
        normalCat = (ImageView) findViewById(R.id.catImageView);
         if (Cat.weight == 1){
            switchToSuperThinCat();
        }
        else if (Cat.weight == 2){
            switchToThinCat();
        }
        else if (Cat.weight == 3){
            switchToNormalCat();
        }
        else if (Cat.weight == 4){
            switchToFatCat();
        }
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }

    public void giveUp(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
//        switchToFatCat();
    }

    private void switchToFatCat(){
        normalCat.setImageResource(R.drawable.study_fat_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToNormalCat(){
        normalCat.setImageResource(R.drawable.study_normal_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToThinCat(){
        normalCat.setImageResource(R.drawable.study_thin_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToSuperThinCat(){
        normalCat.setImageResource(R.drawable.study_super_thin_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("timer","Done");
        stopWatch.stop();
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_plus_signin_btn_text_light_pressed) // notification icon
                .setContentTitle("CatMyLife") // title for notification
                .setContentText("Help your cat become smarter!") // message for notification
                .setAutoCancel(false) // clear notification after click
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_NO_CREATE);
        mBuilder.setContentIntent(pi);
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

}
