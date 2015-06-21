package com.proevan.spotifystreamer;

import android.app.Application;

import com.orhanobut.logger.Logger;

public class SpotifyStreamerApplication extends Application {

    public static final String TAG = "SpotifyStreamerApplication";

    private static boolean sIsTestMode = false; // workaround for testing, prevent UI test stuck problem

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
    }

    public static boolean isTestMode() {
        return sIsTestMode;
    }

    public static void setIsTestMode(boolean isTestMode) {
        sIsTestMode = isTestMode;
    }
}
