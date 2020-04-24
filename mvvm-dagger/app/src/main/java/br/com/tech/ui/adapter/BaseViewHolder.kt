package br.com.tech.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : ItemViewAdapter>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}