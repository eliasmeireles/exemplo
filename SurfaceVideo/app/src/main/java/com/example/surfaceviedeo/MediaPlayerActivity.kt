package com.example.surfaceviedeo

import android.graphics.Point
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.exemplo.mediaplayer.DataSource
import kotlinx.android.synthetic.main.activity_media_player.*
import java.lang.Exception

const val DATA_SOURCE_KEY = "DATA_SOURCE_KEY"

class MediaPlayerActivity : AppCompatActivity(), SurfaceHolder.Callback {


    private lateinit var mediaPlayer: MediaPlayer
    //    private val rtspUrl = "rtsp://192.168.0.101:8080/video/h264"
    private var rtspUrl =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_media_player)

        val intent = intent
        if (intent != null && intent.hasExtra(DATA_SOURCE_KEY)) {
            val dataSource = intent.getParcelableExtra<DataSource>(DATA_SOURCE_KEY)

            if (dataSource != null) {
                dataSource.sources?.apply {
                    rtspUrl = this
                }
            }
        }

        val holder = surface_view.holder

        holder.addCallback(this)
    }

    override fun onResume() {
        super.onResume()
        videoLoading.visibility = View.VISIBLE
    }

    override fun onStop() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpSurface(surface: Surface) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setSurface(surface)
        mediaPlayer.setAudioAttributes(createAudioAttributes())
        mediaPlayer.setOnCompletionListener {
            if (!it.isPlaying) {
                Toast.makeText(this, "Não foi possivel reproduzir a mídia!", Toast.LENGTH_LONG)
                    .show()
                finish()
            }
        }
        try {
            mediaPlayer.setDataSource(rtspUrl)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Não foi possivel reproduzir a mídia!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setSurfaceDimension(mediaPlayer: MediaPlayer, width: Int, height: Int) {
        if (width > 0 && height > 0) {
            val aspectRation = height.toFloat() / width.toFloat()
            val screenDimensions = Point()
            windowManager.defaultDisplay.getSize(screenDimensions)
            val surfaceWidth = screenDimensions.x
            val surfaceHeight = (surfaceWidth * aspectRation).toInt()
            val param = FrameLayout.LayoutParams(surfaceWidth, surfaceHeight)
            surface_view.layoutParams = param
            val surfaceHolder = surface_view.holder
            mediaPlayer.setDisplay(surfaceHolder)
        }
    }

    private fun prepareMediaPlayer() {
        try {
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Não foi possivel reproduzir a mídia!", Toast.LENGTH_LONG).show()
            finish()
        }
        mediaPlayer.setOnPreparedListener {
            //            mediaPlayer.seekTo(plabackPosition)
            mediaPlayer.start()
            videoLoading.visibility = View.GONE
        }

        mediaPlayer.setOnVideoSizeChangedListener { player, width, height ->
            setSurfaceDimension(player, width, height)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val surface = holder.surface
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpSurface(surface)
            prepareMediaPlayer()
        }
    }
}
