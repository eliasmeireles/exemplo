package com.example.chart.ui.adapter

import android.view.ViewGroup
import com.example.chart.R
import com.example.chart.model.Consumption
import com.example.chart.ui.viewholder.BaseViewHolder
import com.example.chart.ui.viewholder.ConsumptionViewHolder

class ConsumptionAdapter(items: ArrayList<Consumption>) : BaseAdapter<Consumption>(items = items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Consumption> {
        return ConsumptionViewHolder(viewLayout(parent = parent, layoutId =  R.layout.adapter_consumption_layout))
    }

    override fun holder(holder: BaseViewHolder<Consumption>) {

    }
}