package com.proevan.spotifystreamer.presenter.impl;

import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;
import com.proevan.spotifystreamer.util.DelayActionFilter;
import com.proevan.spotifystreamer.view.MainPageView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.client.Response;

import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_ID;
import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_NAME;

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
        mMainPageView.hideNoDataMessage();
        mMainPageView.showLoadingView();
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
                    mMainPageView.hideLoadingView();
                    mMainPageView.setResultItems(artistsPager.artists.items);
                    if (artistsPager.artists.items.size() == 0)
                        mMainPageView.showNoDataMessage();
                }

                @Override
                public void failure(SpotifyError spotifyError) {
                    Logger.e("searchArtist failure: " + spotifyError.getMessage());
                    mMainPageView.hideLoadingView();
                    mMainPageView.showMessage(spotifyError.getLocalizedMessage());
                    mMainPageView.showNoDataMessage();
                }
            });
        } else {
            mMainPageView.hideLoadingView();
            mMainPageView.clearSearchResult();
        }
    }

    @Override
    public void onSearchResultItemClick(ArtistListAdapter adapter, int position) {
        Bundle bundle = new Bundle();
        Artist artist = adapter.getItem(position);
        bundle.putString(ARTIST_ID.name(), artist.id);
        bundle.putString(ARTIST_NAME.name(), artist.name);
        mMainPageView.openTracksPage(bundle);
    }
}
