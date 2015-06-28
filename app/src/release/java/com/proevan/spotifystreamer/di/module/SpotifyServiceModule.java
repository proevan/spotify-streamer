package com.proevan.spotifystreamer.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import retrofit.RestAdapter;

@Module
public class SpotifyServiceModule {

    @Provides
    @Singleton
    SpotifyService provideSpotifyService(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .build();

        return restAdapter.create(SpotifyService.class);
    }
}
