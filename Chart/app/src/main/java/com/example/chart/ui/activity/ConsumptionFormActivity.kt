package com.example.chart.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.chart.R
import com.example.chart.extension.formatAsString
import com.example.chart.model.Consumption
import kotlinx.android.synthetic.main.activity_consuption_form.*
import java.util.*
import kotlin.collections.ArrayList


class ConsumptionFormActivity : AppCompatActivity() {

    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consuption_form)
        getConsumptionDate()
        createNewConsumption()
    }

    private fun createNewConsumption() {
        fab_create_new_consumption.setOnClickListener {
            if (isFormValid()) {
                val consumptionValue: Double = consumption_value.text.toString().toDouble()
                val consumptionName = consumption_name.text.toString()
                val consumptionDescription = consumption_description.text.toString()

                val consumption = Consumption(
                    name = consumptionName,
                    date = selectedDate!!,
                    value = consumptionValue,
                    description = consumptionDescription
                )

                val intent = Intent()
                val bundle = Bundle()
                bundle.putSerializable(CONSUMPTION_EXTRA_KEY, consumption)
                intent.putExtras(bundle)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun isFormValid(): Boolean {
        val inputs = ArrayList<EditText>()
        inputs.add(consumption_date)
        inputs.add(consumption_description)
        inputs.add(consumption_value)
        inputs.add(consumption_name)

        var hasError = true
        inputs.forEach { input ->
            if (input.text.toString().trim().isEmpty()) {
                input.error = "This is a required field!"
                hasError = false
            } else {
                input.error = null
            }
        }
        return hasError
    }

    private fun getConsumptionDate() {
        consumption_date.setOnClickListener {
            dateSelectDialog()
        }
    }

    private fun dateSelectDialog() {
        val calendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate = calendar.time
            consumption_date.setText(selectedDate?.formatAsString())
        }

        val datePickerDialog = DatePickerDialog(
            this@ConsumptionFormActivity,
            date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.setOnCancelListener {
            selectedDate = null
            consumption_date.text = null
        }
        datePickerDialog.show()
    }

    companion object {
        const val NEW_CONSUMPTION_CODE: Int = 10
        const val CONSUMPTION_EXTRA_KEY: String = "CONSUMPTION_EXTRA_KEY"
    }
}