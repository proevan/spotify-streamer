package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.di.module.TracksPresenterModule;
import com.proevan.spotifystreamer.view.TracksView;
import com.proevan.spotifystreamer.view.activity.TracksActivity;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TracksPresenterModule.class,
        SpotifyServiceModule.class
})
public interface TracksPresenterComponent {

    void inject(TracksActivity activity);

    void inject(TracksFragment fragment);

    class Initializer {

        public static TracksPresenterComponent init(TracksView tracksView) {
            return DaggerTracksPresenterComponent.builder()
                    .tracksPresenterModule(new TracksPresenterModule(tracksView))
                    .build();
        }
    }

}
