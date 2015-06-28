package com.proevan.spotifystreamer.view.fragment;

import android.support.test.espresso.action.ViewActions;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.conponent.PlayerPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.fragment.PlayerFragmentTestCase;
import com.proevan.spotifystreamer.model.TrackItem;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;
import static org.hamcrest.Matchers.not;

public class PlayerFragmentTest extends PlayerFragmentTestCase {

    public void setUp() throws Exception {
        super.setUp();
        initTestView(TrackItem.convertFromTracks(COLDPLAY_TOP_TRACKS_OBJECT.tracks), 0);
        PlayerPresenterComponent.Initializer.getInstance().inject(this);
    }

    private void initTestView(List<TrackItem> trackItems, int playIndex) throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        getActivity().addFragment(PlayerFragment.newInstance(trackItems, playIndex), PlayerFragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
    }

    // test case block start
    public void testLayout() throws Exception {
        // arrange
        TrackItem firstTrackItem = TrackItem.convertFromTracks(COLDPLAY_TOP_TRACKS_OBJECT.tracks).get(0);
        String albumName = firstTrackItem.getAlbumName();
        String trackName = firstTrackItem.getTrackName();
        String durationText = PlayerFragment.durationToString(firstTrackItem.getDurationInMillisecond().intValue());

        // act

        // assert
        onView(withText(trackName))
                .check(matches(isDisplayed()));
        onView(withText(albumName))
                .check(matches(isDisplayed()));
        onView(withText(durationText))
                .check(matches(isDisplayed()));
        onView(withId(R.id.current_time))
                .check(matches(isDisplayed()));
        onView(withId(R.id.seekbar))
                .check(matches(isDisplayed()));
        for (String artistName : firstTrackItem.getArtistNameList())
            onView(withText(artistName))
                    .check(matches(isDisplayed()));
        onView(withId(R.id.play_btn))
                .check(matches(isDisplayed()));
        onView(withId(R.id.pause_btn))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.next_btn))
                .check(matches(isDisplayed()));
        onView(withId(R.id.previous_btn))
                .check(matches(isDisplayed()));
    }

    public void testClickPlayButton() throws Exception {
        // arrange

        // act
        onView(withId(R.id.play_btn))
                .perform(click());

        // assert
        onView(withId(R.id.play_btn))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.pause_btn))
                .check(matches(isDisplayed()));
    }

    public void testClickPauseButton() throws Exception {
        // arrange

        // act
        onView(withId(R.id.play_btn))
                .perform(click());
        onView(withId(R.id.pause_btn))
                .perform(click());

        // assert
        onView(withId(R.id.play_btn))
                .check(matches(isDisplayed()));
        onView(withId(R.id.pause_btn))
                .check(matches(not(isDisplayed())));
    }
    // test case block end
}