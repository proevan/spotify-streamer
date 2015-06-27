package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestSearchPresenterModule;
import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;
import com.proevan.spotifystreamer.di.uitestcase.fragment.SearchFragmentTestCase;
import com.proevan.spotifystreamer.view.SearchPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TestSearchPresenterModule.class,
        TestSpotifyServiceModule.class
})
public interface SearchPresenterComponent {

    void inject(SearchFragment fragment);
    void inject(MainActivity activity);

    void inject(MainActivityTestCase testCase);
    void inject(SearchFragmentTestCase testCase);

    class Initializer {

        private static SearchPresenterComponent sInstance;
        private static SearchPageView sSearchPageView;

        public static SearchPresenterComponent init(SearchPageView searchPageView) {
            if (sInstance == null || !sSearchPageView.equals(searchPageView)) {
                sSearchPageView = searchPageView;
                sInstance = DaggerSearchPresenterComponent.builder()
                        .testSearchPresenterModule(new TestSearchPresenterModule(searchPageView))
                        .build();
            }

            return sInstance;
        }

        public static SearchPresenterComponent getInstance() {
            return sInstance;
        }

    }

}
