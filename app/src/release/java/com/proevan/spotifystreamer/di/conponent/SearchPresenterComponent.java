package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.SearchPresenterModule;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.view.SearchPageView;
import com.proevan.spotifystreamer.view.activity.MainActivity;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SearchPresenterModule.class,
        SpotifyServiceModule.class
})
public interface SearchPresenterComponent {

    void inject(MainActivity activity);

    void inject(SearchFragment fragment);

    class Initializer {

        public static SearchPresenterComponent init(SearchPageView searchPageView) {
            return DaggerSearchPresenterComponent.builder()
                    .searchPresenterModule(new SearchPresenterModule(searchPageView))
                    .build();
        }
    }

}
