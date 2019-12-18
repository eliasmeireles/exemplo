package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.util.VLCUtil;

public class VLCInstance {
    public final static String TAG = "VLC/Util/VLCInstance";

    private static LibVLC sLibVLC = null;

    /**
     * A set of utility functions for the VLC application
     */
    public synchronized static LibVLC get(final Context context) throws IllegalStateException {
        if (sLibVLC == null) {
            if (!VLCUtil.hasCompatibleCPU(context)) {
                Log.e(TAG, VLCUtil.getErrorMsg());
                throw new IllegalStateException("LibVLC initialisation failed: " + VLCUtil.getErrorMsg());
            }

            try {
                sLibVLC = new LibVLC(context);
            } catch (Throwable e) {
                e.printStackTrace();
            }

        }
        return sLibVLC;
    }

    public static synchronized void restart(Context context) throws IllegalStateException {
        if (sLibVLC != null) {
            sLibVLC.release();
            sLibVLC = new LibVLC(context);
        }
    }
}
