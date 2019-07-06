package com.example.chart.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chart.R
import com.example.chart.model.Consumption
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ConsumptionChartActivity : AppCompatActivity() {

    private lateinit var consumptionList: ArrayList<Consumption>
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_consumption)
        lineChart = findViewById(R.id.chart_data_display)
        getListOfConsumption()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getListOfConsumption() {
        if (intent != null && intent.hasExtra(CONSUMPTION_LIST_EXTRA_KEY)) {
            consumptionList =
                intent.getSerializableExtra(CONSUMPTION_LIST_EXTRA_KEY) as ArrayList<Consumption>

            val entry = ArrayList<Entry>()
            consumptionList.forEach { consumption ->  entry.add(Entry(consumption.value.toFloat(), consumption.value.toFloat()))}

            val lineDataSet = LineDataSet(entry, "Consumption")
            lineDataSet.color = ContextCompat.getColor(this@ConsumptionChartActivity, R.color.colorPrimary)
            lineChart.data = LineData(lineDataSet)
            lineChart.invalidate()

        }
    }

    companion object {
        const val CONSUMPTION_LIST_EXTRA_KEY = "CONSUMPTION_LIST_EXTRA_KEY"
    }
}