package br.com.tech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tech.R
import kotlinx.android.synthetic.main.adapater_see_more_layout.view.*

abstract class BaseAdapter<T : ItemViewAdapter>(protected val delegate: Delegate?) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val items = arrayListOf<ItemViewAdapter>()

    companion object {
        const val SEE_MORE_VIEW_TYPE = -1
    }

    private val seeMoreViewObject = object : ItemViewAdapter {
        override fun getViewType(): Int {
            return SEE_MORE_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T> {
        return when (viewType) {
            SEE_MORE_VIEW_TYPE -> seeMoreViewHolder(viewGroup = parent)
            else -> getViewHolder(parent = parent)
        }
    }

    abstract fun getViewHolder(parent: ViewGroup): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = items[position]
        @Suppress("UNCHECKED_CAST")
        holder.bind(data = item as T)
    }

    fun setItems(items: List<ItemViewAdapter>) {
        this.items.clear()
        this.items.addAll(items)
        hasNextPage()
    }

    fun addItems(items: List<ItemViewAdapter>) {
        this.items.remove(seeMoreViewObject)
        this.items.addAll(items)
        hasNextPage()
    }

    private fun hasNextPage() {
        delegate?.let {
            if (it.hasNextPage()) {
                items.add(seeMoreViewObject)
            }
        }
        notifyDataSetChanged()
    }

    fun addItem(item: ItemViewAdapter) {
        this.items.remove(seeMoreViewObject)
        this.items.add(item)
        notifyDataSetChanged()
        hasNextPage()
    }


    private fun seeMoreViewHolder(viewGroup: ViewGroup): BaseViewHolder<T> {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapater_see_more_layout, viewGroup, false)

        return object : BaseViewHolder<T>(itemView) {

            init {
                delegate?.let {
                    itemView.findViewById<View>(R.id.see_more_view_container).setOnClickListener {
                        itemView.see_more_text.visibility = View.GONE
                        itemView.progress_loading.visibility = View.VISIBLE
                        delegate.nextPage()
                    }
                }
            }

            override fun bind(data: T) {
                itemView.see_more_text.visibility = View.VISIBLE
                itemView.progress_loading.visibility = View.GONE
            }
        }
    }

    interface Delegate {
        fun nextPage()
        fun hasNextPage(): Boolean
    }
}