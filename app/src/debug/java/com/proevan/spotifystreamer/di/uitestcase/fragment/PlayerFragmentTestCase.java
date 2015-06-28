package com.proevan.spotifystreamer.di.uitestcase.fragment;

import android.test.ActivityInstrumentationTestCase2;

import com.proevan.spotifystreamer.di.FragmentTestContainerActivity;
import com.proevan.spotifystreamer.di.storyteller.SpotifyServiceStoryTeller;

import javax.inject.Inject;

import kaaes.spotify.webapi.android.SpotifyService;

public class PlayerFragmentTestCase extends ActivityInstrumentationTestCase2<FragmentTestContainerActivity> {

    @Inject
    protected SpotifyService mMockSpotifyService;
    @Inject
    protected SpotifyServiceStoryTeller mSpotifyServiceStoryTeller;

    public PlayerFragmentTestCase() {
        super(FragmentTestContainerActivity.class);
    }
}
