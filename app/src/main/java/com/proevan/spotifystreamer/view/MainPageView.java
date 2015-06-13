package com.proevan.spotifystreamer.view;

import android.os.Bundle;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

public interface MainPageView {

    public void setResultItems(List<Artist> artists);
    public void clearSearchResult();
    public void openTracksPage(Bundle bundle);
    public void showMessage(String message);
}
