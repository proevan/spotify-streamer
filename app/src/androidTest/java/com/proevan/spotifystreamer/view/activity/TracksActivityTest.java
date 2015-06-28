package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class TracksActivityTest extends TracksActivityTestCase {


    private void initTestView(String artistId, String artistName) throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        setActivityIntent(createTargetIntent(artistId, artistName));
        getActivity();
    }

    private Intent createTargetIntent(String artistId, String artistName) {
        Intent intentLaunchActivity =
                TracksActivity.getCallingIntent(getInstrumentation().getTargetContext(), artistId, artistName);

        return intentLaunchActivity;
    }

    public void testLayoutActionBar() throws Exception {
        // arrange
//        setUp(FAKE_ARTIST_ID);

        // act

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText("Coldplay"))
                .check(matches(isDisplayed()));
    }
}