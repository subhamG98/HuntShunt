package com.github.florent37.materialviewpager.subham;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view);

        VideoURL = TestRecyclerViewAdapter1.video1;
        VideoView vidView = (VideoView) findViewById(R.id.myVideo);

        Uri vidUri = Uri.parse(VideoURL);

        Log.i("videochecki", "" + VideoURL);
        vidView.setVideoURI(vidUri);

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();
        // Find your VideoView in your video_main.xml layout
        // Execute StreamVideo AsyncTask

    }

}