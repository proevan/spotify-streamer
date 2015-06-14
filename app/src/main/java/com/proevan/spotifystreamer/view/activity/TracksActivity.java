package com.proevan.spotifystreamer.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.presenter.TracksPresenter;
import com.proevan.spotifystreamer.view.TracksPageView;

import java.util.List;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.models.Artist;

public class TracksActivity extends AppCompatActivity implements TracksPageView {

    public enum INTENT_BUNDLE_KEY {
        ARTIST_NAME
    }

    @Inject
    TracksPresenter mTracksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        TracksPresenterComponent.Initializer.init(this).inject(this);
        mTracksPresenter.parseIntentBundle(getIntent().getExtras());
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
    }

    @Override
    public void setTrackItems(List<Artist> artists) {

    }

    @Override
    public void clearTrackItems() {

    }

    @Override
    public void openPlayerPage(Bundle bundle) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void closePage() {

    }
}
