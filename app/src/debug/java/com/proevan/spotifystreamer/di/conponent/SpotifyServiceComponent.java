package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.ArtistSearchResultActivityTestCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SpotifyServiceModule.class})
public interface SpotifyServiceComponent {

    void inject(MainPresenterImpl presenter);

    void inject(ArtistSearchResultActivityTestCase testCase);

    class Initializer {

        private static SpotifyServiceComponent sInstance;

        public static SpotifyServiceComponent init() {
            if (sInstance == null) {
                sInstance = DaggerSpotifyServiceComponent.builder()
                        .spotifyServiceModule(new SpotifyServiceModule())
                        .build();
            }

            return sInstance;
        }

        public static SpotifyServiceComponent recreate() {
            sInstance = null;

            return init();
        }
    }

}
