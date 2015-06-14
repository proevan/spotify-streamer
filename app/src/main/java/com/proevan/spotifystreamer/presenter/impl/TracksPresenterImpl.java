package com.proevan.spotifystreamer.presenter.impl;

import android.os.Bundle;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.view.TracksPageView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_NAME;

public class TracksPresenterImpl implements TracksPresenter {

    private static final String EMPTY_STRING = "";

    private TracksPageView mTracksPageView;
    private SpotifyService mSpotifyService;

    @Inject
    public TracksPresenterImpl(TracksPageView tracksPageView, SpotifyService spotifyService) {
        mTracksPageView = tracksPageView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void parseIntentBundle(Bundle extras) {
        if (extras != null) {
            String artistName = extras.getString(ARTIST_NAME.name(), EMPTY_STRING);
            mTracksPageView.setSubtitle(artistName);
        }
    }
}
