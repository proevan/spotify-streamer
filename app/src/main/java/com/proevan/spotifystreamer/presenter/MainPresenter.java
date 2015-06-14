package com.proevan.spotifystreamer.presenter;

import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;

public interface MainPresenter {

    public void onSearchTextChange(CharSequence text);
    public void onSearchResultItemClick(ArtistListAdapter adapter, int position);
}
