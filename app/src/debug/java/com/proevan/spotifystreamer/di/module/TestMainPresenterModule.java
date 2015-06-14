package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;
import com.proevan.spotifystreamer.view.MainPageView;

import org.hamcrest.CoreMatchers;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;

import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Module
public class TestMainPresenterModule {

    public static final String TEST_SEARCH_TEXT = "coldplay";
    public static final String TEST_EXCLUDE_SEARCH_TEXT_REGEX = "^(?!" + TEST_SEARCH_TEXT + "$).*$";
    public static final String FIRST_FAKE_ARTIST_NAME = "Coldplay";
    public static final String LAST_FAKE_ARTIST_NAME = "Princess of China (In The Style of Coldplay& Rihan";
    public static final String FAKE_NO_IMAGE_ARTIST_NAME = "Coldplay & Lele";

    public MainPageView mMainPageView;
    public SpotifyService mMockSpotifyService;

    public TestMainPresenterModule(MainPageView mainPageView) {
        mMainPageView = mainPageView;
        mMockSpotifyService = mock(SpotifyService.class);
        stubSearchResult();
    }
    private void stubSearchResult() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<ArtistsPager> callback = (SpotifyCallback<ArtistsPager>)invocationOnMock.getArguments()[1];
                callback.success(generateFakeSearchResultArtistPager(), null);
                return null;
            }
        }).when(mMockSpotifyService).searchArtists(matches(TEST_SEARCH_TEXT), Matchers.<SpotifyCallback<ArtistsPager>>any());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<ArtistsPager> callback = (SpotifyCallback<ArtistsPager>)invocationOnMock.getArguments()[1];
                callback.success(generateEmptySearchResultArtistPager(), null);
                return null;
            }
        }).when(mMockSpotifyService).searchArtists(matches(TEST_EXCLUDE_SEARCH_TEXT_REGEX), Matchers.<SpotifyCallback<ArtistsPager>>any());
    }

    private ArtistsPager generateFakeSearchResultArtistPager() {
        ArtistsPager artistsPager = new ArtistsPager();
        Pager<Artist> artistPager = new Pager<>();
        artistPager.items = generateFakeSearchResultArtists();
        artistsPager.artists = artistPager;

        return artistsPager;
    }

    private ArtistsPager generateEmptySearchResultArtistPager() {
        ArtistsPager artistsPager = new ArtistsPager();
        Pager<Artist> artistPager = new Pager<>();
        artistPager.items = new ArrayList<>();
        artistsPager.artists = artistPager;

        return artistsPager;
    }

    private List<Artist> generateFakeSearchResultArtists() {
        List<Artist> artists = new ArrayList<>();
        artists.add(generateFakeArtistWithOneImage(FIRST_FAKE_ARTIST_NAME, "https://i.scdn.co/image/99afd5b3e7ce4b82fdc007dc5ed8dfe0806f6fe2", TEST_ARTIST_ID));
        artists.add(generateFakeArtistWithOneImage(FAKE_NO_IMAGE_ARTIST_NAME, null, TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage("Various Artists - Coldplay Tribute", null, TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage("Karaoke - Coldplay", null, TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage("ColdPlay Wu", "https://i.scdn.co/image/d29c1e0ea74efccf7ec2634b5c2d2bc42f522a21", TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage("Coldplay Metal Tribute", null, TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage("Éxito De Coldplay", null, TEST_ARTIST_ID_NO_RESULT));
        artists.add(generateFakeArtistWithOneImage(LAST_FAKE_ARTIST_NAME, null, TEST_ARTIST_ID_NO_RESULT));

        return artists;
    }

    private Artist generateFakeArtistWithOneImage(String name, String imageUrl, String id) {
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

    @Provides
    @Singleton
    MainPresenter provideTestMainPresenter(SpotifyService spotifyService){
        return new MainPresenterImpl(mMainPageView, spotifyService);
    }

    @Provides
    @Singleton
    SpotifyService provideTestSpotifyService(){
        return mMockSpotifyService;
    }
}
