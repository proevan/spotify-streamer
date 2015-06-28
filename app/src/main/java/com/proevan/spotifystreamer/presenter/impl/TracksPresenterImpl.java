package com.proevan.spotifystreamer.presenter.impl;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;
import com.proevan.spotifystreamer.view.TracksView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.client.Response;

public class TracksPresenterImpl implements TracksPresenter {

    private static final String API_COUNTRY_CODE_PARAM_KEY = "country";
    private static final String DEFAULT_API_COUNTRY_CODE = "US";

    private TracksView mTracksView;
    private SpotifyService mSpotifyService;
    private Tracks mTracks;

    @Inject
    public TracksPresenterImpl(TracksView tracksView, SpotifyService spotifyService) {
        mTracksView = tracksView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onViewCreated(String artistId) {
        loadTracks(artistId);
    }

    @Override
    public void loadTracks(String artistId) {
        mTracksView.hideNoDataMessage();
        mTracksView.showLoadingView();
        mSpotifyService.getArtistTopTrack(artistId, generateQueryParamWithCountryCode(), new SpotifyCallback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                Logger.d("loadTracks success");
                mTracks = tracks;
                mTracksView.hideLoadingView();
                mTracksView.setTrackItems(tracks.tracks);
                if (tracks.tracks.size() == 0)
                    mTracksView.showNoDataMessage();
            }

            @Override
            public void failure(SpotifyError spotifyError) {
                Logger.e("loadTracks failure: " + spotifyError.getMessage());
                mTracksView.hideLoadingView();
                mTracksView.showMessage(spotifyError.getLocalizedMessage());
                mTracksView.showNoDataMessage();
            }
        });
    }

    private Map<String, Object> generateQueryParamWithCountryCode() {
        Map<String, Object> params = new HashMap<>();
        params.put(API_COUNTRY_CODE_PARAM_KEY, DEFAULT_API_COUNTRY_CODE);

        return params;
    }

    @Override
    public void onTrackItemClick(TrackListAdapter adapter, int index) {
        mTracksView.openPlayerView(mTracks, index);
    }
}
