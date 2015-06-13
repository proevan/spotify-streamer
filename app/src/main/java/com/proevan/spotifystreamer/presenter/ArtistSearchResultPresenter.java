package com.proevan.spotifystreamer.presenter;

public interface ArtistSearchResultPresenter {

    public void onSearchTextChange(CharSequence text);
    public void onSearchResultItemClick(int position);
}
