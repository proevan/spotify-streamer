package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;

import kaaes.spotify.webapi.android.models.Tracks;

import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;

public class PlayerActivityTest extends ActivityInstrumentationTestCase2<PlayerActivity> {

    public PlayerActivityTest() {
        super(PlayerActivity.class);
    }

    private void initTestView(Tracks tracks, int playIndex) throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        setActivityIntent(createLaunchIntent(tracks, playIndex));
        getActivity();
    }

    private Intent createLaunchIntent(Tracks tracks, int playIndex) {
        Intent intentLaunch = PlayerActivity.getCallingIntent(
                getInstrumentation().getTargetContext(),
                tracks,
                playIndex
        );

        return intentLaunch;
    }

    // test case block start
    public void testAttachedFragments() throws Exception {
        // arrange
        initTestView(COLDPLAY_TOP_TRACKS_OBJECT, 0);

        // act

        // assert
        assertNotNull(getActivity().getSupportFragmentManager().findFragmentByTag(PlayerFragment.TAG));
    }
    // test case block end
}