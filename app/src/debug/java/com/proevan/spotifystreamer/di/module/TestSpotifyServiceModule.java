package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.di.storyteller.SpotifyServiceStoryTeller;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

import static com.proevan.spotifystreamer.di.mock.MockTracks.COLDPLAY_TOP_TRACKS_OBJECT;
import static com.proevan.spotifystreamer.di.mock.MockTracks.EMPTY_TOP_TRACKS_OBJECT;
import static org.mockito.Mockito.mock;

@Module
public class TestSpotifyServiceModule {

    public static final String NO_RESULT_STRING_PARAM = "noResult";

    private SpotifyService mMockSpotifyService;
    private SpotifyServiceStoryTeller mSpotifyServiceStoryTeller;

    public TestSpotifyServiceModule() {
        mMockSpotifyService = mock(SpotifyService.class);
        mSpotifyServiceStoryTeller = new SpotifyServiceStoryTeller(mMockSpotifyService);
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
