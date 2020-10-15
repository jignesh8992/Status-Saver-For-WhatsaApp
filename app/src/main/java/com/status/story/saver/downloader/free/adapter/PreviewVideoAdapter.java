package com.status.story.saver.downloader.free.adapter;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.github.ybq.android.spinkit.SpinKitView;
import com.jignesh.jndroid.videoview.MediaController;
import com.jignesh.jndroid.videoview.UniversalMediaController;
import com.jignesh.jndroid.videoview.VideoView;
import com.status.story.saver.downloader.free.R;

import java.util.ArrayList;

public class PreviewVideoAdapter extends PagerAdapter {

    // Global Variables
    private String TAG = PreviewVideoAdapter.class.getSimpleName();
    private Context mContext;
    private int curr_position;
    private ArrayList<String> videoList;
    private int mSeekPosition = 0;
    private boolean isPlaying = true;
    private LayoutInflater inflater;

    // Views
    private VideoView videoView;
    private VideoView curr_videoView;
    private UniversalMediaController mediaController;


    // constructor
    public PreviewVideoAdapter(Context mContext, ArrayList<String> imagePaths, int curr_position) {
        this.mContext = mContext;
        this.videoList = imagePaths;
        this.curr_position = curr_position;
    }

    @Override
    public int getCount() {
        return this.videoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_preview_video, container, false);


        final SpinKitView progressBar = view.findViewById(R.id.progressbar);
        try {
            mediaController = view.findViewById(R.id.media_controller);
        } catch (Exception ignored) {
        }
        videoView = view.findViewById(R.id.video_view);
        videoView.setVideoViewCallback(new VideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {

            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) {
                System.out.println("Pause!");
                if (position == curr_position) mSeekPosition = videoView.getCurrentPosition();
                isPlaying = false;
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) {
                System.out.println("Play!");
                progressBar.setVisibility(View.GONE);
                isPlaying = true;
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {

            }
        });


        String video_path = videoList.get(position);
        if (position == curr_position) {
            progressBar.setVisibility(View.VISIBLE);
            playVideo(videoView, video_path);
        } else {
            videoView.pause();
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    public void removeView(int position) {
        videoList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void setPosition(int postion, boolean isPlaying) {
        this.curr_position = postion;
        this.isPlaying = isPlaying;
        notifyDataSetChanged();
    }


    private void playVideo(final VideoView videoView, String video_path) {

        try {
            curr_videoView = videoView;
            videoView.setVideoURI(Uri.parse(video_path));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (isPlaying) {
                        Log.d("IF", "isPlaying=" + isPlaying);
                        videoView.start();
                    } else {
                        Log.d("ELSE", "isPlaying=" + isPlaying);
                        videoView.start();
                        videoView.pause();
                        videoView.mMediaController.hideCenterView();
                    }

                }
            });
            videoView.seekTo(mSeekPosition);
            if (mediaController != null) videoView.setMediaController(mediaController);
        } catch (Exception e) {
            Log.d(TAG, "playVideo" + e.toString());

        }


    }

    public int getSeekPosition() {
        return mSeekPosition;
    }

    public void setSeekPosition(int mSeekPosition) {
        this.mSeekPosition = mSeekPosition;
    }

    public VideoView getVideoView() {
        return curr_videoView;
    }


}
