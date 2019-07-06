package com.example.chart.ui.viewholder

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.example.chart.R
import com.example.chart.extension.findViewById
import com.example.chart.extension.formatAsString
import com.example.chart.model.Consumption

class ConsumptionViewHolder(itemView: View) : BaseViewHolder<Consumption>(itemView) {

    private val consumptionName = findViewById<TextView>(R.id.consumption_name)
    private val consumptionValue = findViewById<TextView>(R.id.consumption_value)
    private val consumptionDate = findViewById<TextView>(R.id.consumption_date)
    private val consumptionDescription =
        findViewById<TextView>(R.id.consumption_description)

    override fun bind(data: Consumption) {
        consumptionName.text = data.name
        consumptionValue.text = data.value.toString()
        consumptionDate.text = data.date.formatAsString()
        consumptionDescription.text = data.description
    }

    override fun isLastItem() {}
}