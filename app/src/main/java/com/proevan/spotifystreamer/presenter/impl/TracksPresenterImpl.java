package com.proevan.spotifystreamer.presenter.impl;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;
import com.proevan.spotifystreamer.view.TracksPageView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.client.Response;

import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_ID;
import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_NAME;

public class TracksPresenterImpl implements TracksPresenter {

    private static final String EMPTY_STRING = "";
    private static final String API_COUNTRY_CODE_PARAM_KEY = "country";
    private static final String DEFAULT_API_COUNTRY_CODE = "US";

    private TracksPageView mTracksPageView;
    private SpotifyService mSpotifyService;

    @Inject
    public TracksPresenterImpl(TracksPageView tracksPageView, SpotifyService spotifyService) {
        mTracksPageView = tracksPageView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onCreateView(Bundle extras) {
        mTracksPageView.hideNoDataMessage();
        mTracksPageView.showLoadingView();
        String artistId = extras.getString(ARTIST_ID.name(), EMPTY_STRING);
        String artistName = extras.getString(ARTIST_NAME.name(), EMPTY_STRING);
        mTracksPageView.setSubtitle(artistName);
        loadTracks(artistId);
    }

    private void loadTracks(String artistId) {
        mSpotifyService.getArtistTopTrack(artistId, generateQueryParamWithCountryCode(), new SpotifyCallback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                Logger.d("loadTracks success");
                mTracksPageView.hideLoadingView();
                mTracksPageView.setTrackItems(tracks.tracks);
                if (tracks.tracks.size() == 0)
                    mTracksPageView.showNoDataMessage();
            }

            @Override
            public void failure(SpotifyError spotifyError) {
                Logger.e("loadTracks failure: " + spotifyError.getMessage());
                mTracksPageView.hideLoadingView();
                mTracksPageView.showMessage(spotifyError.getLocalizedMessage());
                mTracksPageView.showNoDataMessage();
            }
        });
    }

    private Map<String, Object> generateQueryParamWithCountryCode() {
        Map<String, Object> params = new HashMap<>();
        params.put(API_COUNTRY_CODE_PARAM_KEY, DEFAULT_API_COUNTRY_CODE);

        return params;
    }

    @Override
    public void onUpButtonClick() {
        mTracksPageView.closePage();
    }

    @Override
    public void onTrackItemClick(TrackListAdapter adapter, int index) {

    }
}
