package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.PlayerPresenterModule;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.view.PlayerView;
import com.proevan.spotifystreamer.view.activity.PlayerActivity;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        PlayerPresenterModule.class,
        SpotifyServiceModule.class
})
public interface PlayerPresenterComponent {

    void inject(PlayerActivity activity);

    void inject(PlayerFragment fragment);

    class Initializer {

        public static PlayerPresenterComponent init(PlayerView playerView) {
            return DaggerPlayerPresenterComponent.builder()
                    .playerPresenterModule(new PlayerPresenterModule(playerView))
                    .build();
        }
    }

}
