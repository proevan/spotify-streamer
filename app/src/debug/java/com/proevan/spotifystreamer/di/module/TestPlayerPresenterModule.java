package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.PlayerPresenter;
import com.proevan.spotifystreamer.presenter.impl.PlayerPresenterImpl;
import com.proevan.spotifystreamer.view.PlayerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestPlayerPresenterModule {

    private PlayerView mPlayerView;

    public TestPlayerPresenterModule(PlayerView playerView) {
        mPlayerView = playerView;
    }

    @Provides
    @Singleton
    PlayerPresenter provideTestPlayerPresenter(){
        return new PlayerPresenterImpl(mPlayerView);
    }
}
