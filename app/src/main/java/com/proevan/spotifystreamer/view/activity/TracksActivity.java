package com.proevan.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import kaaes.spotify.webapi.android.models.Tracks;

public class TracksActivity extends BaseActivity implements TracksFragment.TracksFragmentEventListener {

    private static final String INTENT_PARAM_ARTIST_ID = "INTENT_PARAM_USER_ID";
    private static final String INTENT_PARAM_ARTIST_NAME = "INTENT_PARAM_USER_NAME";
    private static final String INSTANCE_STATE_PARAM_ARTIST_ID = "INSTANCE_STATE_PARAM_ARTIST_ID";
    private static final String INSTANCE_STATE_PARAM_ARTIST_NAME = "INSTANCE_STATE_PARAM_ARTIST_NAME";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreInstanceState(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        initActionBar();
        if (savedInstanceState == null)
            addFragment(R.id.layout_container, TracksFragment.newInstance(mArtistId));
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_ARTIST_ID, mArtistId);
            outState.putString(INSTANCE_STATE_PARAM_ARTIST_NAME, mArtistName);
        }
        super.onSaveInstanceState(outState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
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

    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
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
    public void openPlayerView(Tracks tracks, int selectIndex) {
        Intent LaunchIntent = PlayerActivity.getCallingIntent(this, tracks, selectIndex);
        startActivity(LaunchIntent);
    }
}
