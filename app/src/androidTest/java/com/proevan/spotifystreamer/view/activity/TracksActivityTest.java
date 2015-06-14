package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_NAME;

public class TracksActivityTest extends TracksActivityTestCase {

    private static final String TEST_ARTIST_NAME = "Coldplay";

    public void setUp() throws Exception {
        super.setUp();
        setActivityIntent(generateFakeIntentWithBundle());
        getActivity();
        TracksPresenterComponent.Initializer.instance.inject(this);
    }

    // stub block start
    private Intent generateFakeIntentWithBundle() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME.name(), TEST_ARTIST_NAME);
        intent.putExtras(bundle);

        return intent;
    }
    // stub block end

    // test case block start
    public void testLayoutActionBar() throws Exception {
        // arrange

        // act

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText(TEST_ARTIST_NAME))
                .check(matches(isDisplayed()));
    }
    // test case block end
}