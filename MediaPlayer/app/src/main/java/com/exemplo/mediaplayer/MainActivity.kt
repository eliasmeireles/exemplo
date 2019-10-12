package com.exemplo.mediaplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.media.AudioAttributesCompat
import androidx.media2.common.MediaMetadata
import androidx.media2.common.SessionPlayer
import androidx.media2.common.UriMediaItem
import androidx.media2.player.MediaPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), DataSourceDelegate {
    private var videoPlayer: MediaPlayer? = null
    private var videoPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view_data_source.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = DataSourceAdapter(this@MainActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoPlayer != null) {
            videoPlayer?.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (videoPlayer != null) {
            videoPlayer?.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (videoPlayer != null) {
            videoPlayer?.reset()
        }
    }

    override fun onResume() {
        super.onResume()
        if (videoPlayer != null) {
            Log.e("State", videoPlayer?.playerState.toString())
            video_view_player.setPlayer(videoPlayer!!)
            videoPlayer?.play()
        }
    }

    override fun onBackPressed() {
        if (videoPlaying && videoPlayer != null) {
            videoPlayer?.reset()
            closeVideo()
        } else {
            super.onBackPressed()
        }
    }

    override fun dataSource(dataSource: DataSource) {

        val mediaMetaData = MediaMetadata.Builder()
            .putString(MediaMetadata.METADATA_KEY_TITLE, dataSource.title)
            .build()

        val mediaItem =
            UriMediaItem.Builder(dataSource.sources.toUri())
                .setMetadata(mediaMetaData)
                .build()
        video_view_player.visibility = View.VISIBLE
        videoPlaying = true
        close_video.visibility = View.VISIBLE

        MediaPlayer(this).apply {
            this.setAudioAttributes(
                AudioAttributesCompat.Builder().setContentType(AudioAttributesCompat.CONTENT_TYPE_MOVIE).build()
            )

            video_view_player.setPlayer(this)
            this@MainActivity.videoPlayer = this
            setMediaItem(mediaItem)
            close_video.setOnClickListener {
                reset()
                closeVideo()
            }

            prepare().addListener(
                Runnable {
                    play()
                },
                Executors.newSingleThreadExecutor()
            )
        }
    }

    private fun closeVideo() {
        close_video.visibility = View.GONE
        video_view_player.visibility = View.GONE
        videoPlaying = false
    }
}
