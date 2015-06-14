package com.proevan.spotifystreamer.di.uitestcase.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.view.activity.TracksActivity;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

public class TracksActivityTestCase extends ActivityInstrumentationTestCase2<TracksActivity> {

    @Inject
    protected SpotifyService mMockSpotifyService;

    public TracksActivityTestCase() {
        super(TracksActivity.class);
    }
}
