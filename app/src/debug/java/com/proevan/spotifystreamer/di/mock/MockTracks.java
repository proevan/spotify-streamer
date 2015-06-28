package com.proevan.spotifystreamer.di.mock;

import java.util.Collections;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

import static com.proevan.spotifystreamer.di.mock.MockTrack.COLDPLAY_TOP_TRACKS;

public class MockTracks {

    public static final Tracks COLDPLAY_TOP_TRACKS_OBJECT = generateFakeTracks(COLDPLAY_TOP_TRACKS);
    public static final Tracks EMPTY_TOP_TRACKS_OBJECT = generateFakeTracks(Collections.EMPTY_LIST);

    private static Tracks generateFakeTracks(List<Track> tracks) {
        Tracks tracksObject = new Tracks();
        tracksObject.tracks = tracks;

        return tracksObject;
    }
}
