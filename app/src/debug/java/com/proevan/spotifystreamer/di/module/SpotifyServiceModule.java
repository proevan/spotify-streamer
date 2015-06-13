package com.proevan.spotifystreamer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

import static org.mockito.Mockito.mock;

@Module
public class SpotifyServiceModule {

    private SpotifyService mMockSpotifyService;

    public SpotifyServiceModule() {
        mMockSpotifyService = mock(SpotifyService.class);
    }

    @Provides
    @Singleton
    SpotifyService provideTestSpotifyService(){
        return mMockSpotifyService;
    }
}
