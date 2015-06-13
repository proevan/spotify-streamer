package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.MainPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import retrofit.RestAdapter;

@Module
public class MainPresenterModule {

    private MainPageView mMainPageView;

    public MainPresenterModule(MainPageView mainPageView) {
        mMainPageView = mainPageView;
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mMainPageView, spotifyService);
    }

    @Provides
    @Singleton
    SpotifyService provideSpotifyService(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                .build();

        return restAdapter.create(SpotifyService.class);
    }
}
