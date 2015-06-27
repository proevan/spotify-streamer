package com.proevan.spotifystreamer;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

public class SpotifyStreamerApplication extends Application {

    public static final String TAG = "SpotifyStreamerApplication";
    private static Context sAppContext;
    private static boolean sIsTestMode = false; // workaround for testing, prevent UI test stuck problem

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
        sAppContext = this;
    }

    public static boolean isTestMode() {
        return sIsTestMode;
    }

    public static void setIsTestMode(boolean isTestMode) {
        sIsTestMode = isTestMode;
    }

    public static Context getAppContext() {
        return sAppContext;
    }
}
