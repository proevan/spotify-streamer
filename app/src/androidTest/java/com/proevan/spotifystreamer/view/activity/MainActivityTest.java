package com.proevan.spotifystreamer.view.activity;


import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACKS;
import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String FAKE_ARTIST_ID = "fakeColdplayId";
    private static final String TEST_ARTIST_NAME = "Coldplay";

    public MainActivityTest() {
        super(MainActivity.class);
    }

    private void initTestView() throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        getActivity();
    }

    // test case block start
    public void testAttachedFragments() throws Exception {
        // arrange
        initTestView();

        // act

        // assert
        assertNotNull(getActivity().getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG));
    }

    public void testLayoutActionBar() throws Exception {
        // arrange
        initTestView();

        // act

        // assert
        onView(ViewMatchers.withText(R.string.app_name))
                .check(matches(isDisplayed()));
    }

    public void testOpenTracksView() throws Exception {
        // arrange
        initTestView();
        boolean is2PaneMode = getActivity().is2PaneMode();

        // act
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().openTracksView(FAKE_ARTIST_ID, TEST_ARTIST_NAME);
            }
        });
        getInstrumentation().waitForIdleSync();

        // assert
        if (is2PaneMode) {
            onView(withId(R.id.pane_2))
                    .check(matches(isDisplayed()));
            assertNotNull(getActivity().getSupportFragmentManager().findFragmentByTag(TracksFragment.TAG));
        } else {
            onView(withId(R.id.pane_2))
                    .check(doesNotExist());
            onView(withText(COLDPLAY_TOP_TRACKS.get(0).name))
                    .check(matches(isDisplayed()));
            pressBack();
            onView(withId(R.id.pane_1))
                    .check(matches(isDisplayed()));
        }
    }

    public void testOpenPlayerView() throws Exception {
        // arrange
        initTestView();

        // act
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().openPlayerView(COLDPLAY_TOP_TRACKS_OBJECT, 0);
            }
        });
        getInstrumentation().waitForIdleSync();

        // assert
        assertTrue(((PlayerFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PlayerFragment.TAG))
                .getDialog().isShowing());
    }
    // test case block end
}