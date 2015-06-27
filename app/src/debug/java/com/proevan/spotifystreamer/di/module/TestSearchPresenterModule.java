package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.SearchPresenter;
import com.proevan.spotifystreamer.presenter.impl.SearchPresenterImpl;
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
public class TestSearchPresenterModule {

    public SearchPageView mSearchPageView;

    public TestSearchPresenterModule(SearchPageView searchPageView) {
        mSearchPageView = searchPageView;
    }

    @Provides
    @Singleton
    SearchPresenter provideTestMainPresenter(SpotifyService spotifyService){
        return new SearchPresenterImpl(mSearchPageView, spotifyService);
    }
}
