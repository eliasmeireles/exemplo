package com.example.chart.extension

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun <T: View> RecyclerView.ViewHolder.findViewById(viewId: Int): T {
    return itemView.findViewById(viewId)
}