package com.proevan.spotifystreamer.presenter;

import com.proevan.spotifystreamer.model.TrackItem;

import java.util.List;

public interface PlayerPresenter {

    void onViewCreated(List<TrackItem> trackItems, int playIndex);
    void onPlayButtonClick();
    void onPreviousButtonClick();
    void onNextButtonClick();
    void onScrubBarSelect();
}
