package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.module.TestTracksPresenterModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;
import com.proevan.spotifystreamer.di.uitestcase.fragment.TracksFragmentTestCase;
import com.proevan.spotifystreamer.view.TracksView;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        TestTracksPresenterModule.class,
        TestSpotifyServiceModule.class
})
public interface TracksPresenterComponent {

    void inject(TracksFragment fragment);

    void inject(TracksActivityTestCase testCase);
    void inject(TracksFragmentTestCase testCase);

    class Initializer {

        private static TracksPresenterComponent sInstance;
        private static TracksView sTracksView;

        public static TracksPresenterComponent init(TracksView tracksView) {
            if (sInstance == null || !sTracksView.equals(tracksView)) {
                sTracksView = tracksView;
                sInstance = DaggerTracksPresenterComponent.builder()
                        .testTracksPresenterModule(new TestTracksPresenterModule(tracksView))
                        .build();
            }

            return sInstance;
        }

        public static TracksPresenterComponent getInstance() {
            return sInstance;
        }
    }

}
