package com.catmylife.android.catmylife;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FocusPage extends AppCompatActivity {
//     TextView timer;
//    long startTime;
//    long countUp;
//    Chronometer stopWatch;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_focus_page);
//        stopWatch = (Chronometer) findViewById(R.id.chrono);
//        startTime = SystemClock.elapsedRealtime();
//
//        timer = (TextView) findViewById(R.id.timerTextView);
//        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
//            public void onChronometerTick(Chronometer arg0) {
//                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
//                String asText = (countUp / 60) + ":" + (countUp % 60);
//                timer.setText(asText);
//            }
//        });
//
//        stopWatch.start();
//    }

    ProgressBar progressBar;
    Button start_timer, stop_timer;
    FocusPage.MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_page);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        start_timer = (Button) findViewById(R.id.button3);
        stop_timer = (Button) findViewById(R.id.button4);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCountDownTimer = new FocusPage.MyCountDownTimer(10000, 1000);
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
    public void giveUp(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("timer","Done");
        myCountDownTimer.cancel();
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_plus_signin_btn_text_light_pressed) // notification icon
                .setContentTitle("Notification!") // title for notification
                .setContentText("Hello word") // message for notification
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
        Notification notification = mBuilder.build();
        mNotifyMgr.notify(mNotificationId, notification);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

}
