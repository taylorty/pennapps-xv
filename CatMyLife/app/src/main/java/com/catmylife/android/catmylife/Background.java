package com.catmylife.android.catmylife;

/**
 * Created by sarah on 1/21/17.
 */

public class Background {
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}