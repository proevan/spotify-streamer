package com.proevan.spotifystreamer.presenter;

import android.os.Bundle;

import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;

public interface TracksPresenter {

    public void onCreateView(Bundle extras);
    public void onTrackItemClick(TrackListAdapter adapter, int index);
}
