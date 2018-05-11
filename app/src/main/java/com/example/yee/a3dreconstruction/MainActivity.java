package com.example.yee.a3dreconstruction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int PICK_VIDEO_REQUEST = 1001;
    private VideoView mVideoView;
    private Button mCaptureButton;
    private Button mUploadButton;
    private Button mChooseButton;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = (VideoView) findViewById(R.id.viewbutton);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setBackgroundResource(R.drawable.hero);

        mChooseButton = (Button) findViewById(R.id.choosebutton);
        mChooseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
            }
        });

        mUploadButton = (Button) findViewById(R.id.uploadbutton);
        mUploadButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                if(videoUri != null) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("video/mp4");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
                    startActivity(Intent.createChooser(sharingIntent, "Share Video!"));
                }
            }
        });

        mCaptureButton = (Button) findViewById(R.id.capturebutton);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
    }
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            Log.d("Video URI", videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.setBackgroundColor(Color.TRANSPARENT);
            mVideoView.start();
        }
        if(requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK)
        {
            videoUri = intent.getData();
            Log.d("Video URI", videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            mVideoView.setBackgroundColor(Color.TRANSPARENT);
            mVideoView.start();
        }
    }

}
