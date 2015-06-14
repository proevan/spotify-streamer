package com.proevan.spotifystreamer.di.module;

import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.impl.TracksPresenterImpl;
import com.proevan.spotifystreamer.view.TracksPageView;

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
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Module
public class TestTracksPresenterModule {

    public static final String TEST_ARTIST_ID = "FakeID";
    public static final String TEST_ARTIST_NAME = "Coldplay";
    public static final String TEST_ARTIST_ID_NO_RESULT = "FakeIDNoResult";
    public static final String TEST_ARTIST_NAME_NO_RESULT = "No Result Artist";
    public static final String FIRST_FAKE_TRACK_NAME = "A Sky Full of Stars";
    public static final String FIRST_FAKE_ALBUM_NAME = "Ghost Stories";
    public static final String LAST_FAKE_TRACK_NAME = "Clocks";
    public static final String LAST_FAKE_ALBUM_NAME = "A Rush Of Blood To The Head";
    public static final String FAKE_NO_IMAGE_TRACK_NAME = "The Scientist";
    public static final String FAKE_NO_IMAGE_ALBUM_NAME = "A Rush Of Blood To The Head";

    private TracksPageView mTracksPageView;
    private SpotifyService mMockSpotifyService;

    public TestTracksPresenterModule(TracksPageView tracksPageView) {
        mTracksPageView = tracksPageView;
        mMockSpotifyService = mock(SpotifyService.class);
        stubSearchResult();
    }

    private void stubSearchResult() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<Tracks> callback = (SpotifyCallback<Tracks>)invocationOnMock.getArguments()[2];
                callback.success(generateFakeTracksResponse(), null);
                return null;
            }
        }).when(mMockSpotifyService).getArtistTopTrack(matches(TEST_ARTIST_ID), anyMap(), Matchers.<SpotifyCallback<Tracks>>any());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<Tracks> callback = (SpotifyCallback<Tracks>)invocationOnMock.getArguments()[2];
                callback.success(generateEmptyTracksResponse(), null);
                return null;
            }
        }).when(mMockSpotifyService).getArtistTopTrack(matches(TEST_ARTIST_ID_NO_RESULT), anyMap(), Matchers.<SpotifyCallback<Tracks>>any());
    }

    private Tracks generateFakeTracksResponse() {
        Tracks trackResponse = new Tracks();
        trackResponse.tracks = generateFakeTracks();

        return trackResponse;
    }

    private Tracks generateEmptyTracksResponse() {
        Tracks trackResponse = new Tracks();
        trackResponse.tracks = new ArrayList<>();

        return trackResponse;
    }

    private List<Track> generateFakeTracks() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(generateFakeTrackWithOneAlbumImage(FIRST_FAKE_TRACK_NAME, FIRST_FAKE_ALBUM_NAME, "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581"));
        tracks.add(generateFakeTrackWithOneAlbumImage(FAKE_NO_IMAGE_TRACK_NAME, FAKE_NO_IMAGE_ALBUM_NAME, null));
        tracks.add(generateFakeTrackWithOneAlbumImage("Yellow", "Parachutes", "https://i.scdn.co/image/af4812064c4963f5ce4e630cbf70b540ce3f60dc"));
        tracks.add(generateFakeTrackWithOneAlbumImage("Fix You", "X & Y", "https://i.scdn.co/image/0306577597d1fa09d10fda6753b8b77faabfa579"));
        tracks.add(generateFakeTrackWithOneAlbumImage("Paradise", "Mylo Xyloto", "https://i.scdn.co/image/6e04fd4fcc5b107f93dc1860941dc240e79e1295"));
        tracks.add(generateFakeTrackWithOneAlbumImage("Magic", "Ghost Stories", "https://i.scdn.co/image/663f92496739278bf8b050cf8f76a6f4e7fc8581"));
        tracks.add(generateFakeTrackWithOneAlbumImage("Viva La Vida", "Viva La Vida Or Death And All His Friends", "https://i.scdn.co/image/28967fae3eaf33b0570b53cf621390204bf050d4"));
        tracks.add(generateFakeTrackWithOneAlbumImage(LAST_FAKE_TRACK_NAME, LAST_FAKE_ALBUM_NAME, "https://i.scdn.co/image/4a408081438be137b71b1e8afafb98795d53e9d6"));

        return tracks;
    }

    private Track generateFakeTrackWithOneAlbumImage(String trackName, String albumName, String imageUrl) {
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

    @Provides
    @Singleton
    TracksPresenter provideTestTracksPresenter(SpotifyService spotifyService){
        return new TracksPresenterImpl(mTracksPageView, spotifyService);
    }

    @Provides
    @Singleton
    SpotifyService provideTestSpotifyService(){
        return mMockSpotifyService;
    }
}
