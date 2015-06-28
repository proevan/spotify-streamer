package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestPlayerPresenterModule;
import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.uitestcase.fragment.PlayerFragmentTestCase;
import com.proevan.spotifystreamer.view.PlayerView;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TestPlayerPresenterModule.class,
        TestSpotifyServiceModule.class
})
public interface PlayerPresenterComponent {

    void inject(PlayerFragment fragment);

    void inject(PlayerFragmentTestCase testCase);

    class Initializer {

        private static PlayerPresenterComponent sInstance;
        private static PlayerView sPlayerView;

        public static PlayerPresenterComponent init(PlayerView playerView) {
            if (sInstance == null || !sPlayerView.equals(playerView)) {
                sPlayerView = playerView;
                sInstance = DaggerPlayerPresenterComponent.builder()
                        .testPlayerPresenterModule(new TestPlayerPresenterModule(playerView))
                        .build();
            }

            return sInstance;
        }

        public static PlayerPresenterComponent getInstance() {
            return sInstance;
        }

    }

}
