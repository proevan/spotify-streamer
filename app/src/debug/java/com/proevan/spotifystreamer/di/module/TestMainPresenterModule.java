package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.SearchPageView;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyService;

import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Module
public class TestMainPresenterModule {

    public SearchPageView mSearchPageView;

    public TestMainPresenterModule(SearchPageView searchPageView) {
        mSearchPageView = searchPageView;
    }

    @Provides
    @Singleton
    MainPresenter provideTestMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mSearchPageView, spotifyService);
    }
}
