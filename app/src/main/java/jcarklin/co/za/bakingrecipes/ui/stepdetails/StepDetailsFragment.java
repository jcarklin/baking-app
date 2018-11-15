package jcarklin.co.za.bakingrecipes.ui.stepdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jcarklin.co.za.bakingrecipes.R;
import jcarklin.co.za.bakingrecipes.repository.model.Step;

public class StepDetailsFragment extends Fragment {

    private Step selectedStep;

    @BindView(R.id.pv_step_video)
    PlayerView playerView;
    @BindView(R.id.tv_step_description)
    @Nullable
    TextView stepDescription;
    @BindView(R.id.step_thumbnail)
    @Nullable
    ImageView stepThumbnail;

    private SimpleExoPlayer exoPlayer;
    private long exoPlayerPosition;

    public StepDetailsFragment() {

    }

    public static StepDetailsFragment newInstance() {
        StepDetailsFragment fragment = new StepDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            exoPlayerPosition = savedInstanceState.getLong("playerPosition");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playerPosition", exoPlayerPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this,view);
        playerView.setDefaultArtwork(getResources().getDrawable(R.drawable.ic_cake_black_48dp));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateUi();
    }

    private void populateUi() {
        if (stepDescription != null) {
            stepDescription.setText(selectedStep.getDescription());
        }
        if (selectedStep.getVideoURL() != null && !selectedStep.getVideoURL().isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            stepThumbnail.setVisibility(View.GONE);
            initializePlayer(Uri.parse(selectedStep.getVideoURL()));
        } else {
            playerView.setVisibility(View.GONE);
            stepThumbnail.setVisibility(View.VISIBLE);
            if (selectedStep.getThumbnailURL() != null && !selectedStep.getThumbnailURL().isEmpty()) {
                Picasso.get()
                        .load(selectedStep.getThumbnailURL())
                        .placeholder(R.drawable.ic_cake_black_48dp)
                        .error(R.drawable.ic_cake_black_48dp)
                        .into(stepThumbnail);
            } else {
                stepThumbnail.setImageDrawable(getResources().getDrawable(R.drawable.ic_cake_black_48dp));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void initializePlayer(Uri videoUri) {
        if (exoPlayer==null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), renderersFactory,trackSelector,loadControl);
            playerView.setPlayer(exoPlayer);
        }
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(videoUri);
        exoPlayer.prepare(mediaSource,true,false);
        exoPlayer.seekTo(exoPlayerPosition);
        exoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayerPosition = exoPlayer.getContentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setStep(Step step) {
        this.selectedStep = step;
    }
}
