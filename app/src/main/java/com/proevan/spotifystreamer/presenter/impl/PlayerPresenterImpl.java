package com.proevan.spotifystreamer.presenter.impl;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.R;
import com.proevan.spotifystreamer.SpotifyStreamerApplication;
import com.proevan.spotifystreamer.model.TrackItem;
import com.proevan.spotifystreamer.presenter.PlayerPresenter;
import com.proevan.spotifystreamer.view.PlayerView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class PlayerPresenterImpl implements PlayerPresenter, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final int SEEK_BAR_UPDATE_FREQUENCY_IN_MS = 16;
    private PlayerView mPlayerView;
    private MediaPlayer mMediaPlayer;
    private List<TrackItem> mTrackItems;
    private int mCurrentPlayingIndex;
    private boolean mIsPlayingMode = false;
    private boolean mIsPrepareStarted = false;

    private Runnable mSeekBarUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null && mIsPlayingMode) {
                mPlayerView.setPlayingProgress(mMediaPlayer.getCurrentPosition());
                new Handler().postDelayed(mSeekBarUpdateRunnable, SEEK_BAR_UPDATE_FREQUENCY_IN_MS);
            }
        }
    };

    @Inject
    public PlayerPresenterImpl(PlayerView playerView) {
        mPlayerView = playerView;
    }

    @Override
    public void onViewCreated(List<TrackItem> trackItems, int playIndex) {
        mTrackItems = trackItems;
        mCurrentPlayingIndex = playIndex;
        mPlayerView.setTrackItem(mTrackItems.get(mCurrentPlayingIndex));
    }

    @Override
    public void onPlayButtonClick() {
        mIsPlayingMode = true;
        mPlayerView.switchToPauseButton();
        playTrack();
    }

    private void playTrack() {
        new Handler().post(mSeekBarUpdateRunnable);
        if (mIsPrepareStarted)
            mMediaPlayer.start();
        else
            prepareMediaPlayer(getCurrentTrackItem().getPreviewUrl());
    }

    private TrackItem getCurrentTrackItem() {
        return mTrackItems.get(mCurrentPlayingIndex);
    }

    private void prepareMediaPlayer(String previewUrl) {
        mIsPrepareStarted = true;
        if (mMediaPlayer != null)
            mMediaPlayer.release();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setScreenOnWhilePlaying(true);
        try {
            mMediaPlayer.setDataSource(previewUrl);
            mMediaPlayer.prepareAsync();
            mIsPrepareStarted = true;
        } catch (IOException e) {
            Logger.e(e, "prepareMediaPlayer error");
            SpotifyStreamerApplication.showToast(R.string.message_prepare_media_player_error, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onPauseButtonClick() {
        mIsPlayingMode = false;
        mPlayerView.switchToPlayButton();
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    @Override
    public void onNextButtonClick() {
        mIsPrepareStarted = false;
        mCurrentPlayingIndex = getNextTrackIndex();
        mPlayerView.setTrackItem(getCurrentTrackItem());
        if (mIsPlayingMode)
            playTrack();
    }

    @Override
    public void onPreviousButtonClick() {
        mIsPrepareStarted = false;
        mCurrentPlayingIndex = getPreviousTrackIndex();
        mPlayerView.setTrackItem(getCurrentTrackItem());
        if (mIsPlayingMode)
            playTrack();
    }

    private int getNextTrackIndex() {
        if (mCurrentPlayingIndex + 1 >= mTrackItems.size())
            return 0;
        else
            return mCurrentPlayingIndex + 1;
    }

    private int getPreviousTrackIndex() {
        if (mCurrentPlayingIndex - 1 < 0)
            return mTrackItems.size() - 1;
        else
            return mCurrentPlayingIndex - 1;
    }

    @Override
    public void onViewPause() {
        onPauseButtonClick();
    }

    @Override
    public void onViewDestory() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayerView.setTrackDuration(mediaPlayer.getDuration());
        if (mIsPlayingMode)
            mMediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        onNextButtonClick();
    }
}
