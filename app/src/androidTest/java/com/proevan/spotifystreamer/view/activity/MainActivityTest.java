package com.proevan.spotifystreamer.view.activity;


import android.graphics.drawable.Drawable;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.MainPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.activity.MainActivityTestCase;
import com.proevan.spotifystreamer.presenter.impl.MainPresenterImpl;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.di.module.TestMainPresenterModule.FAKE_NO_IMAGE_ARTIST_NAME;
import static com.proevan.spotifystreamer.di.module.TestMainPresenterModule.FIRST_FAKE_ARTIST_NAME;
import static com.proevan.spotifystreamer.di.module.TestMainPresenterModule.LAST_FAKE_ARTIST_NAME;
import static com.proevan.spotifystreamer.util.CustomMatcher.isImageTheSame;
import static org.hamcrest.Matchers.allOf;

public class MainActivityTest extends MainActivityTestCase {

    private static final String TEST_SEARCH_TEXT = "coldplay";
    private static final String TEST_SEARCH_TEXT_NO_RESULT = "no result";

    public void setUp() throws Exception {
        super.setUp();
        getActivity();
        MainPresenterComponent.Initializer.instance.inject(this);
        getActivity().setTestMode(true);
    }

    // test case block start
    public void testLayoutActionBarTitle() throws Exception {
        // arrange

        // act

        // assert
        onView(ViewMatchers.withText(R.string.app_name))
                .check(matches(isDisplayed()));
    }

    public void testLayoutSearchBar() throws Exception {
        // arrange

        // act

        // assert
        onView(withId(R.id.search_bar))
                .check(matches(isDisplayed()));
    }

    public void testClearResultDuringTyping() throws Exception {
        // arrange

        // act
        onView(withId(R.id.search_bar))
                .perform(typeText(TEST_SEARCH_TEXT));

        // assert
        onView(allOf(withText(FIRST_FAKE_ARTIST_NAME), withId(R.id.name)))
                .check(doesNotExist());
    }

    public void testSearch() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);

        // assert
        onView(allOf(withText(FIRST_FAKE_ARTIST_NAME), withId(R.id.name)))
                .check(matches(isDisplayed()));
    }

    public void testSearchThenClearText() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);
        onView(withId(R.id.search_bar))
                .perform(clearText());

        // assert
        onView(allOf(withText(FIRST_FAKE_ARTIST_NAME), withId(R.id.name)))
                .check(doesNotExist());
    }

    public void testNoResult() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT_NO_RESULT);

        // assert
        onView(withText(R.string.no_result))
                .check(matches(isDisplayed()));
    }

    public void testFirstArtistItem() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);

        // assert
        Matcher<View> firstFakeItem = withChild(withText(FIRST_FAKE_ARTIST_NAME));
        onView(firstFakeItem)
                .check(matches(isDisplayed()));
    }

    public void testLastArtistItem() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);
        Matcher<View> lastFakeItem = withChild(withText(LAST_FAKE_ARTIST_NAME));
        onView(withId(R.id.recycler_view))
                .perform(scrollTo(lastFakeItem));
        onView(lastFakeItem)
                .perform(click());

        // assert
        onView(lastFakeItem)
                .check(matches(isDisplayed()));
    }

    public void testArtistItemImagePlaceHolder() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(hasSibling(withText(FAKE_NO_IMAGE_ARTIST_NAME)), withId(R.id.image)))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }

    public void testFirstArtistItemClick() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText(FIRST_FAKE_ARTIST_NAME))
                .check(matches(isDisplayed()));
    }

    public void testLastArtistItemClick() throws Exception {
        // arrange

        // act
        searchAndWaitForResult(TEST_SEARCH_TEXT);
        Matcher<View> lastFakeItem = withChild(withText(LAST_FAKE_ARTIST_NAME));
        onView(withId(R.id.recycler_view))
                .perform(scrollTo(lastFakeItem));
        onView(lastFakeItem)
                .perform(click());

        // assert
        onView(withText(getActivity().getString(R.string.title_activity_tracks)))
                .check(matches(isDisplayed()));
        onView(withText(LAST_FAKE_ARTIST_NAME))
                .check(matches(isDisplayed()));
    }
    // test case block end

    private void searchAndWaitForResult(String text) throws InterruptedException {
        onView(withId(R.id.search_bar))
                .perform(typeText(text));
        waitForSearchTypingDelay();
    }

    private void waitForSearchTypingDelay() throws InterruptedException {
        Thread.sleep(MainPresenterImpl.SEARCH_TYPING_DELAY);
    }
}