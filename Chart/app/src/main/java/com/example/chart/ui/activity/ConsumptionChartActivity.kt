package com.example.chart.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chart.R
import com.example.chart.model.Consumption
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

class ConsumptionChartActivity : AppCompatActivity() {

    private lateinit var consumptionList: ArrayList<Consumption>
    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private lateinit var combinedChart: CombinedChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_consumption)
        barChart = findViewById(R.id.bar_chart_data_display)
        lineChart = findViewById(R.id.line_chart_data_display)
        combinedChart = findViewById(R.id.combined_chart_data_display)

        getListOfConsumption()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getListOfConsumption() {
        if (intent != null && intent.hasExtra(CONSUMPTION_LIST_EXTRA_KEY)) {
            consumptionList =
                intent.getSerializableExtra(CONSUMPTION_LIST_EXTRA_KEY) as ArrayList<Consumption>

            barChartBuild()
            lineChartBuild()
            combinedChartBui()
        }
    }

    private fun combinedChartBui() {
        combinedChart.description.isEnabled = false
        combinedChart.setDrawGridBackground(false)
        combinedChart.setDrawBarShadow(false)
        combinedChart.setBackgroundColor(Color.WHITE)
        combinedChart.isHighlightFullBarEnabled = false
        combinedChart.drawOrder = arrayOf(CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE)
        combinedChartLegendSetting()
        axisSetting()

        val combinedData = CombinedData()
        combinedData.setData(lineDataBuild())
        combinedData.setData(barDataBuild())
        combinedChart.xAxis.mAxisMaximum = combinedData.xMax

        combinedChart.data = combinedData
        combinedChart.invalidate()
    }

    private fun axisSetting() {
        val axisRight = combinedChart.axisRight
        axisRight.setDrawGridLines(false)
        axisRight.axisMaximum = 0f

        val axisLeft = combinedChart.axisLeft
        axisLeft.setDrawGridLines(false)
        axisLeft.axisMinimum = 0f

        val xAxis = combinedChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
        xAxis.axisMaximum = 0f
        xAxis.axisMaximum = 1f
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return consumptionList[value.toInt()].name
            }
        }
    }

    private fun combinedChartLegendSetting() {
        val legend = combinedChart.legend
        legend.isWordWrapEnabled = false
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
    }

    private fun lineChartBuild() {
        lineChart.data = lineDataBuild()
        lineChart.invalidate()
        lineChart.description.isEnabled = false
    }

    private fun lineDataBuild(): LineData {
        val entryLineData = ArrayList<Entry>()
        consumptionList.forEach { consumption ->
            entryLineData.add(
                Entry(
                    consumptionList.indexOf(consumption).toFloat(),
                    consumption.value.toFloat() / 2
                )
            )
        }
        val lineDataSet = LineDataSet(entryLineData, "Consumption")
        lineDataSet.color = ContextCompat.getColor(this@ConsumptionChartActivity, R.color.colorDark)
        return LineData(lineDataSet)
    }

    private fun barChartBuild() {
        barChart.data = barDataBuild()
        barChart.animateX(600, Easing.EaseInBack)
        barChart.invalidate()
        barChart.description.isEnabled = false
        barChart.animate()
    }

    private fun barDataBuild(): BarData {
        val entryLineData = ArrayList<Entry>()
        val entryBarData = ArrayList<BarEntry>()
        consumptionList.forEach { consumption ->
            entryBarData.add(
                BarEntry(
                    consumptionList.indexOf(consumption).toFloat(),
                    consumption.value.toFloat(),
                    ContextCompat.getColor(this@ConsumptionChartActivity, R.color.colorPrimaryDark)
                )
            )

            entryLineData.add(
                Entry(
                    consumptionList.indexOf(consumption).toFloat(),
                    consumption.value.toFloat()
                )
            )
        }

        val lineDataSet = LineDataSet(entryLineData, "Consumption")
        lineDataSet.color = ContextCompat.getColor(this@ConsumptionChartActivity, R.color.colorDark)

        val barDataSet = BarDataSet(entryBarData, "Consumption")
        return BarData(barDataSet)
    }

    companion object {
        const val CONSUMPTION_LIST_EXTRA_KEY = "CONSUMPTION_LIST_EXTRA_KEY"
    }
}