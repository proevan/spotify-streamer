package com.proevan.spotifystreamer.util;

import android.app.Activity;
import android.widget.Toast;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class TestingUtil {

    public static void waitForPreviousToastDismiss(int duration) throws InterruptedException {
        int durationInMillisecond;

        if (duration == Toast.LENGTH_SHORT)
            durationInMillisecond = 2000;
        else if (duration == Toast.LENGTH_LONG)
            durationInMillisecond = 3500;
        else
            durationInMillisecond = duration;

        Thread.sleep(durationInMillisecond);
    }

    public static void checkToastIsDisplayed(Activity activity, int stringResId) {
        onView(withText(stringResId)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

}
