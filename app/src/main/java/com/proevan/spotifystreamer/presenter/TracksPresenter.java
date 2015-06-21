package com.proevan.spotifystreamer.presenter;

import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;

public interface TracksPresenter {

    void loadTracks(String artistId);
    void onTrackItemClick(TrackListAdapter adapter, int index);
}
