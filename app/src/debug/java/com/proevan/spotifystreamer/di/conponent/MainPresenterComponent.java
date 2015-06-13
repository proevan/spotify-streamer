package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestMainPresenterModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;
import com.proevan.spotifystreamer.view.MainPageView;
import com.proevan.spotifystreamer.view.impl.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestMainPresenterModule.class})
public interface MainPresenterComponent {

    void inject(MainActivity activity);

    void inject(MainActivityTestCase testCase);

    class Initializer {

        public static MainPresenterComponent instance;

        public static MainPresenterComponent init(MainPageView mainPageView) {
            MainPresenterComponent component = DaggerMainPresenterComponent.builder()
                        .testMainPresenterModule(new TestMainPresenterModule(mainPageView))
                        .build();
            instance = component;

            return component;
        }
    }

}
