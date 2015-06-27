package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.SearchPresenter;
import com.proevan.spotifystreamer.presenter.impl.SearchPresenterImpl;
import com.proevan.spotifystreamer.view.SearchPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

@Module
public class SearchPresenterModule {

    private SearchPageView mSearchPageView;

    public SearchPresenterModule(SearchPageView searchPageView) {
        mSearchPageView = searchPageView;
    }

    @Provides
    @Singleton
    SearchPresenter provideMainPresenter(SpotifyService spotifyService){
        return new SearchPresenterImpl(mSearchPageView, spotifyService);
    }
}
