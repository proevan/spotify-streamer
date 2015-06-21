package com.proevan.spotifystreamer.view;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

public interface MainPageView {

    void setResultItems(List<Artist> artists);
    void clearSearchResult();
    void openTracksPage(String artistId, String artistName);
    void showMessage(String message);
    void showNoDataMessage();
    void hideNoDataMessage();
    void showLoadingView();
    void hideLoadingView();
}
