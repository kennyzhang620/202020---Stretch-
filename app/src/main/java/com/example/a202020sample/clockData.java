package com.example.a202020sample;

public class clockData {

    public static String elapsedTime;
    public static boolean passiveAlert = false;
    public static String tonalQuality = "Moderate";
    public static int hours = 0;
    public static int minutes = 0;
    public static int seconds = 0;
    public static int remainderMins = 20;

    public static void Reset() {
        hours = 0;
        minutes = 0;
        seconds = 0;
        elapsedTime = "0:00:00";
        remainderMins = 20;
    }
            ;


}
