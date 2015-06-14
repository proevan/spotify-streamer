package com.proevan.spotifystreamer.view;

import android.os.Bundle;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public interface TracksPageView {

    public void setSubtitle(String subtitle);
    public void setTrackItems(List<Track> tracks);
    public void clearTrackItems();
    public void openPlayerPage(Bundle bundle);
    public void showMessage(String message);
    public void closePage();
    public void showNoDataMessage();
    public void hideNoDataMessage();
    public void showLoadingView();
    public void hideLoadingView();

    // for testing
    public void setTestMode(boolean isTestMode);
}
