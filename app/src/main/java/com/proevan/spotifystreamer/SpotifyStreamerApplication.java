package com.proevan.spotifystreamer;

import android.app.Application;

import com.orhanobut.logger.Logger;

public class SpotifyStreamerApplication extends Application {

    public static final String TAG = "SpotifyStreamerApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
    }
}
