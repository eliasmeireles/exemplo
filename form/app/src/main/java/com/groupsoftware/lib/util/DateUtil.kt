package com.groupsoftware.lib.util

import android.app.DatePickerDialog
import android.content.Context
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class DateUtil {


    companion object {
        fun dateSelect(
            context: Context,
            dateTimeDelegate: DateTimeDelegate,
            date: DateTime? = null,
            dateTimeZone: DateTimeZone?
        ) {
            showCalendarDialog(
                context = context,
                dateTimeDelegate = dateTimeDelegate,
                date = date
            )
        }

        fun timeSelect(
            context: Context,
            dateTimeDelegate: DateTimeDelegate,
            date: DateTime? = null
        ) {
            showCalendarDialog(
                context = context,
                dateTimeDelegate = dateTimeDelegate,
                date = date
            )
        }

        fun showCalendarDialog(
            context: Context,
            dateTimeDelegate: DateTimeDelegate,
            date: DateTime?,
            timeZone: DateTimeZone? = null
        ) {
            val dateTime = when (date) {
                null -> DateTime.now()
                else -> date
            }
            val datePickerDialog = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    timeZone?.let {
                        dateTimeDelegate.onSelected(
                            selectedDateTime = DateTime(timeZone)
                                .withYear(year)
                                .withMonthOfYear(month)
                                .withDayOfMonth(dayOfMonth)
                        )
                    }.also {
                        dateTimeDelegate.onSelected(
                            selectedDateTime = DateTime()
                                .withYear(year)
                                .withMonthOfYear(month)
                                .withDayOfMonth(dayOfMonth)
                        )
                    }

                },
                dateTime.year,
                dateTime.monthOfYear,
                dateTime.dayOfMonth
            )

            datePickerDialog.apply {
                setOnDismissListener { dateTimeDelegate.onCancel() }
            }
            datePickerDialog.show()
        }
    }

    interface DateTimeDelegate {

        fun onSelected(selectedDateTime: DateTime)

        fun onCancel()
    }

    interface TimeDelegate {

        fun onSelected(hour: Int, second: Int)

        fun onCancel()
    }
}

fun isAfterNowMidNight(
    dateTime: DateTime,
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
): Boolean {
    val dateOfTheDay = DateTime(dateTimeZone)
        .withHourOfDay(23)
        .withMinuteOfHour(59)
        .withSecondOfMinute(59)

    return dateTime.isAfter(dateOfTheDay)
}

fun isAfterNow(
    dateTime: DateTime,
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
): Boolean {
    val dateOfTheDay = DateTime(dateTimeZone)
    return dateTime.isAfter(dateOfTheDay)
}

fun isNowNowStartHourOfTheDay(
    dateTime: DateTime,
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
): Boolean {
    val dateOfTheDay = DateTime(dateTimeZone)
        .withHourOfDay(0)
        .withMinuteOfHour(0)
        .withSecondOfMinute(0)

    return dateTime.isBefore(dateOfTheDay)
}

fun isBeforeNow(
    dateTime: DateTime,
    dateTimeZone: DateTimeZone = DateTimeZone.getDefault()
): Boolean {
    val dateOfTheDay = DateTime(dateTimeZone)
    return dateTime.isBefore(dateOfTheDay)
}