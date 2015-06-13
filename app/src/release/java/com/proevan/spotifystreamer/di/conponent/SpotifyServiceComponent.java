package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SpotifyServiceModule.class})
public interface SpotifyServiceComponent {

    void inject(MainPresenterImpl presenter);

    class Initializer {

        public static SpotifyServiceComponent init() {
            return DaggerSpotifyServiceComponent.builder()
                    .spotifyServiceModule(new SpotifyServiceModule())
                    .build();
        }
    }

}
