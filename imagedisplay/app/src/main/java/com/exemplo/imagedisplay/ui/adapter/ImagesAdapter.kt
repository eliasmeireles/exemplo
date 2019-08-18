package com.exemplo.imagedisplay.ui.adapter

import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.imagedisplay.R
import com.exemplo.imagedisplay.util.ImageScaleGestureDetectorListener

class ImagesAdapter(val imageScaleDelegate: ImageScaleDelegate): RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var imagesDrawableId = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.adapter_image_layout, parent, false)
        return ImageViewHolder(imageScaleDelegate, layout)
    }

    override fun getItemCount(): Int {
        return imagesDrawableId.size
    }

    fun addImages(imageDrawableId: List<Int>) {
        this.imagesDrawableId.addAll(imageDrawableId)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imagesDrawableId[position])
    }

    class ImageViewHolder (imageScaleDelegate: ImageScaleDelegate, itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)

        init {
            imageScaleDelegate.setScaleGestureDetector(image)
        }

        fun bind(imageDrawableId: Int) {
            image.setImageDrawable(ContextCompat.getDrawable(itemView.context, imageDrawableId))
        }
    }

    interface ImageScaleDelegate {
        fun setScaleGestureDetector(imageView: ImageView)
    }
}