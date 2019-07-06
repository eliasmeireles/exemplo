package com.example.chart.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatAsString(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(this)
}