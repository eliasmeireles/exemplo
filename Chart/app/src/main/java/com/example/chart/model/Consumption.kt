package com.example.chart.model

import java.io.Serializable
import java.util.*

class Consumption(
    val date: Date,
    val value: Double,
    val name: String,
    val description: String
) : Serializable