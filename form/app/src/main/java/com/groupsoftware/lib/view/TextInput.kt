package com.groupsoftware.lib.view

import android.view.View

interface TextInput {

    fun getClearText(): String
    fun isValid(): Boolean
    fun view(): View
}