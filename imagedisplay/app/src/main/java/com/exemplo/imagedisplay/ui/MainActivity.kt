package com.exemplo.imagedisplay.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.imagedisplay.R
import com.exemplo.imagedisplay.ui.adapter.ImagesAdapter
import com.exemplo.imagedisplay.util.ImageScaleGestureDetectorListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImagesAdapter.ImageScaleDelegate {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imagesDrawableIds = listOf(R.drawable.image01, R.drawable.image02, R.drawable.image03)
        val imagesAdapter = ImagesAdapter(this)

        recycler_view_images.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = imagesAdapter
            onFlingListener = null
        }

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recycler_view_images)
        imagesAdapter.addImages(imagesDrawableIds)
    }

    override fun setScaleGestureDetector(imageView: ImageView) {
        this.imageView = imageView
        scaleGestureDetector = ScaleGestureDetector(this, ImageScaleGestureDetectorListener(imageView = imageView))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector?.apply {
            this.onTouchEvent(event)
        }
        return true
    }
}
