package com.proevan.spotifystreamer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.proevan.spotifystreamer.di.conponent.SpotifyServiceComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class ArtistSearchResultActivity extends AppCompatActivity {

    public static final String TAG = "ArtistSearchResultActivity";
    public static final int SEARCH_TYPING_DELAY = 500;
    private List<Artist> mArtists = new ArrayList<>();
    private ArtistListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Handler mSearchHandler = new Handler();
    private Runnable mSearchHandlerCallback;

    @Inject
    SpotifyService mSpotifyService;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnTextChanged(value = R.id.search_bar, callback = AFTER_TEXT_CHANGED)
    void onSearchTextChange(CharSequence text) {
        clearSearchResult();
        prepareToSearchWithDelay(text.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpotifyServiceComponent.Initializer.init().inject(this);
        setContentView(R.layout.activity_artist_search_result);
        ButterKnife.inject(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ArtistListAdapter(this, mArtists);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Log.d(TAG, "mAdapter onItemClick: " + index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareToSearchWithDelay(final String searchString) {
        if (mSearchHandlerCallback != null)
            mSearchHandler.removeCallbacks(mSearchHandlerCallback);
        mSearchHandlerCallback = new Runnable() {
            @Override
            public void run() {
                mSearchHandler.removeCallbacks(mSearchHandlerCallback);
                if (mSearchHandlerCallback != null) {
                    mSearchHandlerCallback = null;
                    searchArtist(searchString);
                }
            }
        };

        mSearchHandler.postDelayed(
                mSearchHandlerCallback,
                SEARCH_TYPING_DELAY);
    }

    private void searchArtist(final String name) {
        if (name.length() > 0) {
            mSpotifyService.searchArtists(name, new Callback<ArtistsPager>() {
                @Override
                public void success(ArtistsPager artistsPager, Response response) {
                    Log.d(TAG, "searchArtist success: " + name);
                    setResultData(artistsPager);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, "searchArtist failure: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), "error: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            clearSearchResult();
        }
    }

    private void setResultData(ArtistsPager artistsPager) {
        mArtists.clear();
        mArtists.addAll(artistsPager.artists.items);
        mAdapter.notifyDataSetChanged();
    }

    private void clearSearchResult() {
        mArtists.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artist_search_result, menu);
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
