package com.example.chart.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chart.R
import com.example.chart.model.Consumption
import com.example.chart.ui.activity.ConsumptionFormActivity.Companion.CONSUMPTION_EXTRA_KEY
import com.example.chart.ui.activity.ConsumptionFormActivity.Companion.NEW_CONSUMPTION_CODE
import com.example.chart.ui.adapter.ConsumptionAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var consumptionAdapter: ConsumptionAdapter
    private lateinit var consumptionList: ArrayList<Consumption>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNewConsumptionIntentCreate()
        initializeAdapter()
    }

    private fun initializeAdapter() {
        consumptionList = ArrayList()
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
}
