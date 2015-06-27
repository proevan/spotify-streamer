package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestMainPresenterModule;
import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;
import com.proevan.spotifystreamer.view.SearchPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TestMainPresenterModule.class,
        TestSpotifyServiceModule.class
})
public interface MainPresenterComponent {

    void inject(MainActivity activity);

    void inject(SearchFragment fragment);

    void inject(MainActivityTestCase testCase);

    class Initializer {

        private static MainPresenterComponent sInstance;
        private static SearchPageView sSearchPageView;

        public static MainPresenterComponent init(SearchPageView searchPageView) {
            if (sInstance == null || !sSearchPageView.equals(searchPageView)) {
                sSearchPageView = searchPageView;
                sInstance = DaggerMainPresenterComponent.builder()
                        .testMainPresenterModule(new TestMainPresenterModule(searchPageView))
                        .build();
            }

            return sInstance;
        }

    }

}
