package com.example.readyedittext.view

import android.widget.LinearLayout


class ViewGroup(
    private val viewContainer: LinearLayout,
    private val views: List<TextInput>
) {
    init {
        views.forEach { view ->
            viewContainer.addView(view.view())
        }
    }

    fun isValid(): Boolean {
        var isValid = false

        views.forEach {
            isValid = it.isValid()
        }

        return isValid
    }
}