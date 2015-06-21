package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.impl.TracksPresenterImpl;
import com.proevan.spotifystreamer.view.TracksPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import retrofit.RestAdapter;

@Module
public class TracksPresenterModule {

    private TracksPageView mTracksPageView;

    public TracksPresenterModule(TracksPageView tracksPageView) {
        mTracksPageView = tracksPageView;
    }

    @Provides
    @Singleton
    TracksPresenter provideTracksPresenter(SpotifyService spotifyService){
        return new TracksPresenterImpl(mTracksPageView, spotifyService);
    }
}
