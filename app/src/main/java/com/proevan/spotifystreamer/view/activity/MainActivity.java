package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.MainPresenterComponent;
import com.proevan.spotifystreamer.presenter.MainPresenter;
import com.proevan.spotifystreamer.presenter.adapter.ArtistListAdapter;
import com.proevan.spotifystreamer.view.MainPageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnTextChanged;
import kaaes.spotify.webapi.android.models.Artist;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class MainActivity extends AppCompatActivity implements MainPageView {

    private ArtistListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    MainPresenter mPresenter;

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnTextChanged(value = R.id.search_bar, callback = AFTER_TEXT_CHANGED)
    void onSearchTextChange(final CharSequence text) {
        Logger.v("onSearchTextChange: " + text);
        mPresenter.onSearchTextChange(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresenterComponent.Initializer.init(this).inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ArtistListAdapter();
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                Logger.v("mAdapter onItemClick: " + index);
                mPresenter.onSearchResultItemClick(index);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
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

    public void setResultItems(List<Artist> artists) {
        mAdapter.removeAll();
        mAdapter.addAll(artists);
    }

    public void clearSearchResult() {
        mAdapter.removeAll();
    }

    @Override
    public void openTracksPage(Bundle bundle) {
        Intent intent = new Intent(this, TracksActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
