package com.proevan.spotifystreamer.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.TracksPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.activity.TracksActivityTestCase;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.FAKE_NO_IMAGE_ALBUM_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.FAKE_NO_IMAGE_TRACK_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.FIRST_FAKE_ALBUM_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.FIRST_FAKE_TRACK_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.LAST_FAKE_ALBUM_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.LAST_FAKE_TRACK_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.TEST_ARTIST_ID;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.TEST_ARTIST_ID_NO_RESULT;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.TEST_ARTIST_NAME;
import static com.proevan.spotifystreamer.di.module.TestTracksPresenterModule.TEST_ARTIST_NAME_NO_RESULT;
import static com.proevan.spotifystreamer.util.CustomMatcher.isImageTheSame;
import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_ID;
import static com.proevan.spotifystreamer.view.activity.TracksActivity.INTENT_BUNDLE_KEY.ARTIST_NAME;
import static org.hamcrest.Matchers.allOf;

public class TracksActivityTest extends TracksActivityTestCase {

    private void setUpWithFakeTrackResult() {
        setActivityIntent(generateFakeIntentWithBundle());
        getActivity();
        TracksPresenterComponent.Initializer.instance.inject(this);
    }

    private void setUpWithNoTrackResult() {
        setActivityIntent(generateFakeIntentWithBundleNoResult());
        getActivity();
        TracksPresenterComponent.Initializer.instance.inject(this);
    }

    private Intent generateFakeIntentWithBundle() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_ID.name(), TEST_ARTIST_ID);
        bundle.putString(ARTIST_NAME.name(), TEST_ARTIST_NAME);
        intent.putExtras(bundle);

        return intent;
    }

    private Intent generateFakeIntentWithBundleNoResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_ID.name(), TEST_ARTIST_ID_NO_RESULT);
        bundle.putString(ARTIST_NAME.name(), TEST_ARTIST_NAME_NO_RESULT);
        intent.putExtras(bundle);

        return intent;
    }

    // test case block start
    public void testLayoutActionBar() throws Exception {
        // arrange
        setUpWithFakeTrackResult();

        // act

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText(TEST_ARTIST_NAME))
                .check(matches(isDisplayed()));
    }

    public void testNoResult() throws Exception {
        // arrange
        setUpWithNoTrackResult();

        // act

        // assert
        onView(withText(R.string.no_result))
                .check(matches(isDisplayed()));
    }

    public void testFirstTrackItem() throws Exception {
        // arrange
        setUpWithFakeTrackResult();

        // act

        // assert
        Matcher<View> firstFakeItem = generateTrackItemMatcher(FIRST_FAKE_TRACK_NAME, FIRST_FAKE_ALBUM_NAME);
        onView(firstFakeItem)
                .check(matches(isDisplayed()));
    }

    public void testLastTrackItem() throws Exception {
        // arrange
        setUpWithFakeTrackResult();

        // act
        Matcher<View> lastFakeItem  = generateTrackItemMatcher(LAST_FAKE_TRACK_NAME, LAST_FAKE_ALBUM_NAME);
        onView(withId(R.id.recycler_view))
                .perform(scrollTo(lastFakeItem));
        onView(lastFakeItem)
                .perform(click());

        // assert
        onView(lastFakeItem)
                .check(matches(isDisplayed()));
    }

    public void testTrackItemImagePlaceHolder() throws Exception {
        // arrange
        setUpWithFakeTrackResult();

        // act

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(withId(R.id.image), withParent(generateTrackItemMatcher(FAKE_NO_IMAGE_TRACK_NAME, FAKE_NO_IMAGE_ALBUM_NAME))))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }
    // test case block end

    private Matcher<View> generateTrackItemMatcher(String trackName, String albumName) {
        return withChild(withChild(allOf(withText(trackName), hasSibling(withText(albumName)))));
    }
}