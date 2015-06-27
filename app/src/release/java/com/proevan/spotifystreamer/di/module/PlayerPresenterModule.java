package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.PlayerPresenter;
import com.proevan.spotifystreamer.presenter.impl.PlayerPresenterImpl;
import com.proevan.spotifystreamer.view.PlayerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

@Module
public class PlayerPresenterModule {

    private PlayerView mPlayerView;

    public PlayerPresenterModule(PlayerView playerView) {
        mPlayerView = playerView;
    }

    @Provides
    @Singleton
    PlayerPresenter providePlayerPresenter(SpotifyService spotifyService){
        return new PlayerPresenterImpl(mPlayerView, spotifyService);
    }
}
