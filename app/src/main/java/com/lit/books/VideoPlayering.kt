package com.lit.books

import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_video_playering.*

class VideoPlayering : AppCompatActivity() {
    // on below line we are creating
    // a variable for our video view.
    private lateinit var videoView: VideoView
    // on below line we are creating
    // a variable for our video url.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playering)
        val prefs = getSharedPreferences("store", MODE_PRIVATE)
        val videolink = prefs.getString("video_link", "")
        val activity_name = prefs.getString("activity_name", "")

        var videoUrl =
            "https://modcom.co.ke/lit/litt.mp4"
        // on below line we are initializing
        // our buttons with id.
        videoView = findViewById(R.id.videoView);
        idTVHeading.text = "$activity_name"
        val progress = findViewById<ProgressBar>(R.id.progress_circular)
        // on below line we are creating
        // uri for our video view.
        val uri: Uri = Uri.parse("https://modcom.co.ke/lit/"+videolink)
        // on below line we are setting
        // video uri for our video view.
        //Toast.makeText(applicationContext, "Please Wait... Video Loading"+videolink, Toast.LENGTH_LONG).show()
        videoView.setVideoURI(uri)
        progress.visibility = View.VISIBLE
        // on below line we are creating variable
        // for media controller and initializing it.
        val mediaController = MediaController(this)
        //mediaController.show(10000)
        // on below line we are setting anchor
        // view for our media controller.
        mediaController.setAnchorView(videoView)
//        if(videoView.isPlaying){
//            progress.visibility = View.GONE
//        }
//        else {
//            progress.visibility = View.VISIBLE
//        }
        // on below line we are setting media player
        // for our media controller.
        mediaController.setMediaPlayer(videoView)
//        mediaController.show(0);
        // on below line we are setting media
        // controller for our video view.
        videoView.setMediaController(mediaController)
        // on below line we are
        // simply starting our video view.
//        videoView.start()
//        videoView.requestFocus();

        videoView.setOnPreparedListener(OnPreparedListener {
            progress.visibility = View.GONE
            videoView.start()
        })

        videoView.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra -> // do something when an error is occur during the video playback
            Toast.makeText(applicationContext, "Error Occurred, Check Your Connections", Toast.LENGTH_SHORT).show()
            false
        })
    }
}