package com.proevan.spotifystreamer.presenter;

import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;

public interface TracksPresenter {

    void onViewCreated(String artistId);
    void loadTracks(String artistId);
    void onTrackItemClick(TrackListAdapter adapter, int index);
}
