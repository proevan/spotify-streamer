package com.proevan.spotifystreamer.view.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.di.conponent.SearchPresenterComponent;
import com.proevan.spotifystreamer.di.uitestcase.fragment.SearchFragmentTestCase;
import com.proevan.spotifystreamer.presenter.impl.SearchPresenterImpl;

import org.hamcrest.Matcher;

import kaaes.spotify.webapi.android.models.Artist;

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
import static com.proevan.spotifystreamer.di.mock.MockArtist.COLDPLAY_SEARCH_RESULTS;
import static com.proevan.spotifystreamer.di.mock.MockArtist.COLDPLAY_SEARCH_RESULT_1;
import static com.proevan.spotifystreamer.di.mock.MockArtist.COLDPLAY_SEARCH_RESULT_2;
import static com.proevan.spotifystreamer.di.mock.MockArtistsPager.COLDPLAY_SEARCH_RESULT_PAGER;
import static com.proevan.spotifystreamer.di.mock.MockArtistsPager.EMPTY_SEARCH_RESULT_PAGER;
import static com.proevan.spotifystreamer.macher.CustomMatcher.isImageTheSame;
import static org.hamcrest.Matchers.allOf;

public class SearchFragmentTest extends SearchFragmentTestCase {

    public void setUp() throws Exception {
        super.setUp();
        initTestView();
        SearchPresenterComponent.Initializer.getInstance().inject(this);
    }

    private void initTestView() throws Exception {
        SpotifyStreamerApplication.setIsTestMode(true);
        getActivity().addFragment(SearchFragment.newInstance(false), SearchFragment.TAG);
        getInstrumentation().waitForIdleSync();
    }

    // test case block start
    public void testLayoutSearchBar() throws Exception {
        // arrange

        // act

        // assert
        onView(withId(R.id.search_bar))
                .check(matches(isDisplayed()));
    }

    public void testClearResultDuringTyping() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(COLDPLAY_SEARCH_RESULT_PAGER);

        // act
        onView(withId(R.id.search_bar))
                .perform(typeText("coldplay"));

        // assert
        onView(withArtistNameItem(COLDPLAY_SEARCH_RESULT_1.name))
                .check(doesNotExist());
    }

    public void testSearchResultLayout() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(COLDPLAY_SEARCH_RESULT_PAGER);

        // act
        searchAndWaitForResult("coldplay");

        // assert
        for (Artist coldplaySearchResult : COLDPLAY_SEARCH_RESULTS)
            onView(withId(R.id.recycler_view))
                    .perform(scrollTo(withArtistNameItem(coldplaySearchResult.name)));
    }

    public void testSearchThenClearText() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(COLDPLAY_SEARCH_RESULT_PAGER);

        // act
        searchAndWaitForResult("coldplay");
        onView(withId(R.id.search_bar))
                .perform(clearText());

        // assert
        for (Artist coldplaySearchResult : COLDPLAY_SEARCH_RESULTS)
            onView(withArtistNameItem(coldplaySearchResult.name))
                    .check(doesNotExist());
    }

    public void testNoResult() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(EMPTY_SEARCH_RESULT_PAGER);

        // act
        searchAndWaitForResult("find no results");

        // assert
        onView(withText(R.string.no_result))
                .check(matches(isDisplayed()));
    }

    public void testArtistItemImagePlaceHolder() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(COLDPLAY_SEARCH_RESULT_PAGER);

        // act
        searchAndWaitForResult("coldplay");

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(hasSibling(withText(COLDPLAY_SEARCH_RESULT_2.name)), withId(R.id.image)))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }

    public void testArtistItemClick() throws Exception {
        // arrange
        mSpotifyServiceStoryTeller.stubSearchArtistsSuccess(COLDPLAY_SEARCH_RESULT_PAGER);

        // act
        searchAndWaitForResult("coldplay");
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        // assert
        assertTrue(getActivity().isMethodInvokedOpenTracksView());
    }
    // test case block end

    private void searchAndWaitForResult(String text) throws InterruptedException {
        onView(withId(R.id.search_bar))
                .perform(typeText(text));
        waitForSearchTypingDelay();
    }

    private void waitForSearchTypingDelay() throws InterruptedException {
        Thread.sleep(SearchPresenterImpl.SEARCH_TYPING_DELAY);
    }

    private Matcher<View> withArtistNameItem(String name) {
        return withChild(allOf(withText(name), withId(R.id.name)));
    }
}