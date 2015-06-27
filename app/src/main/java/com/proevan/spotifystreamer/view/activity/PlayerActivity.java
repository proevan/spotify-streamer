package com.proevan.spotifystreamer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.model.TrackItem;
import com.proevan.spotifystreamer.view.fragment.PlayerFragment;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import org.parceler.Parcels;

import java.util.List;

import kaaes.spotify.webapi.android.models.Tracks;

public class PlayerActivity extends BaseActivity {

    private static final String INTENT_PARAM_TRACK_ITEMS = "INTENT_PARAM_TRACK_ITEMS";
    private static final String INTENT_PARAM_PLAY_INDEX = "INTENT_PARAM_PLAY_INDEX";
    private static final String INSTANCE_STATE_PARAM_TRACK_ITEMS = "INSTANCE_STATE_PARAM_TRACK_ITEMS";
    private static final String INSTANCE_STATE_PARAM_PLAY_INDEX = "INSTANCE_STATE_PARAM_PLAY_INDEX";

    public static Intent getCallingIntent(Context context, Tracks tracks, int playIndex) {
        Intent intent = new Intent(context, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_PARAM_TRACK_ITEMS, Parcels.wrap(TrackItem.convertFromTracks(tracks.tracks)));
        bundle.putInt(INTENT_PARAM_PLAY_INDEX, playIndex);
        intent.putExtras(bundle);

        return intent;
    }

    private List<TrackItem> mTrackItems;
    private int mPlayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(savedInstanceState);
        setContentView(R.layout.activity_player);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(INSTANCE_STATE_PARAM_TRACK_ITEMS, Parcels.wrap(mTrackItems));
            outState.putInt(INSTANCE_STATE_PARAM_PLAY_INDEX, mPlayIndex);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mTrackItems = Parcels.unwrap(extras.getParcelable(INTENT_PARAM_TRACK_ITEMS));
                mPlayIndex = extras.getInt(INTENT_PARAM_PLAY_INDEX);
                addFragment(R.id.layout_container, PlayerFragment.newInstance(mTrackItems, mPlayIndex));
            }
        } else {
            mTrackItems = Parcels.unwrap(savedInstanceState.getParcelable(INSTANCE_STATE_PARAM_TRACK_ITEMS));
            mPlayIndex = savedInstanceState.getInt(INSTANCE_STATE_PARAM_PLAY_INDEX);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
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
}
