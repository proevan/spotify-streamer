package com.proevan.spotifystreamer.presenter.impl;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.presenter.SearchPresenter;
import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;
import com.proevan.spotifystreamer.util.DelayActionFilter;
import com.proevan.spotifystreamer.view.SearchView;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.client.Response;

public class SearchPresenterImpl implements SearchPresenter {

    public static final int SEARCH_TYPING_DELAY = 500;
    private SearchView mSearchView;
    private SpotifyService mSpotifyService;
    private DelayActionFilter mDelayActionFilter = new DelayActionFilter(SEARCH_TYPING_DELAY);

    @Inject
    public SearchPresenterImpl(SearchView searchView, SpotifyService spotifyService) {
        mSearchView = searchView;
        mSpotifyService = spotifyService;
    }

    @Override
    public void onSearchTextChange(final CharSequence text) {
        mSearchView.hideNoDataMessage();
        mSearchView.showLoadingView();
        mSearchView.clearSearchResult();
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
                    mSearchView.hideLoadingView();
                    mSearchView.setResultItems(artistsPager.artists.items);
                    if (artistsPager.artists.items.size() == 0)
                        mSearchView.showNoDataMessage();
                }

                @Override
                public void failure(SpotifyError spotifyError) {
                    Logger.e("searchArtist failure: " + spotifyError.getMessage());
                    mSearchView.hideLoadingView();
                    mSearchView.showMessage(spotifyError.getLocalizedMessage());
                    mSearchView.showNoDataMessage();
                }
            });
        } else {
            mSearchView.hideLoadingView();
            mSearchView.clearSearchResult();
        }
    }

    @Override
    public void onSearchResultItemClick(ArtistListAdapter adapter, int position) {
        Artist artist = adapter.getItem(position);
        mSearchView.openTracksPage(artist.id, artist.name);
    }
}
