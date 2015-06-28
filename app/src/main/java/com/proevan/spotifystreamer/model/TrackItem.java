package com.proevan.spotifystreamer.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Track;

@Parcel
public class TrackItem {

    private String mTrackName;
    private String mPreviewUrl;
    private String mAlbumName;
    private String mAlbumCoverImageUrl;
    private List<String> mArtistNameList = new ArrayList<>();
    private Long mDurationInMillisecond;

    public static TrackItem convertFromTrack(Track track) {
        TrackItem trackItem = new TrackItem();
        trackItem.setTrackName(track.name);
        trackItem.setPreviewUrl(track.preview_url);
        trackItem.setAlbumName(track.album.name);
        trackItem.setDurationInMillisecond(track.duration_ms);
        if (track.album.images.size() > 0)
            trackItem.setAlbumCoverImageUrl(track.album.images.get(0).url);
        for (ArtistSimple artistSimple : track.artists)
            trackItem.getArtistNameList().add(artistSimple.name);

        return trackItem;
    }

    public static List<TrackItem> convertFromTracks(List<Track> tracks) {
        List<TrackItem> trackItems = new ArrayList<>();
        for (Track track : tracks)
            trackItems.add(convertFromTrack(track));

        return trackItems;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

    public String getPreviewUrl() {
        return mPreviewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        mPreviewUrl = previewUrl;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getAlbumCoverImageUrl() {
        return mAlbumCoverImageUrl;
    }

    public void setAlbumCoverImageUrl(String albumCoverImageUrl) {
        mAlbumCoverImageUrl = albumCoverImageUrl;
    }

    public List<String> getArtistNameList() {
        return mArtistNameList;
    }

    public void setArtistNameList(List<String> artistNameList) {
        mArtistNameList = artistNameList;
    }

    public Long getDurationInMillisecond() {
        return mDurationInMillisecond;
    }

    public void setDurationInMillisecond(Long durationInMillisecond) {
        mDurationInMillisecond = durationInMillisecond;
    }
}
