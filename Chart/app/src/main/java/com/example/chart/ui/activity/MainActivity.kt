package com.example.chart.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chart.R
import com.example.chart.model.Consumption
import com.example.chart.ui.activity.ConsumptionChartActivity.Companion.CONSUMPTION_LIST_EXTRA_KEY
import com.example.chart.ui.activity.ConsumptionFormActivity.Companion.CONSUMPTION_EXTRA_KEY
import com.example.chart.ui.activity.ConsumptionFormActivity.Companion.NEW_CONSUMPTION_CODE
import com.example.chart.ui.adapter.ConsumptionAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var consumptionAdapter: ConsumptionAdapter
    private lateinit var consumptionList: ArrayList<Consumption>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNewConsumptionIntentCreate()
        initializeAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.display_chart) {
            val intent = Intent(this@MainActivity, ConsumptionChartActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(CONSUMPTION_LIST_EXTRA_KEY, consumptionList)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        return false
    }

    private fun initializeAdapter() {
        consumptionList = ArrayList()
        createDemoConsumption()
        consumptionAdapter = ConsumptionAdapter(consumptionList)
        recycler_view_consumption.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = consumptionAdapter
        }
    }

    private fun createNewConsumptionIntentCreate() {
        fab_add_new_consumption.setOnClickListener {
            val intent = Intent(this@MainActivity, ConsumptionFormActivity::class.java)
            startActivityForResult(intent, NEW_CONSUMPTION_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null && data.hasExtra(CONSUMPTION_EXTRA_KEY)) {
            val consumption: Consumption =
                data.getSerializableExtra(CONSUMPTION_EXTRA_KEY) as Consumption
            consumptionAdapter.addItem(consumption)

        }
    }

    private fun createDemoConsumption() {
        consumptionList.add(Consumption(date = Date(), name = "Rice", value = 12.2, description = "Some kind of food."))
        consumptionList.add(Consumption(date = Date(), name = "Beans", value = 18.2, description = "Some kind of food."))
        consumptionList.add(Consumption(date = Date(), name = "Potato", value = 7.2, description = "Some kind of food."))
        consumptionList.add(Consumption(date = Date(), name = "Banana", value = 4.2, description = "Some kind of food."))
        consumptionList.add(Consumption(date = Date(), name = "Strawberry Pie", value = 10.2, description = "Some kind of food."))
        consumptionList.add(Consumption(date = Date(), name = "Soda", value = 2.2, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Coffee", value = 5.65, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Juice", value = 3.15, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Energy Drink", value = 8.55, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Milk", value = 5.45, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Tea", value = 3.75, description = "Some kind of drink."))
        consumptionList.add(Consumption(date = Date(), name = "Bear", value = 6.52, description = "Some kind of drink."))
    }
}
