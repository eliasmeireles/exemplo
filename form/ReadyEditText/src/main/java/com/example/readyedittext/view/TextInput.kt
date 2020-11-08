package com.example.readyedittext.view

import android.view.View

interface TextInput {

    fun getClearText(): String
    fun isValid(): Boolean
    fun view(): View
}