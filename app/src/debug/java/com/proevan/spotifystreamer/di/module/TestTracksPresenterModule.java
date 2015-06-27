package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.impl.TracksPresenterImpl;
import com.proevan.spotifystreamer.view.TracksView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

@Module
public class TestTracksPresenterModule {

    private TracksView mTracksView;

    public TestTracksPresenterModule(TracksView tracksView) {
        mTracksView = tracksView;
    }

    @Provides
    @Singleton
    TracksPresenter provideTestTracksPresenter(SpotifyService spotifyService){
        return new TracksPresenterImpl(mTracksView, spotifyService);
    }
}
