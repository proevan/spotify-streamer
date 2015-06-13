package com.proevan.spotifystreamer.di.uitestcase.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.view.impl.MainActivity;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

public class ArtistSearchResultActivityTestCase extends ActivityInstrumentationTestCase2<MainActivity> {

    @Inject
    protected SpotifyService mMockSpotifyService;

    public ArtistSearchResultActivityTestCase() {
        super(MainActivity.class);
    }
}
