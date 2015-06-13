package com.proevan.spotifystreamer;


import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.proevan.spotifystreamer.di.conponent.SpotifyServiceComponent;
import com.proevan.spotifystreamer.di.uitestcase.activity.ArtistSearchResultActivityTestCase;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.Callback;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.proevan.spotifystreamer.testingutil.CustomMatcher.isImageTheSame;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.doAnswer;

public class ArtistListActivityTest extends ArtistSearchResultActivityTestCase {

    public void setUp() throws Exception {
        super.setUp();
        SpotifyServiceComponent.Initializer.recreate().inject(this);
        stubColdplaySearchResult();
        getActivity();
    }

    // stub block start
    private void stubColdplaySearchResult() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Callback<ArtistsPager> callback = (Callback<ArtistsPager>)invocationOnMock.getArguments()[1];
                callback.success(generateFakeColdplaySearchResultArtistPager(), null);
                return null;
            }
        }).when(mMockSpotifyService).searchArtists(Matchers.matches("coldplay"), Matchers.<Callback<ArtistsPager>>any());
    }

    private ArtistsPager generateFakeColdplaySearchResultArtistPager() {
        ArtistsPager artistsPager = new ArtistsPager();
        Pager<Artist> artistPager = new Pager<>();
        artistPager.items = generateFakeColdplaySearchResultArtists();
        artistsPager.artists = artistPager;

        return artistsPager;
    }

    private ArrayList<Artist> generateFakeColdplaySearchResultArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(generateFakeArtistWithOneImage("Coldplay", "https://i.scdn.co/image/99afd5b3e7ce4b82fdc007dc5ed8dfe0806f6fe2"));
        artists.add(generateFakeArtistWithOneImage("Coldplay & Lele", null));
        artists.add(generateFakeArtistWithOneImage("Various Artists - Coldplay Tribute", null));
        artists.add(generateFakeArtistWithOneImage("Karaoke - Coldplay", null));
        artists.add(generateFakeArtistWithOneImage("ColdPlay Wu", "https://i.scdn.co/image/d29c1e0ea74efccf7ec2634b5c2d2bc42f522a21"));

        return artists;
    }

    private Artist generateFakeArtistWithOneImage(String name, String imageUrl) {
        Artist artist = new Artist();
        artist.name = name;
        List<Image> images = new ArrayList<>();
        if (imageUrl != null) {
            Image image = new Image();
            image.url = imageUrl;
            images.add(image);
        }
        artist.images = images;

        return artist;
    }
    // stub block end

    // test case block start
    public void testLayoutActionBarTitle() throws Exception {
        // arrange

        // act

        // assert
        onView(withText(R.string.app_name))
                .check(matches(isDisplayed()));
    }

    public void testLayoutSearchBar() throws Exception {
        // arrange

        // act

        // assert
        onView(withId(R.id.search_bar))
                .check(matches(isDisplayed()));
    }

    public void testSearchColdplay() throws Exception {
        // arrange

        // act
        onView(withId(R.id.search_bar))
                .perform(typeText("coldplay"));

        // assert
        onView(allOf(withText("Coldplay"), withId(R.id.name)))
                .check(matches(isDisplayed()));
        onView(allOf(withText("Coldplay & Lele"), withId(R.id.name)))
                .check(matches(isDisplayed()));
        onView(allOf(withText("Various Artists - Coldplay Tribute"), withId(R.id.name)))
                .check(matches(isDisplayed()));
        onView(allOf(withText("Karaoke - Coldplay"), withId(R.id.name)))
                .check(matches(isDisplayed()));
    }

    public void testArtistItemImagePlaceHolder() throws Exception {
        // arrange

        // act
        onView(withId(R.id.search_bar))
                .perform(typeText("coldplay"));

        // assert
        Drawable placeholderDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.spotify_placeholder);
        onView(allOf(hasSibling(withText("Coldplay & Lele")), withId(R.id.image)))
                .check(matches(isImageTheSame(placeholderDrawable)));
    }
    // test case block end
}