package com.catmylife.android.catmylife;

/**
 * Created by sarah on 1/22/17.
 */

public class cat {
    static int weight = 3;
    static int study_time = 0;
    static String name;
    static int min_sleep_time = 60;
    static int next_level = 60;
    private static int level = 0;
    static String title = "baby";

    public static void level_up(){
        level ++;
        switch (level){
            case 1:
                title = "infant";
                break;
            case 2:
                title = "elementary";
                break;
            case 3:
                title = "middle school";
                break;
            case 4:
                title = "highschool";
                break;
            case 5:
                title = "college";
                break;
            case 6:
                title = "master";
                break;
            case 7:
                title = "doctor";
                break;
            case 8:
                title = "married";
                break;
            case 9:
                title = "professor";
                break;
            case 10:
                title = "hermit";
                break;
            case 11:
                title = "buddha";
                break;
        }
        study_time = 0;

    }
}
