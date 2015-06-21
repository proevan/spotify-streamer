package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.storyteller.SpotifyServiceStoryTeller;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;
import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.view.TracksPageView;

import org.hamcrest.Matcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import kaaes.spotify.webapi.android.models.Track;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.mock.MockArtistsPager.COLDPLAY_SEARCH_RESULT_PAGER;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACKS;
import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACK_2;
import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;
import static com.proevan.spotifystreamer.di.mock.MockTracks.EMPTY_TOP_TRACKS_OBJECT;
import static com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule.*;
import static com.proevan.spotifystreamer.macher.CustomMatcher.isImageTheSame;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class TracksActivityTest extends TracksActivityTestCase {

    private static final String FAKE_ARTIST_ID = "fakeColdplayId";
    private static final String FAKE_ARTIST_NAME = "Coldplay";

    private SpotifyServiceStoryTeller mSpotifyServiceStoryTeller;

    private void setUp(String artistId, String artistName) throws Exception {
        initTestView(artistId, artistName);
        TracksPresenterComponent.Initializer.init(getActivity()).inject(this);
        mSpotifyServiceStoryTeller = new SpotifyServiceStoryTeller(mMockSpotifyService);
    }

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

    // test case block start
    public void testLayoutActionBar() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID, FAKE_ARTIST_NAME);

        // act

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText("Coldplay"))
                .check(matches(isDisplayed()));
    }

    public void testNoResult() throws Exception {
        // arrange
        setUp(NO_RESULT_STRING_PARAM, FAKE_ARTIST_NAME);

        // act

        // assert
        onView(withText(R.string.no_result))
                .check(matches(isDisplayed()));
    }

    public void testTrackListLayout() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID, FAKE_ARTIST_NAME);

        // act

        // assert
        for (Track coldplayTopTrack : COLDPLAY_TOP_TRACKS)
            onView(withId(R.id.recycler_view))
                    .perform(scrollTo(withTrackAndAlbumNameItem(coldplayTopTrack.name, coldplayTopTrack.album.name)));
    }

    public void testTrackItemImagePlaceHolder() throws Exception {
        // arrange
        setUp(FAKE_ARTIST_ID, FAKE_ARTIST_NAME);

        // act

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(withId(R.id.image), withParent(withTrackAndAlbumNameItem(COLDPLAY_TOP_TRACK_2.name, COLDPLAY_TOP_TRACK_2.album.name))))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }
    // test case block end

    private Matcher<View> withTrackAndAlbumNameItem(String trackName, String albumName) {
        return withChild(withChild(allOf(withText(trackName), hasSibling(withText(albumName)))));
    }
}