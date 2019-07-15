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
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter

class ConsumptionChartActivity : AppCompatActivity() {

    private lateinit var consumptionList: ArrayList<Consumption>
    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private lateinit var combinedChart: CombinedChart
    private lateinit var colors: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_consumption)
        barChart = findViewById(R.id.bar_chart_data_display)
        lineChart = findViewById(R.id.line_chart_data_display)
        combinedChart = findViewById(R.id.combined_chart_data_display)

        colors = ArrayList()
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.jan))
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.feb))
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.mar))
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.apr))
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.jun))
        colors.add(ContextCompat.getColor(this@ConsumptionChartActivity, R.color.jul))

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
        combinedChartLegendSetting()
        axisSetting()
        combinedChart.description.isEnabled = false
        combinedChart.setScaleEnabled(false)
        combinedChart.setDrawGridBackground(false)
        combinedChart.setDrawBarShadow(false)
        combinedChart.setBackgroundColor(Color.WHITE)
        combinedChart.isHighlightFullBarEnabled = false
        combinedChart.drawOrder = arrayOf(CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE)

        val combinedData = CombinedData()
        combinedData.setData(barDataBuild())
        combinedData.setData(lineDataBuild())
        combinedChart.xAxis.mAxisMaximum = combinedData.xMax
        val xAxis = combinedChart.xAxis
        xAxis.axisMaximum = combinedData.xMax + 0.35F
        combinedChart.data = combinedData
        combinedChart.invalidate()
    }

    private fun axisSetting() {
        val axisRight = combinedChart.axisRight
        axisRight.isEnabled = false

        val axisLeft = combinedChart.axisLeft
        axisLeft.setDrawGridLines(false)
        axisLeft.axisMinimum = 0f

        val xAxis = combinedChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -45F
        xAxis.granularity = 1f
        xAxis.axisMinimum = -0.4f
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "jun/2018"
            }
        }
    }

    private fun combinedChartLegendSetting() {
        val legend = combinedChart.legend
        legend.isEnabled = false
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
        lineDataSet.color = Color.rgb(45, 55, 70)
        lineDataSet.lineWidth = 2.5f
        lineDataSet.setCircleColor(Color.rgb(55, 55, 70))
        lineDataSet.circleRadius = 5f
        lineDataSet.fillColor = Color.rgb(20, 55, 70)
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setDrawValues(true)
        lineDataSet.valueTextSize = 10f
        lineDataSet.valueTextColor = Color.rgb(55, 65, 70)
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
        val entryBarData = ArrayList<BarEntry>()
        consumptionList.forEach { consumption ->
            entryBarData.add(
                BarEntry(
                    consumptionList.indexOf(consumption).toFloat(),
                    consumption.value.toFloat()
                )
            )
        }

        val barDataSet = BarDataSet(entryBarData, "Consumption")
        barDataSet.colors = colors
        return BarData(barDataSet)
    }

    companion object {
        const val CONSUMPTION_LIST_EXTRA_KEY = "CONSUMPTION_LIST_EXTRA_KEY"
    }
}