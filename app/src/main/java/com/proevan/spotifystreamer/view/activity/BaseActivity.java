package com.proevan.spotifystreamer.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

  protected void addFragment(int containerViewId, Fragment fragment, String tag) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment, tag);
    fragmentTransaction.commit();
  }

  protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(containerViewId, fragment, tag);
    fragmentTransaction.commit();
  }

}
