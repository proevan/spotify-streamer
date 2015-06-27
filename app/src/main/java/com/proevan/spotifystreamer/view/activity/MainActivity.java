package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.view.fragment.SearchFragment;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

public class MainActivity extends BaseActivity implements SearchFragment.SearchFragmentEventListener,
        TracksFragment.TracksFragmentEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MainPresenterComponent.Initializer.init(this).inject(this);
        setContentView(R.layout.activity_main);
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
    public void openTracksPage(String artistId, String artistName) {
        if (is2PaneMode())
            attachTracksFragmentToPane2(artistId);
        else
            launchTracksActivity(artistId, artistName);
    }

    private void attachTracksFragmentToPane2(String artistId) {
        replaceFragment(R.id.pane_2, TracksFragment.newInstance(artistId));
    }

    private void launchTracksActivity(String artistId, String artistName) {
        Intent intentToLaunch = TracksActivity.getCallingIntent(this, artistId, artistName);
        startActivity(intentToLaunch);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
