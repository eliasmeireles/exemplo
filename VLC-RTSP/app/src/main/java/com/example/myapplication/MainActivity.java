package com.example.myapplication;

import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,
        org.videolan.libvlc.MediaPlayer.EventListener {

    private TextureView textureView = null;
    private View loadingContainer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);
        textureView = findViewById(R.id.texture_view);
        loadingContainer = findViewById(R.id.loading_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textureView.setSurfaceTextureListener(this);
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        LibVLC libVLC = VLCInstance.get(this);
        mediaPlayer = new MediaPlayer(libVLC);
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onStop();
    }

    private void settingUpPlayer() {
        final IVLCVout playerVLCVout = mediaPlayer.getVLCVout();
        mediaPlayer.setScale(0);
        playerVLCVout.detachViews();
        playerVLCVout.setVideoView(textureView);
        playerVLCVout.setWindowSize(textureView.getWidth(), textureView.getHeight());
        playerVLCVout.attachViews();
        textureView.setKeepScreenOn(true);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        settingUpPlayer();
//       Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
//        Uri uri = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
        Uri uri = Uri.parse("rtsp://192.168.0.101:8080/video/h264");

//        Uri uri = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
        Media media = new Media(VLCInstance.get(this), uri);
        mediaPlayer.setMedia(media);
        mediaPlayer.setEventListener(this);
        mediaPlayer.play(uri);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onEvent(org.videolan.libvlc.MediaPlayer.Event event) {
        Log.e("BUFFER_SIZE", String.valueOf(event.getBuffering()));
        if (event.type == org.videolan.libvlc.MediaPlayer.Event.Buffering) {
            loadingContainer.setVisibility(View.VISIBLE);
            if (event.getBuffering() > 0) {
                loadingContainer.setVisibility(View.GONE);
            }
        }
    }
}
