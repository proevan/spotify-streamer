package com.proevan.spotifystreamer.presenter.impl;

import com.proevan.spotifystreamer.model.TrackItem;
import com.proevan.spotifystreamer.presenter.PlayerPresenter;
import com.proevan.spotifystreamer.view.PlayerView;

import java.util.List;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

public class PlayerPresenterImpl implements PlayerPresenter {

    private PlayerView mPlayerView;
    private SpotifyService mSpotifyService;

    @Inject
    public PlayerPresenterImpl(PlayerView playerView, SpotifyService spotifyService) {
        mPlayerView = playerView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onViewCreated(List<TrackItem> trackItems, int playIndex) {
        TrackItem currentTrackItem = trackItems.get(playIndex);
        mPlayerView.setTrackItem(currentTrackItem);
    }

    @Override
    public void onPlayButtonClick() {

    }

    @Override
    public void onPreviousButtonClick() {

    }

    @Override
    public void onNextButtonClick() {

    }

    @Override
    public void onScrubBarSelect() {

    }
}
