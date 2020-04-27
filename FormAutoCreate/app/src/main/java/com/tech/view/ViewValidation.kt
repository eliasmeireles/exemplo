package com.tech.view

import android.view.View

interface ViewValidation {
    fun isValid(): Boolean

    fun getView(): View
}