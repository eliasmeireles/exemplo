package com.example.chart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chart.ui.viewholder.BaseViewHolder

abstract class BaseAdapter<T>(private var items: ArrayList<T>) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
        holder(holder = holder)

        if (itemCount - 1 == position) {
            holder.isLastItem()
            isLastItem()
        }
    }

    /**
     * Override this method if you needs to get notification on
     * last item displayed on.
     * */
    open fun isLastItem(){}

    open fun holder(holder: BaseViewHolder<T>){}

    fun viewLayout(parent: ViewGroup, layoutId: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    open fun addItem(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    open fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open fun setItems(items: ArrayList<T>) {
        this.items = items
        notifyDataSetChanged()
    }
}