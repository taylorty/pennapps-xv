package com.catmylife.android.catmylife;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catmylife.android.catmylife.logger.Log;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;

import java.util.concurrent.TimeUnit;

public class MainPage extends AppCompatActivity {
    public static final String TAG = "Main";
    TextView t;
    private long originalCoin = 500;
    private long stepCount = 0;
    //private int feedCounter = 0;
    private GoogleApiClient mClient = null;
    private long prevStepCount = 0;
    Chronometer stopWatch;
    long countUp;
    private String name = Login.getName();
    ImageView normalCat;
    TextView title;
    ProgressBar progressBar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        t = (TextView) findViewById(R.id.coinTextView);
        buildFitnessClient();
        readData();
        stopWatch = (Chronometer) findViewById(R.id.chrono);
        t.setText(Long.toString(originalCoin));
        title = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                progressBar.setProgress(Cat.feedCounter);
                Cat.feedCounter -= 1;
                readData();
                title.setText(Cat.title);
                if (Cat.feedCounter < 0) {
                    Cat.feedCounter = 10;
                    Cat.weight -= 1;
                    if (Cat.weight == 0) {
                        switchDie();
                    } else if (Cat.weight == 1){
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
                }
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
        AnimationDrawable normalCatAnimation =    (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();

    }

    private void switchToFatCat(){
        normalCat.setImageResource(R.drawable.fat_cat_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToNormalCat(){
        normalCat.setImageResource(R.drawable.normal_cat_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToThinCat(){
        normalCat.setImageResource(R.drawable.thin_cat_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    private void switchToSuperThinCat(){
        normalCat.setImageResource(R.drawable.superthin_cat_animation);
        AnimationDrawable normalCatAnimation =  (AnimationDrawable)normalCat.getDrawable();
        normalCatAnimation.setCallback(normalCat);
        normalCatAnimation.setVisible(true, true);
        normalCatAnimation.start();
    }
    public void switchDie() {
        Intent intent = new Intent(this, CoffinPage.class);
        startActivity(intent);
    }

    /**
     * Build a {@link GoogleApiClient} to authenticate the user and allow the application
     * to connect to the Fitness APIs. The included scopes should match the scopes needed
     * by your app (see the documentation for details).
     * Use the {@link GoogleApiClient.OnConnectionFailedListener}
     * to resolve authentication failures (for example, the user has not signed in
     * before, or has multiple accounts and must specify which account to use).
     */
    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.  What to do?
                                // Subscribe to some data sources!
                                subscribe();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.w(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.w(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .build();
    }
    /**
     * Record step data by requesting a subscription to background step data.
     */
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.RecordingApi.subscribe(mClient, DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            if (status.getStatusCode()
                                    == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
                                Log.i(TAG, "Existing subscription for activity detected.");
                            } else {
                                Log.i(TAG, "Successfully subscribed!");
                            }
                        } else {
                            Log.w(TAG, "There was a problem subscribing.");
                        }
                    }
                });
    }

    public void buyFood(View view) {
        readData();
        if (originalCoin < 100) {
            return;
        }
        originalCoin -= 100;
        t.setText(Long.toString(originalCoin));
        Cat.feed();
        if (Cat.weight == 0) {
            switchDie();
        } else if (Cat.weight == 1){
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
    }

    public void study(View view) {
        Intent intent = new Intent(this, FocusPage.class);
        startActivity(intent);
        stopWatch.stop();
    }

    public void readData() {
        new VerifyDataTask().execute();
        t.setText(Long.toString(originalCoin));
    }

    public void sleep(View view) {
        Intent intent = new Intent(this, SleepPage.class);
        startActivity(intent);
        stopWatch.stop();
    }

    /**
     * Read the current daily step total, computed from midnight of the current day
     * on the device's current timezone.
     */
    public class VerifyDataTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            long total = 0;
            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
            DailyTotalResult totalResult = result.await(5, TimeUnit.SECONDS);
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                total = totalSet.isEmpty()
                        ? 0
                        : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                prevStepCount = stepCount;
                stepCount = total;
                originalCoin += (stepCount - prevStepCount)*20;

            } else {
                Log.w(TAG, "There was a problem getting the step count.");
            }
            Log.i(TAG, "Total steps: " + total);
            return null;
        }
    }
}
