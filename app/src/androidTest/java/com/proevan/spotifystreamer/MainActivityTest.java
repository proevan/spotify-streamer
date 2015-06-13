package com.proevan.spotifystreamer;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    // stub block start
    // stub block end

    // test case block start
    public void testLayoutActionBarTitle() throws Exception {
        // arrange

        // act

        // assert
        onView(withText(R.string.action_bar_title_main_activity))
                .check(matches(isDisplayed()));
    }

    public void testLayoutSearchBar() throws Exception {
        // arrange

        // act

        // assert
        onView(withId(R.id.search_bar))
                .check(matches(isDisplayed()));
    }
    // test case block end
}