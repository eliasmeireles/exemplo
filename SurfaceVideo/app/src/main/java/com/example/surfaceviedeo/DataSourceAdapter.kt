package com.example.surfaceviedeo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exemplo.mediaplayer.DataSource


fun <T : View> RecyclerView.ViewHolder.findViewById(viewId: Int): T {
    return itemView.findViewById(viewId)
}

class DataSourceAdapter(val dataSourceDelegate: DataSourceDelegate) :
    RecyclerView.Adapter<DataSourceAdapter.ViewHolder>() {


    val dataSources = dataSourceList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_data_source,
                parent, false
            ),
            dataSourceDelegate
        )
    }

    override fun getItemCount(): Int {
        return dataSources.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSource = dataSources[position])
    }

    class ViewHolder(itemView: View, val dataSourceDelegate: DataSourceDelegate) :
        RecyclerView.ViewHolder(itemView) {

        val dataResourcePlay = findViewById<ImageButton>(R.id.data_source_play)
        val dataResourceTitle = findViewById<TextView>(R.id.data_source_title)
        val dataResourceThumb = findViewById<ImageView>(R.id.data_source_thumb)


        fun bind(dataSource: DataSource) {
            dataResourceTitle.text = dataSource.title
            Glide
                .with(itemView.context)
                .load(dataSource.thumb)
                .centerCrop()
                .into(dataResourceThumb)

            dataResourcePlay.setOnClickListener {
                dataSourceDelegate.dataSource(dataSource = dataSource)
            }

        }
    }
}