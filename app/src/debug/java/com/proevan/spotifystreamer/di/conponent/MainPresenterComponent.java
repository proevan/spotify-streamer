package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestMainPresenterModule;
import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;
import com.proevan.spotifystreamer.view.MainPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import kaaes.spotify.webapi.android.SpotifyService;

@Singleton
@Component(modules = {
        TestMainPresenterModule.class,
        TestSpotifyServiceModule.class
})
public interface MainPresenterComponent {

    void inject(MainActivity activity);

    void inject(MainActivityTestCase testCase);

    class Initializer {

        private static MainPresenterComponent sInstance;
        private static MainPageView sMainPageView;

        public static MainPresenterComponent init(MainPageView mainPageView) {
            if (sInstance == null || !sMainPageView.equals(mainPageView)) {
                sMainPageView = mainPageView;
                sInstance = DaggerMainPresenterComponent.builder()
                        .testMainPresenterModule(new TestMainPresenterModule(mainPageView))
                        .build();
            }

            return sInstance;
        }

    }

}
