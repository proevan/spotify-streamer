package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FixedDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.model.TrackItem;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import kaaes.spotify.webapi.android.models.Tracks;

public class MainActivity extends BaseActivity implements SearchFragment.SearchFragmentEventListener,
        TracksFragment.TracksFragmentEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            addFragment(R.id.pane_1, SearchFragment.newInstance(is2PaneMode()));
    }

    private boolean is2PaneMode() {
        if (findViewById(R.id.pane_2) != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openTracksView(String artistId, String artistName) {
        if (is2PaneMode())
            attachTracksFragmentToPane2AndAddSubTitle(artistId, artistName);
        else
            launchTracksActivity(artistId, artistName);
    }

    private void attachTracksFragmentToPane2AndAddSubTitle(String artistId, String artistName) {
        setSubtitle(artistName);
        replaceFragment(R.id.pane_2, TracksFragment.newInstance(artistId));
    }

    private void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
    }

    private void launchTracksActivity(String artistId, String artistName) {
        Intent intentToLaunch = TracksActivity.getCallingIntent(this, artistId, artistName);
        startActivity(intentToLaunch);
    }

    @Override
    public void openPlayerView(Tracks tracks, int selectIndex) {
        showDialog(
                PlayerFragment.newInstance(TrackItem.convertFromTracks(tracks.tracks), selectIndex),
                PlayerFragment.class.getSimpleName()
        );
    }

    public void showDialog(FixedDialogFragment dialogFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, tag);
    }
}
