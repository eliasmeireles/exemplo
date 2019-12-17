package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.videolan.libvlc.AWindow;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.media.VideoView;
import org.videolan.libvlc.util.VLCVideoLayout;

public class MainActivity extends AppCompatActivity {

    private boolean mNativeStarted = false;
    private VLCVideoLayout mVideoSurface = null;
    private AWindow aWindow = null;
    private View.OnLayoutChangeListener mOnLayoutChangeListener = null;

    private static int sInit = -1;
    private MediaPlayer mediaPlayer;

    static synchronized boolean loadLibraries(Context context) {
        if (sInit != -1) return sInit == 1;
        try {
            System.loadLibrary("c++_shared");
            System.loadLibrary("vlc");
            System.loadLibrary("vlcjni");
        } catch (UnsatisfiedLinkError ule) {
            Toast.makeText(context, "Can't load vlcjni library: " + ule, Toast.LENGTH_LONG).show();
            sInit = 0;
            return false;
        } catch (SecurityException se) {
            Toast.makeText(context, "Encountered a security issue when loading vlcjni library: " + se, Toast.LENGTH_LONG).show();
            sInit = 0;
            return false;
        }
        sInit = 1;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!loadLibraries(this)) {
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);
        mVideoSurface = findViewById(R.id.video_surface);
        aWindow = new AWindow(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        surfaceView.setMediaController(new MediaController(this));
//        aWindow.setVideoView(surfaceView);
//        aWindow.attachViews();
//        if (mOnLayoutChangeListener == null) {
//            mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right,
//                                           int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
//                        aWindow.setWindowSize(right - left, bottom - top);
//                    }
//                }
//            };
//        }
//        mVideoSurface.addOnLayoutChangeListener(mOnLayoutChangeListener);
//        Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        Uri uri = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
//        Uri uri = Uri.parse("rtsp://192.168.0.101:8080/video/h264");
        LibVLC libVLC = new LibVLC(this);
        mediaPlayer = new MediaPlayer(new Media(libVLC, uri));
        mediaPlayer.attachViews(mVideoSurface, null, false, false);
        mediaPlayer.setAudioDigitalOutputEnabled(false);
        mediaPlayer.setVideoScale(MediaPlayer.ScaleType.SURFACE_FIT_SCREEN);
        mediaPlayer.play();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mOnLayoutChangeListener != null) {
            mVideoSurface.removeOnLayoutChangeListener(mOnLayoutChangeListener);
            mOnLayoutChangeListener = null;
        }

        if (mNativeStarted) {
            nativeStop();
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


    private long mInstance;

    private native boolean nativeCreate();

    private native void nativeDestroy();

    private native boolean nativeStart(AWindow aWindowNative);

    private native void nativeStop();
}
