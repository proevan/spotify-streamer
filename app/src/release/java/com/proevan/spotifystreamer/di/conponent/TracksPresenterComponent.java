package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.di.module.TracksPresenterModule;
import com.proevan.spotifystreamer.view.TracksPageView;
import com.proevan.spotifystreamer.view.activity.TracksActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TracksPresenterModule.class,
        SpotifyServiceModule.class
})
public interface TracksPresenterComponent {

    void inject(TracksActivity activity);

    class Initializer {

        public static TracksPresenterComponent init(TracksPageView tracksPageView) {
            return DaggerTracksPresenterComponent.builder()
                    .tracksPresenterModule(new TracksPresenterModule(tracksPageView))
                    .build();
        }
    }

}
