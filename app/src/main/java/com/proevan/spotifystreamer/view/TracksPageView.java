package com.proevan.spotifystreamer.view;

import android.os.Bundle;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public interface TracksPageView {

    void setSubtitle(String subtitle);
    void setTrackItems(List<Track> tracks);
    void clearTrackItems();
    void openPlayerPage(Bundle bundle);
    void showMessage(String message);
    void showNoDataMessage();
    void hideNoDataMessage();
    void showLoadingView();
    void hideLoadingView();
}
