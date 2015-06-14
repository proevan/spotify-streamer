package com.proevan.spotifystreamer.view.activity;

import android.os.Bundle;
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

    public enum INTENT_BUNDLE_KEY {
        ARTIST_ID, ARTIST_NAME
    }

    private TrackListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mIsTestMode = false; // workaround for testing, prevent UI test stuck problem

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
        TracksPresenterComponent.Initializer.init(this).inject(this);
        setContentView(R.layout.activity_tracks);
        ButterKnife.inject(this);

        initActionBar();
        initRecyclerView();
        mPresenter.onCreateView(getIntent().getExtras());
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mPresenter.onUpButtonClick();
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
    public void closePage() {
        finish();
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
        if (!mIsTestMode)
            mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void setTestMode(boolean isTestMode) {
        mIsTestMode = isTestMode;
    }
}
