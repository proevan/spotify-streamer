package com.proevan.spotifystreamer.presenter.impl;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;
import com.proevan.spotifystreamer.util.DelayActionFilter;
import com.proevan.spotifystreamer.view.SearchPageView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.client.Response;

public class MainPresenterImpl implements MainPresenter {

    public static final int SEARCH_TYPING_DELAY = 500;
    private SearchPageView mSearchPageView;
    private SpotifyService mSpotifyService;
    private DelayActionFilter mDelayActionFilter = new DelayActionFilter(SEARCH_TYPING_DELAY);

    @Inject
    public MainPresenterImpl(SearchPageView searchPageView, SpotifyService spotifyService) {
        mSearchPageView = searchPageView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onSearchTextChange(final CharSequence text) {
        mSearchPageView.hideNoDataMessage();
        mSearchPageView.showLoadingView();
        mSearchPageView.clearSearchResult();
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
                    mSearchPageView.hideLoadingView();
                    mSearchPageView.setResultItems(artistsPager.artists.items);
                    if (artistsPager.artists.items.size() == 0)
                        mSearchPageView.showNoDataMessage();
                }

                @Override
                public void failure(SpotifyError spotifyError) {
                    Logger.e("searchArtist failure: " + spotifyError.getMessage());
                    mSearchPageView.hideLoadingView();
                    mSearchPageView.showMessage(spotifyError.getLocalizedMessage());
                    mSearchPageView.showNoDataMessage();
                }
            });
        } else {
            mSearchPageView.hideLoadingView();
            mSearchPageView.clearSearchResult();
        }
    }

    @Override
    public void onSearchResultItemClick(ArtistListAdapter adapter, int position) {
        Artist artist = adapter.getItem(position);
        mSearchPageView.openTracksPage(artist.id, artist.name);
    }
}
