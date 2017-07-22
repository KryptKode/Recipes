package com.kryptkode.cyberman.recipe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Cyberman on 7/21/2017.
 */
    /**
     * A fullscreen activity to play audio or video streams.
     */
    public class RecipeVideoPlayerActivity extends AppCompatActivity {
        public static final String VIDEO_URL = "video";

        private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

        public static final String PLAY_WHEN_READY = "play_when_ready";
        public static final String CURRENT_WINDOW = "current_window";
        public static final String PLAY_BACK_POSITION = "play_back_position";

        private ExoPlayerListenter exoPlayerListenter;
        private ProgressBar progressBar;
        private SimpleExoPlayerView playerView;
        private SimpleExoPlayer player;
        private boolean playWhenReady;
        private int currentWindow;
        private long playbackPosition;
        private String videoUrl;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe_video_player);
            playerView = (SimpleExoPlayerView) findViewById(R.id.video_view);

            exoPlayerListenter = new ExoPlayerListenter();
            progressBar = (ProgressBar) findViewById(R.id.player_progress_bar);
            if (getIntent().hasExtra(VIDEO_URL)) {
                videoUrl = getIntent().getStringExtra(VIDEO_URL);
            }

            if (savedInstanceState != null) {
                playbackPosition = savedInstanceState.getLong(PLAY_BACK_POSITION);
                currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            }else{
                playbackPosition = 0;
                currentWindow = 0;
                playWhenReady = true;
            }


        }


        private void initializePlayer() {
           /* // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);*/
            // using a DefaultTrackSelector with an adaptive video selection factory
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.addListener(exoPlayerListenter);

            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);

        }

        private MediaSource buildMediaSource(Uri uri) {
            /*DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
            DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                    dataSourceFactory);
            return new DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null);*/
            return new ExtractorMediaSource(uri,
                    new DefaultHttpDataSourceFactory("ua"),
                    new DefaultExtractorsFactory(), null ,null);

        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
            outState.putLong(PLAY_BACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW, currentWindow);
            super.onSaveInstanceState(outState);
        }

        @Override
        protected void onStart() {
            super.onStart();
            if(Util.SDK_INT > 23){
                initializePlayer();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            hideSystemUi();
            if (Util.SDK_INT<=23 || player == null){
                initializePlayer();
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            if (Util.SDK_INT <=23){
                releasePlayer();
            }
        }

        private void releasePlayer() {
            playWhenReady = player.getPlayWhenReady();
            currentWindow = player.getCurrentWindowIndex();
            playbackPosition = player.getCurrentPosition();
            player.removeListener(exoPlayerListenter);
            player.release();
            player = null;


        }

        @Override
        protected void onStop() {
            super.onStop();
            if (Util.SDK_INT > 23){
                releasePlayer();
            }
        }

        @SuppressLint("InlinedApi")
        private void hideSystemUi() {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        private class ExoPlayerListenter implements ExoPlayer.EventListener {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState){
                    case ExoPlayer.STATE_IDLE:
                        showProgressBar();
                        Log.i("player", "onPlayerStateChanged: IDLE " + playWhenReady);
                        break;
                    case ExoPlayer.STATE_READY:
                        hideProgressBar();
                        Log.i("player", "onPlayerStateChanged: READY " + playWhenReady);
                        break;

                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        }

        private void showProgressBar() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private void hideProgressBar() {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


