package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACKS;
import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;

public class TracksActivityTest extends ActivityInstrumentationTestCase2<TracksActivity> {

    private static final String FAKE_ARTIST_ID = "fakeColdplayId";
    private static final String TEST_ARTIST_NAME = "Coldplay";

    public TracksActivityTest() {
        super(TracksActivity.class);
    }

    private void initTestView(String artistId, String artistName) throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        setActivityIntent(createLaunchIntent(artistId, artistName));
        getActivity();
    }

    private Intent createLaunchIntent(String artistId, String artistName) {
        Intent intentLaunch = TracksActivity.getCallingIntent(
                getInstrumentation().getTargetContext(),
                artistId,
                artistName
        );

        return intentLaunch;
    }

    // test case block start
    public void testAttachedFragments() throws Exception {
        // arrange
        initTestView(FAKE_ARTIST_ID, TEST_ARTIST_NAME);

        // act

        // assert
        assertNotNull(getActivity().getSupportFragmentManager().findFragmentByTag(TracksFragment.TAG));
    }

    public void testLayoutActionBar() throws Exception {
        // arrange
        initTestView(FAKE_ARTIST_ID, TEST_ARTIST_NAME);

        // act

        // assert
        onView(withText(R.string.title_activity_tracks))
                .check(matches(isDisplayed()));
        onView(withText("Coldplay"))
                .check(matches(isDisplayed()));
    }

    public void testOpenPlayerView() throws Exception {
        // arrange
        initTestView(FAKE_ARTIST_ID, TEST_ARTIST_NAME);

        // act
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().openPlayerView(COLDPLAY_TOP_TRACKS_OBJECT, 0);
            }
        });
        getInstrumentation().waitForIdleSync();

        // assert
        onView(withId(R.id.seekbar))
                .check(matches(isDisplayed()));
        pressBack();
        onView(withText(COLDPLAY_TOP_TRACKS.get(0).name))
                .check(matches(isDisplayed()));
    }
    // test case block end
}