package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.di.storyteller.SpotifyServiceStoryTeller;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;
import static com.proevan.spotifystreamer.di.mock.MockTracks.EMPTY_TOP_TRACKS_OBJECT;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Module
public class TestSpotifyServiceModule {

    public static final String NO_RESULT_STRING_PARAM = "noResult";

    private SpotifyService mMockSpotifyService;
    private SpotifyServiceStoryTeller mSpotifyServiceStoryTeller;

    public TestSpotifyServiceModule() {
        mMockSpotifyService = mock(SpotifyService.class);
        mSpotifyServiceStoryTeller =
                new SpotifyServiceStoryTeller(mMockSpotifyService);
        mSpotifyServiceStoryTeller.stubGetArtistTopTrackSuccess(COLDPLAY_TOP_TRACKS_OBJECT);
        mSpotifyServiceStoryTeller.stubGetArtistTopTrackSuccess(NO_RESULT_STRING_PARAM, EMPTY_TOP_TRACKS_OBJECT);
    }

    @Provides
    @Singleton
    SpotifyService provideTestSpotifyService(){
        return mMockSpotifyService;
    }

    @Provides
    @Singleton
    SpotifyServiceStoryTeller provideTestSpotifyServiceStoryTeller(){
        return mSpotifyServiceStoryTeller;
    }
}
