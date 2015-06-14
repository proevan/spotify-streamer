package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.TestTracksPresenterModule;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;
import com.proevan.spotifystreamer.view.TracksPageView;
import com.proevan.spotifystreamer.view.activity.TracksActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestTracksPresenterModule.class})
public interface TracksPresenterComponent {

    void inject(TracksActivity activity);

    void inject(TracksActivityTestCase testCase);

    class Initializer {

        public static TracksPresenterComponent instance;

        public static TracksPresenterComponent init(TracksPageView tracksPageView) {
            TracksPresenterComponent component = DaggerTracksPresenterComponent.builder()
                        .testTracksPresenterModule(new TestTracksPresenterModule(tracksPageView))
                        .build();
            instance = component;

            return component;
        }
    }

}
