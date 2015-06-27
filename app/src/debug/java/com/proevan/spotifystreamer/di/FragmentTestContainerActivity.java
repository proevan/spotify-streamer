package com.proevan.spotifystreamer.di;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment$DialogStyle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class FragmentTestContainerActivity extends FragmentActivity {

    @SuppressLint("NewApi")
    private static final int CONTAINER_ID = View.generateViewId();

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


    public void showDialogFragment(DialogFragment$DialogStyle dialog, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialog.show(ft, tag);
    }
}
