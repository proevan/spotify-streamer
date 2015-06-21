package com.proevan.spotifystreamer.di.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

public class MockTrack {

    public static final Track COLDPLAY_TOP_TRACK_1 = generateFakeTrackWithOneAlbumImage("A Sky Full of Stars", "Ghost Stories", "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581");
    public static final Track COLDPLAY_TOP_TRACK_2 = generateFakeTrackWithOneAlbumImage("The Scientist", "A Rush Of Blood To The Head", null);
    public static final Track COLDPLAY_TOP_TRACK_3 = generateFakeTrackWithOneAlbumImage("Yellow", "Parachutes", "https://i.scdn.co/image/af4812064c4963f5ce4e630cbf70b540ce3f60dc");
    public static final Track COLDPLAY_TOP_TRACK_4 = generateFakeTrackWithOneAlbumImage("Fix You", "X & Y", "https://i.scdn.co/image/0306577597d1fa09d10fda6753b8b77faabfa579");
    public static final Track COLDPLAY_TOP_TRACK_5 = generateFakeTrackWithOneAlbumImage("Paradise", "Mylo Xyloto", "https://i.scdn.co/image/6e04fd4fcc5b107f93dc1860941dc240e79e1295");
    public static final Track COLDPLAY_TOP_TRACK_6 = generateFakeTrackWithOneAlbumImage("Magic", "Ghost Stories", "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581");
    public static final Track COLDPLAY_TOP_TRACK_7 = generateFakeTrackWithOneAlbumImage("Viva La Vida", "Viva La Vida Or Death And All His Friends", "https://i.scdn.co/image/28967fae3eaf33b0570b53cf621390204bf050d4");
    public static final Track COLDPLAY_TOP_TRACK_8 = generateFakeTrackWithOneAlbumImage("Clocks", "A Rush Of Blood To The Head", "https://i.scdn.co/image/4a408081438be137b71b1e8afafb98795d53e9d6");

    public static final List<Track> COLDPLAY_TOP_TRACKS =
            Arrays.asList(
                    COLDPLAY_TOP_TRACK_1,
                    COLDPLAY_TOP_TRACK_2,
                    COLDPLAY_TOP_TRACK_3,
                    COLDPLAY_TOP_TRACK_4,
                    COLDPLAY_TOP_TRACK_5,
                    COLDPLAY_TOP_TRACK_6,
                    COLDPLAY_TOP_TRACK_7,
                    COLDPLAY_TOP_TRACK_8
            );

    private static Track generateFakeTrackWithOneAlbumImage(String trackName, String albumName, String imageUrl) {
        Track track = new Track();
        track.name = trackName;
        Album album = new Album();
        album.name = albumName;
        List<Image> images = new ArrayList<>();
        if (imageUrl != null) {
            Image image = new Image();
            image.url = imageUrl;
            images.add(image);
        }
        album.images = images;
        track.album = album;

        return track;
    }
}
