package com.example.chart.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     ** Override this method if you needs work with the
     ** on data in your @{link ViewHolder}.
     * */
    open fun bind(data: T){}

    /**
     * Override this method if you needs to get notification on
     * last item displayed on.
     * */
    open fun isLastItem(){}
}