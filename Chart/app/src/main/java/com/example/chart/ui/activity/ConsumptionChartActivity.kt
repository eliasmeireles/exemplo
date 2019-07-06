package com.example.chart.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chart.R
import com.example.chart.model.Consumption
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*

class ConsumptionChartActivity : AppCompatActivity() {

    private lateinit var consumptionList: ArrayList<Consumption>
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_consumption)
        barChart = findViewById(R.id.chart_data_display)
        getListOfConsumption()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getListOfConsumption() {
        if (intent != null && intent.hasExtra(CONSUMPTION_LIST_EXTRA_KEY)) {
            consumptionList =
                intent.getSerializableExtra(CONSUMPTION_LIST_EXTRA_KEY) as ArrayList<Consumption>

            val entry = ArrayList<BarEntry>()
            consumptionList.forEach { consumption ->
                entry.add(
                    BarEntry(
                        consumptionList.indexOf(consumption).toFloat(),
                        consumption.value.toFloat(),
                        ContextCompat.getColor(this@ConsumptionChartActivity, R.color.colorPrimaryDark)
                    )
                )
            }

            val barDataSet = BarDataSet(entry, "Consumption")
            barChart.data = BarData(barDataSet)
            barChart.invalidate()
            barChart.description.isEnabled = false

        }
    }

    companion object {
        const val CONSUMPTION_LIST_EXTRA_KEY = "CONSUMPTION_LIST_EXTRA_KEY"
    }
}