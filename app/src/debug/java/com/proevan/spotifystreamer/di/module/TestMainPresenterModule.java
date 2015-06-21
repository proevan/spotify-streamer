package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.MainPageView;

import org.hamcrest.CoreMatchers;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;

import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Module
public class TestMainPresenterModule {

    public MainPageView mMainPageView;

    public TestMainPresenterModule(MainPageView mainPageView) {
        mMainPageView = mainPageView;
    }

    @Provides
    @Singleton
    MainPresenter provideTestMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mMainPageView, spotifyService);
    }
}
