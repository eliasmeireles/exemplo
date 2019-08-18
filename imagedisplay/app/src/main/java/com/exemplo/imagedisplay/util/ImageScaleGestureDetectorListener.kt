package com.exemplo.imagedisplay.util

import android.view.ScaleGestureDetector
import android.widget.ImageView
import kotlin.math.max
import kotlin.math.min

class ImageScaleGestureDetectorListener(val imageView: ImageView) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private var mScaleFactor = 1.0f

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        mScaleFactor *= detector.scaleFactor
        mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
        imageView.scaleX = mScaleFactor
        imageView.scaleY = mScaleFactor
        return true
    }
}