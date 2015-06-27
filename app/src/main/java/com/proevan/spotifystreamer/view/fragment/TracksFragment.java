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
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.presenter.adapter.TrackListAdapter;
import com.proevan.spotifystreamer.view.TracksView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class TracksFragment extends Fragment implements TracksView {

    private static final String ARG_PARAM_ARTIST_ID = "ARG_PARAM_ARTIST_ID";

    private String mArtistId;
    private TrackListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TracksFragmentEventListener mTracksFragmentEventListener;

    @Inject
    TracksPresenter mPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.no_result_text)
    TextView mNoResultTextView;

    @InjectView(R.id.progress_view)
    ProgressBarCircularIndeterminate mProgressView;

    public static TracksFragment newInstance(String artistId) {
        TracksFragment fragment = new TracksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    public TracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TracksPresenterComponent.Initializer.init(this).inject(this);
        if (getArguments() != null) {
            mArtistId = getArguments().getString(ARG_PARAM_ARTIST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_tracks, container, false);
        ButterKnife.inject(this, fragmentView);

        initRecyclerView();
        return fragmentView;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TrackListAdapter();
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Logger.v("mAdapter onItemClick: " + index);
                mPresenter.onTrackItemClick(mAdapter, index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(mArtistId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mTracksFragmentEventListener = (TracksFragmentEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement TracksFragmentEventListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTracksFragmentEventListener = null;
    }

    @Override
    public void setTrackItems(List<Track> tracks) {
        mAdapter.removeAll();
        mAdapter.addAll(tracks);
    }

    @Override
    public void openPlayerView(Tracks tracks, int selectIndex) {
        mTracksFragmentEventListener.openPlayerView(tracks, selectIndex);
    }

    @Override
    public void showMessage(String message) {
        SpotifyStreamerApplication.showToast(message, Toast.LENGTH_SHORT);
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

    public interface TracksFragmentEventListener {
        public void openPlayerView(Tracks tracks, int selectIndex);
    }
}
