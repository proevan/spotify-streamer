package com.proevan.spotifystreamer.view;

import com.proevan.spotifystreamer.model.TrackItem;

public interface PlayerView {

    void setTrackItem(TrackItem trackItem);
    void switchToPlayButton();
    void switchToPauseButton();
    void setTrackDuration(int durationInMillisecond);
    void setPlayingProgress(int progressInMillisecond);
}
