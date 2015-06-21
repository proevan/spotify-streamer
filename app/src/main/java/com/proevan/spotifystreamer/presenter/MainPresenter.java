package com.proevan.spotifystreamer.presenter;

import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;

public interface MainPresenter {

    void onSearchTextChange(CharSequence text);
    void onSearchResultItemClick(ArtistListAdapter adapter, int position);
}
