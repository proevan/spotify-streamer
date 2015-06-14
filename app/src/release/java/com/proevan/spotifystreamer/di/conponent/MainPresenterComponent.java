package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.MainPresenterModule;
import com.proevan.spotifystreamer.view.MainPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainPresenterModule.class})
public interface MainPresenterComponent {

    void inject(MainActivity activity);

    class Initializer {

        public static MainPresenterComponent init(MainPageView mainPageView) {
            return DaggerMainPresenterComponent.builder()
                    .mainPresenterModule(new MainPresenterModule(mainPageView))
                    .build();
        }
    }

}
