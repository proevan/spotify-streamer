package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.MainPageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

import static org.mockito.Mockito.mock;

@Module
public class TestMainPresenterModule {

    private MainPageView mMainPageView;
    private SpotifyService mMockSpotifyService;

    public TestMainPresenterModule(MainPageView mainPageView) {
        mMainPageView = mainPageView;
        mMockSpotifyService = mock(SpotifyService.class);
    }

    @Provides
    @Singleton
    MainPresenter provideTestMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mMainPageView, spotifyService);
    }

    @Provides
    @Singleton
    SpotifyService provideTestSpotifyService(){
        return mMockSpotifyService;
    }
}
