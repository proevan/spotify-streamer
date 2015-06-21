package com.proevan.spotifystreamer.di.mock;

import java.util.Collections;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;

import static com.proevan.spotifystreamer.di.mock.MockArtist.*;

public class MockArtistsPager {

    public static final ArtistsPager COLDPLAY_SEARCH_RESULT_PAGER = generateFakeArtistPager(COLDPLAY_SEARCH_RESULTS);
    public static final ArtistsPager EMPTY_SEARCH_RESULT_PAGER = generateFakeArtistPager(Collections.EMPTY_LIST);

    private static ArtistsPager generateFakeArtistPager(List<Artist> artists) {
        ArtistsPager artistsPager = new ArtistsPager();
        Pager<Artist> artistPager = new Pager<>();
        artistPager.items = artists;
        artistsPager.artists = artistPager;

        return artistsPager;
    }
}
