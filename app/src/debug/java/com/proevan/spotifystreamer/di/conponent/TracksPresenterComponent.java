package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestSpotifyServiceModule;
import com.proevan.spotifystreamer.di.module.TestTracksPresenterModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;
import com.proevan.spotifystreamer.di.uitestcase.fragment.TracksFragmentTestCase;
import com.proevan.spotifystreamer.view.TracksPageView;
import com.proevan.spotifystreamer.view.activity.TracksActivity;
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
        private static TracksPageView sTracksPageView;

        public static TracksPresenterComponent init(TracksPageView tracksPageView) {
            if (sInstance == null || !sTracksPageView.equals(tracksPageView)) {
                sTracksPageView = tracksPageView;
                sInstance = DaggerTracksPresenterComponent.builder()
                        .testTracksPresenterModule(new TestTracksPresenterModule(tracksPageView))
                        .build();
            }

            return sInstance;
        }

        public static TracksPresenterComponent getInstance() {
            return sInstance;
        }
    }

}
