package com.proevan.spotifystreamer.view.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.fragment.TracksFragmentTestCase;

import org.hamcrest.Matcher;

import kaaes.spotify.webapi.android.models.Track;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACKS;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACK_2;
import static com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule.NO_RESULT_STRING_PARAM;
import static com.proevan.spotifystreamer.macher.CustomMatcher.isImageTheSame;
import static org.hamcrest.Matchers.allOf;

public class TracksFragmentTest extends TracksFragmentTestCase {

    private static final String FAKE_ARTIST_ID = "fakeColdplayId";

    private void setUp(String artistId) throws Exception {
        initTestView(artistId);
        TracksPresenterComponent.Initializer.getInstance().inject(this);
    }

    private void initTestView(String artistId) throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        getActivity().addFragment(TracksFragment.newInstance(artistId), TracksFragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
    }

    // test case block start
    public void testNoResult() throws Exception {
        // arrange
        setUp(NO_RESULT_STRING_PARAM);

        // act

        // assert
        onView(withText(R.string.no_result))
                .check(matches(isDisplayed()));
    }

    public void testTrackListLayout() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID);

        // act

        // assert
        for (Track coldplayTopTrack : COLDPLAY_TOP_TRACKS)
            onView(withId(R.id.recycler_view))
                    .perform(scrollTo(withTrackAndAlbumNameItem(coldplayTopTrack.name, coldplayTopTrack.album.name)));
    }

    public void testTrackItemImagePlaceHolder() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID);

        // act

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(withId(R.id.image), withParent(withTrackAndAlbumNameItem(COLDPLAY_TOP_TRACK_2.name, COLDPLAY_TOP_TRACK_2.album.name))))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }

    public void testTrackItemClick() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID);

        // act
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        // assert
        assertTrue(getActivity().isMethodInvokedOpenPlayerView());
    }
    // test case block end

    private Matcher<View> withTrackAndAlbumNameItem(String trackName, String albumName) {
        return withChild(withChild(allOf(withText(trackName), hasSibling(withText(albumName)))));
    }
}