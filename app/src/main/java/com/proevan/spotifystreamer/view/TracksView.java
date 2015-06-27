package com.proevan.spotifystreamer.view;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public interface TracksView {

    void setTrackItems(List<Track> tracks);
    void openPlayerView(Tracks tracks, int selectIndex);
    void showMessage(String message);
    void showNoDataMessage();
    void hideNoDataMessage();
    void showLoadingView();
    void hideLoadingView();
}
