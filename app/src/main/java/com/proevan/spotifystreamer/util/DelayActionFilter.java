package com.proevan.spotifystreamer.util;

import android.os.Handler;

public class DelayActionFilter {

    private Handler mSearchHandler = new Handler();
    private Runnable mSearchHandlerCallback;
    private int mDelayInMillisecond;

    public DelayActionFilter(int delayInMillisecond) {
        mDelayInMillisecond = delayInMillisecond;
    }

    public void prepareToDoActionWithDelay(final Callback callback) {
        if (mSearchHandlerCallback != null)
            mSearchHandler.removeCallbacks(mSearchHandlerCallback);
        mSearchHandlerCallback = new Runnable() {
            @Override
            public void run() {
                mSearchHandler.removeCallbacks(mSearchHandlerCallback);
                if (mSearchHandlerCallback != null) {
                    mSearchHandlerCallback = null;
                    callback.doAction();
                }
            }
        };

        mSearchHandler.postDelayed(
                mSearchHandlerCallback,
                mDelayInMillisecond);
    }

    public interface Callback {
        void doAction();
    }
}
