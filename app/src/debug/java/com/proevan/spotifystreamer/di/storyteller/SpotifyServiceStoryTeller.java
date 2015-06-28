package com.proevan.spotifystreamer.di.storyteller;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doAnswer;

public class SpotifyServiceStoryTeller {

    private SpotifyService mMockSpotifyService;

    public SpotifyServiceStoryTeller(SpotifyService mockSpotifyService) {
        mMockSpotifyService = mockSpotifyService;
    }

    public void stubSearchArtistsSuccess(final ArtistsPager artistsPager) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<ArtistsPager> callback = (SpotifyCallback<ArtistsPager>)invocationOnMock.getArguments()[1];
                callback.success(artistsPager, null);
                return null;
            }
        }).when(mMockSpotifyService).searchArtists(anyString(), Matchers.<SpotifyCallback<ArtistsPager>>any());
    }

    public void stubGetArtistTopTrackSuccess(final Tracks tracks) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<Tracks> callback = (SpotifyCallback<Tracks>)invocationOnMock.getArguments()[2];
                callback.success(tracks, null);
                return null;
            }
        }).when(mMockSpotifyService).getArtistTopTrack(anyString(), anyMap(), Matchers.<SpotifyCallback<Tracks>>any());
    }

    public void stubGetArtistTopTrackSuccess(String artistId, final Tracks tracks) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SpotifyCallback<Tracks> callback = (SpotifyCallback<Tracks>)invocationOnMock.getArguments()[2];
                callback.success(tracks, null);
                return null;
            }
        }).when(mMockSpotifyService).getArtistTopTrack(matches(artistId), anyMap(), Matchers.<SpotifyCallback<Tracks>>any());
    }
}
