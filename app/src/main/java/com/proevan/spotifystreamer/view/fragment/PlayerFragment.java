package com.proevan.spotifystreamer.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.di.conponent.PlayerPresenterComponent;
import com.proevan.spotifystreamer.model.TrackItem;
import com.proevan.spotifystreamer.presenter.PlayerPresenter;
import com.proevan.spotifystreamer.presenter.SearchPresenter;
import com.proevan.spotifystreamer.view.PlayerView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerFragment extends Fragment implements PlayerView {

    private static final String ARG_PARAM_TRACK_ITEMS = "ARG_PARAM_TRACK_ITEMS";
    private static final String ARG_PARAM_PLAY_INDEX = "ARG_PARAM_PLAY_INDEX";

    private List<TrackItem> mTrackItems;
    private int mPlayIndex;

    @Inject
    PlayerPresenter mPresenter;

    @InjectView(R.id.artists_name)
    TextView mArtistsName;

    @InjectView(R.id.album_name)
    TextView mAlbumName;

    @InjectView(R.id.album_image)
    ImageView mAlbumImage;

    @InjectView(R.id.track_name)
    TextView mTrackName;

    @InjectView(R.id.previous_btn)
    TextView mPreviousButton;

    @InjectView(R.id.next_btn)
    TextView mNextButton;

    @InjectView(R.id.play_btn)
    TextView mPlayButton;

    @InjectView(R.id.pause_btn)
    TextView mPauseButton;

    public static PlayerFragment newInstance(List<TrackItem> trackItems, int playIndex) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_TRACK_ITEMS, Parcels.wrap(trackItems));
        args.putInt(ARG_PARAM_PLAY_INDEX, playIndex);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlayerPresenterComponent.Initializer.init(this).inject(this);
        if (getArguments() != null) {
            mTrackItems = Parcels.unwrap(getArguments().getParcelable(ARG_PARAM_TRACK_ITEMS));
            mPlayIndex = getArguments().getInt(ARG_PARAM_PLAY_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.inject(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(mTrackItems, mPlayIndex);
    }

    @Override
    public void setTrackItem(TrackItem trackItem) {
        if (trackItem.getTrackName() != null)
            mTrackName.setText(trackItem.getTrackName());
        if (trackItem.getAlbumName() != null)
            mAlbumName.setText(trackItem.getAlbumName());
        if (trackItem.getArtistNameList() != null) {
            mArtistsName.setText(
                    concatArtistsName(trackItem.getArtistNameList())
            );
        }
        if (trackItem.getAlbumCoverImageUrl() != null)
            Picasso.with(mAlbumImage.getContext())
                    .load(trackItem.getAlbumCoverImageUrl())
                    .into(mAlbumImage);
    }

    private String concatArtistsName(List<String> aritstNames) {
        String result = "";
        for (String artistName : aritstNames) {
            result += artistName;
        }

        return result;
    }

    @Override
    public void showPlayButton() {

    }

    @Override
    public void showPauseButton() {

    }
}
