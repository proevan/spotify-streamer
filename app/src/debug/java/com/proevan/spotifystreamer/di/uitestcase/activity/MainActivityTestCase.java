package com.proevan.spotifystreamer.di.uitestcase.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.view.activity.MainActivity;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

public class MainActivityTestCase extends ActivityInstrumentationTestCase2<MainActivity> {

    @Inject
    protected SpotifyService mMockSpotifyService;

    public MainActivityTestCase() {
        super(MainActivity.class);
    }
}
