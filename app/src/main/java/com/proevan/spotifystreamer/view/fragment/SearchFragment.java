package com.proevan.spotifystreamer.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.conponent.SearchPresenterComponent;
import com.proevan.spotifystreamer.presenter.SearchPresenter;
import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;
import com.proevan.spotifystreamer.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;
import kaaes.spotify.webapi.android.models.Artist;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class SearchFragment extends Fragment implements SearchView {

    private ArtistListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchFragmentEventListener mSearchFragmentEventListener;

    @Inject
    SearchPresenter mPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.no_result_text)
    TextView mNoResultTextView;

    @InjectView(R.id.progress_view)
    ProgressBarCircularIndeterminate mProgressView;

    @OnTextChanged(value = R.id.search_bar, callback = AFTER_TEXT_CHANGED)
    void onSearchTextChange(final CharSequence text) {
        Logger.v("onSearchTextChange: " + text);
        mPresenter.onSearchTextChange(text);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchPresenterComponent.Initializer.init(this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, fragmentView);

        initRecyclerView();
        return fragmentView;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ArtistListAdapter();
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Logger.v("mAdapter onItemClick: " + index);
                mPresenter.onSearchResultItemClick(mAdapter, index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSearchFragmentEventListener = (SearchFragmentEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement SearchFragmentEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSearchFragmentEventListener = null;
    }

    public void setResultItems(List<Artist> artists) {
        mAdapter.removeAll();
        mAdapter.addAll(artists);
    }

    public void clearSearchResult() {
        mAdapter.removeAll();
    }

    @Override
    public void openTracksPage(String artistId, String artistName) {
        mSearchFragmentEventListener.openTracksView(artistId, artistName);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(SpotifyStreamerApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoDataMessage() {
        mNoResultTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataMessage() {
        mNoResultTextView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        if (!SpotifyStreamerApplication.isTestMode())
            mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressView.setVisibility(View.GONE);
    }

    public interface SearchFragmentEventListener {
        public void openTracksView(String artistId, String artistName);
    }

}
