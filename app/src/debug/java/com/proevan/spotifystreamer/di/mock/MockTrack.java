package com.proevan.spotifystreamer.di.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

public class MockTrack {

    public static final Track COLDPLAY_TOP_TRACK_1 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "A Sky Full of Stars",
            "Ghost Stories",
            "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_2 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "The Scientist",
            "A Rush Of Blood To The Head",
            null,
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_3 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Yellow",
            "Parachutes",
            "https://i.scdn.co/image/af4812064c4963f5ce4e630cbf70b540ce3f60dc",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_4 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Fix You",
            "X & Y",
            "https://i.scdn.co/image/0306577597d1fa09d10fda6753b8b77faabfa579",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_5 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Paradise",
            "Mylo Xyloto",
            "https://i.scdn.co/image/6e04fd4fcc5b107f93dc1860941dc240e79e1295",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_6 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Magic",
            "Ghost Stories",
            "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_7 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Viva La Vida",
            "Viva La Vida Or Death And All His Friends",
            "https://i.scdn.co/image/28967fae3eaf33b0570b53cf621390204bf050d4",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );
    public static final Track COLDPLAY_TOP_TRACK_8 = generateFakeTrackWithOneArtistAndAlbumImage(
            "Coldplay",
            "Clocks",
            "A Rush Of Blood To The Head",
            "https://i.scdn.co/image/4a408081438be137b71b1e8afafb98795d53e9d6",
            150000l,
            "https://p.scdn.co/mp3-preview/3742af306537513a4f446d7c8f9cdb1cea6e36d1"
    );

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

    private static Track generateFakeTrackWithOneArtistAndAlbumImage(
            String artistName,
            String trackName,
            String albumName,
            String imageUrl,
            Long duration,
            String previewUrl) {
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
        List<ArtistSimple> artistSimples = new ArrayList<>();
        ArtistSimple artistSimple = new ArtistSimple();
        artistSimple.name = artistName;
        artistSimples.add(artistSimple);
        track.artists = artistSimples;
        album.images = images;
        track.album = album;
        track.duration_ms = duration;

        return track;
    }
}
