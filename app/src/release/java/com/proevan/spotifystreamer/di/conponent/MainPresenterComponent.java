package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.MainPresenterModule;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.view.SearchPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MainPresenterModule.class,
        SpotifyServiceModule.class
})
public interface MainPresenterComponent {

    void inject(MainActivity activity);

    void inject(SearchFragment fragment);

    class Initializer {

        public static MainPresenterComponent init(SearchPageView searchPageView) {
            return DaggerMainPresenterComponent.builder()
                    .mainPresenterModule(new MainPresenterModule(searchPageView))
                    .build();
        }
    }

}
