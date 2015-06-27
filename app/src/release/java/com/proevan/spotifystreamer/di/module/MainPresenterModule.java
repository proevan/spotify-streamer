package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.SearchPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

@Module
public class MainPresenterModule {

    private SearchPageView mSearchPageView;

    public MainPresenterModule(SearchPageView searchPageView) {
        mSearchPageView = searchPageView;
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mSearchPageView, spotifyService);
    }
}
