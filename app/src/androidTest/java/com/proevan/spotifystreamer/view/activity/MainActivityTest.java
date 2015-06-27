package com.proevan.spotifystreamer.view.activity;


import android.support.test.espresso.matcher.ViewMatchers;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MainActivityTest extends MainActivityTestCase {


    public void testLayoutActionBarTitle() throws Exception {
        // arrange

        // act

        // assert
        onView(ViewMatchers.withText(R.string.app_name))
                .check(matches(isDisplayed()));
    }
}