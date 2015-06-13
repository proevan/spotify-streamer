package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.ArtistSearchResultActivity;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SpotifyServiceModule.class})
public interface SpotifyServiceComponent {

    void inject(ArtistSearchResultActivity activity);

    class Initializer {

        public static SpotifyServiceComponent init() {
            return DaggerSpotifyServiceComponent.builder()
                    .spotifyServiceModule(new SpotifyServiceModule())
                    .build();
        }
    }

}
