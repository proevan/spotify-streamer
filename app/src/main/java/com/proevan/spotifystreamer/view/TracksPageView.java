package com.proevan.spotifystreamer.view;

import android.os.Bundle;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

public interface TracksPageView {

    public void setTrackItems(List<Artist> artists);
    public void clearTrackItems();
    public void openPlayerPage(Bundle bundle);
    public void showMessage(String message);
    public void closePage();
}
