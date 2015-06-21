package com.proevan.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.proevan.spotifystreamer.view.TracksPageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Track;

public class TracksActivity extends AppCompatActivity implements TracksPageView {

    private static final String INTENT_PARAM_ARTIST_ID = "INTENT_PARAM_USER_ID";
    private static final String INTENT_PARAM_ARTIST_NAME = "INTENT_PARAM_USER_NAME";
    private static final String INSTANCE_STATE_PARAM_ARTIST_ID = "STATE_PARAM_USER_ID";
    private static final String INSTANCE_STATE_PARAM_ARTIST_NAME = "STATE_PARAM_USER_NAME";

    public static Intent getCallingIntent(Context context, String artistId, String artistName) {
        Intent intent = new Intent(context, TracksActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_PARAM_ARTIST_ID, artistId);
        bundle.putString(INTENT_PARAM_ARTIST_NAME, artistName);
        intent.putExtras(bundle);

        return intent;
    }

    private String mArtistId;
    private String mArtistName;
    private TrackListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    TracksPresenter mPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.no_result_text)
    TextView mNoResultTextView;

    @InjectView(R.id.progress_view)
    ProgressBarCircularIndeterminate mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(savedInstanceState);
        TracksPresenterComponent.Initializer.init(this).inject(this);
        setContentView(R.layout.activity_tracks);
        ButterKnife.inject(this);

        initActionBar();
        initRecyclerView();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_ARTIST_ID, mArtistId);
            outState.putString(INSTANCE_STATE_PARAM_ARTIST_NAME, mArtistName);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mArtistId = extras.getString(INTENT_PARAM_ARTIST_ID, "");
                mArtistName = extras.getString(INTENT_PARAM_ARTIST_NAME, "");
            }
        } else {
            mArtistId = savedInstanceState.getString(INSTANCE_STATE_PARAM_ARTIST_ID);
            mArtistName = savedInstanceState.getString(INSTANCE_STATE_PARAM_ARTIST_NAME);
        }
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSubtitle(mArtistName);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
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
        mPresenter.loadTracks(mArtistId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
    }

    @Override
    public void setTrackItems(List<Track> tracks) {
        mAdapter.removeAll();
        mAdapter.addAll(tracks);
    }

    @Override
    public void clearTrackItems() {
        mAdapter.removeAll();
    }

    @Override
    public void openPlayerPage(Bundle bundle) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

}
