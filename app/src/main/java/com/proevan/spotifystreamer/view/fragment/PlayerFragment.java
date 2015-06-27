package com.proevan.spotifystreamer.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import butterknife.OnClick;

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

    @InjectView(R.id.play_btn)
    ImageButton mPlayButton;

    @InjectView(R.id.pause_btn)
    ImageButton mPauseButton;

    @InjectView(R.id.seekbar)
    SeekBar mSeekBar;

    @OnClick(R.id.play_btn)
    public void onPlayButtonClick() {
        mPresenter.onPlayButtonClick();
    }

    @OnClick(R.id.pause_btn)
    public void onPauseButtonClick() {
        mPresenter.onPauseButtonClick();
    }

    @OnClick(R.id.next_btn)
    public void onNextButtonClick() {
        mPresenter.onNextButtonClick();
    }

    @OnClick(R.id.previous_btn)
    public void onPreviousButtonClick() {
        mPresenter.onPreviousButtonClick();
    }

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
        disableSeekBarTouch();

        return fragmentView;
    }

    private void disableSeekBarTouch() {
        mSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(mTrackItems, mPlayIndex);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onViewPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onViewDestory();
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
        if (trackItem.getDurationInMillisecond() != null)
            mSeekBar.setMax(trackItem.getDurationInMillisecond().intValue());
    }

    private String concatArtistsName(List<String> aritstNames) {
        String result = "";
        for (String artistName : aritstNames) {
            result += artistName;
        }

        return result;
    }

    @Override
    public void switchToPlayButton() {
        mPlayButton.setVisibility(View.VISIBLE);
        mPauseButton.setVisibility(View.GONE);
    }

    @Override
    public void switchToPauseButton() {
        mPlayButton.setVisibility(View.GONE);
        mPauseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSeekBarMax(int max) {
        mSeekBar.setMax(max);
    }

    @Override
    public void setSeekBarProgress(int progress) {
        mSeekBar.setProgress(progress);
    }
}