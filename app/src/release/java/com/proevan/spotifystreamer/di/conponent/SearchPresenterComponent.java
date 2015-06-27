package com.proevan.spotifystreamer.di.conponent;

import com.proevan.spotifystreamer.di.module.SearchPresenterModule;
import com.proevan.spotifystreamer.di.module.SpotifyServiceModule;
import com.proevan.spotifystreamer.view.SearchView;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        SearchPresenterModule.class,
        SpotifyServiceModule.class
})
public interface SearchPresenterComponent {

    void inject(SearchFragment fragment);

    class Initializer {

        public static SearchPresenterComponent init(SearchView searchView) {
            return DaggerSearchPresenterComponent.builder()
                    .searchPresenterModule(new SearchPresenterModule(searchView))
                    .build();
        }
    }

}
