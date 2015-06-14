package com.proevan.spotifystreamer.presenter.impl;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.util.DelayActionFilter;
import com.proevan.spotifystreamer.view.MainPageView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainPresenterImpl implements MainPresenter {

    public static final int SEARCH_TYPING_DELAY = 500;
    private MainPageView mMainPageView;
    private SpotifyService mSpotifyService;
    private DelayActionFilter mDelayActionFilter = new DelayActionFilter(SEARCH_TYPING_DELAY);

    @Inject
    public MainPresenterImpl(MainPageView mainPageView, SpotifyService spotifyService) {
        mMainPageView = mainPageView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onSearchTextChange(final CharSequence text) {
        mMainPageView.clearSearchResult();
        mDelayActionFilter.prepareToDoActionWithDelay(new DelayActionFilter.Callback() {
            @Override
            public void doAction() {
                searchArtist(text.toString());
            }
        });
    }

    public void searchArtist(final String name) {
        if (name.length() > 0) {
            mSpotifyService.searchArtists(name, new SpotifyCallback<ArtistsPager>() {
                @Override
                public void success(ArtistsPager artistsPager, Response response) {
                    Logger.d("searchArtist success: " + name);
                    mMainPageView.setResultItems(artistsPager.artists.items);
                }

                @Override
                public void failure(SpotifyError spotifyError) {
                    Logger.e("searchArtist failure: " + spotifyError.getMessage());
                    mMainPageView.showMessage("error: " + spotifyError.getLocalizedMessage());
                }
            });
        } else {
            mMainPageView.clearSearchResult();
        }
    }

    @Override
    public void onSearchResultItemClick(int position) {
        Bundle bundle = new Bundle();
        mMainPageView.openTracksPage(bundle);
    }
}
