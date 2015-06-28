package com.proevan.spotifystreamer.di;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.proevan.spotifystreamer.view.fragment.SearchFragment;
import com.proevan.spotifystreamer.view.fragment.TracksFragment;

import kaaes.spotify.webapi.android.models.Tracks;

public class FragmentTestContainerActivity extends FragmentActivity implements
        SearchFragment.SearchFragmentEventListener,
        TracksFragment.TracksFragmentEventListener
{

    @SuppressLint("NewApi")
    private static final int CONTAINER_ID = View.generateViewId();

    private boolean mIsMethodInvokedOpenTracksView = false;
    private boolean mIsMethodInvokedOpenPlayerView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(CONTAINER_ID);

        setContentView(frameLayout, params);
    }

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(CONTAINER_ID, fragment, tag)
                .commit();
    }


    public void showDialogFragment(DialogFragment dialog, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialog.show(ft, tag);
    }

    @Override
    public void openTracksView(String artistId, String artistName) {
        mIsMethodInvokedOpenTracksView = true;
    }

    public boolean isMethodInvokedOpenTracksView() {
        return mIsMethodInvokedOpenTracksView;
    }

    @Override
    public void openPlayerView(Tracks tracks, int selectIndex) {
        mIsMethodInvokedOpenPlayerView = true;
    }

    public boolean isMethodInvokedOpenPlayerView() {
        return mIsMethodInvokedOpenPlayerView;
    }
}
