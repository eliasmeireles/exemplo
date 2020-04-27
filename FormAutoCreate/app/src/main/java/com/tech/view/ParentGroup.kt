package com.tech.view

import android.widget.LinearLayout

class ParentGroup(
    val viewContainer: LinearLayout,
    val childViews: ArrayList<ViewValidation>
) {

    init {
        childViews.forEach {
            viewContainer.addView(it.getView())
        }
    }

    fun isValid(): Boolean {
        var hasErro = false
        childViews.forEach {
            hasErro = it.isValid()
        }

        return hasErro
    }
}