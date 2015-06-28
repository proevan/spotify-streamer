package com.proevan.spotifystreamer.di.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

public class MockArtist {

    public static final Artist COLDPLAY_SEARCH_RESULT_1 = generateFakeArtistWithOneImage("Coldplay", "https://i.scdn.co/image/99afd5b3e7ce4b82fdc007dc5ed8dfe0806f6fe2", "fakeId1");
    public static final Artist COLDPLAY_SEARCH_RESULT_2 = generateFakeArtistWithOneImage("Coldplay & Lele", null, "fakeId2");
    public static final Artist COLDPLAY_SEARCH_RESULT_3 = generateFakeArtistWithOneImage("Various Artists - Coldplay Tribute", null, "fakeId3");
    public static final Artist COLDPLAY_SEARCH_RESULT_4 = generateFakeArtistWithOneImage("Karaoke - Coldplay", null, "fakeId4");
    public static final Artist COLDPLAY_SEARCH_RESULT_5 = generateFakeArtistWithOneImage("ColdPlay Wu", "https://i.scdn.co/image/d29c1e0ea74efccf7ec2634b5c2d2bc42f522a21", "fakeId5");
    public static final Artist COLDPLAY_SEARCH_RESULT_6 = generateFakeArtistWithOneImage("Coldplay Metal Tribute", null, "fakeId6");
    public static final Artist COLDPLAY_SEARCH_RESULT_7 = generateFakeArtistWithOneImage("Éxito De Coldplay", null, "fakeId7");
    public static final Artist COLDPLAY_SEARCH_RESULT_8 = generateFakeArtistWithOneImage("Princess of China (In The Style of Coldplay& Rihan", null, "fakeId8");

    public static final List<Artist> COLDPLAY_SEARCH_RESULTS =
            Arrays.asList(
                    COLDPLAY_SEARCH_RESULT_1,
                    COLDPLAY_SEARCH_RESULT_2,
                    COLDPLAY_SEARCH_RESULT_3,
                    COLDPLAY_SEARCH_RESULT_4,
                    COLDPLAY_SEARCH_RESULT_5,
                    COLDPLAY_SEARCH_RESULT_6,
                    COLDPLAY_SEARCH_RESULT_7,
                    COLDPLAY_SEARCH_RESULT_8
            );

    private static Artist generateFakeArtistWithOneImage(String name, String imageUrl, String id) {
        Artist artist = new Artist();
        artist.id = id;
        artist.name = name;
        List<Image> images = new ArrayList<>();
        if (imageUrl != null) {
            Image image = new Image();
            image.url = imageUrl;
            images.add(image);
        }
        artist.images = images;

        return artist;
    }
}
