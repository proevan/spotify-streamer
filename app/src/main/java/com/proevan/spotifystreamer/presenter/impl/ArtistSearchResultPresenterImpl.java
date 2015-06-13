package com.proevan.spotifystreamer.presenter.impl;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.di.conponent.SpotifyServiceComponent;
import com.proevan.spotifystreamer.presenter.ArtistSearchResultPresenter;
import com.proevan.spotifystreamer.util.DelayActionFilter;
import com.proevan.spotifystreamer.view.ArtistSearchResultView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistSearchResultPresenterImpl implements ArtistSearchResultPresenter {

    public static final int SEARCH_TYPING_DELAY = 500;
    private ArtistSearchResultView mArtistSearchResultView;
    private DelayActionFilter mDelayActionFilter = new DelayActionFilter(SEARCH_TYPING_DELAY);

    @Inject
    SpotifyService mSpotifyService;

    public ArtistSearchResultPresenterImpl(ArtistSearchResultView artistSearchResultView) {
        SpotifyServiceComponent.Initializer.init().inject(this);
        mArtistSearchResultView = artistSearchResultView;
    }

    @Override
    public void onSearchTextChange(final CharSequence text) {
        mArtistSearchResultView.clearSearchResult();
        mDelayActionFilter.prepareToDoActionWithDelay(new DelayActionFilter.Callback() {
            @Override
            public void doAction() {
                searchArtist(text.toString());
            }
        });
    }

    public void searchArtist(final String name) {
        if (name.length() > 0) {
            mSpotifyService.searchArtists(name, new Callback<ArtistsPager>() {
                @Override
                public void success(ArtistsPager artistsPager, Response response) {
                    Logger.d("searchArtist success: " + name);
                    mArtistSearchResultView.setResultItems(artistsPager.artists.items);
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("searchArtist failure: " + error.getMessage());
                    mArtistSearchResultView.showMessage("error: " + error.getLocalizedMessage());
                }
            });
        } else {
            mArtistSearchResultView.clearSearchResult();
        }
    }

    @Override
    public void onSearchResultItemClick(int position) {
        Bundle bundle = new Bundle();
        mArtistSearchResultView.openTracksPage(bundle);
    }
}
