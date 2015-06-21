package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.impl.TracksPresenterImpl;
import com.proevan.spotifystreamer.view.TracksPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

@Module
public class TestTracksPresenterModule {

    private TracksPageView mTracksPageView;

    public TestTracksPresenterModule(TracksPageView tracksPageView) {
        mTracksPageView = tracksPageView;
    }

    @Provides
    @Singleton
    TracksPresenter provideTestTracksPresenter(SpotifyService spotifyService){
        return new TracksPresenterImpl(mTracksPageView, spotifyService);
    }
}
