package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import com.groupsoftware.lib.R
import org.jetbrains.annotations.NotNull

@SuppressLint("ViewConstructor")
class NumberEditText(
    @NotNull context: Context,
    validationErrorMessage: Int = R.string.invalid_field,
    decimal: Boolean = false,
    requiredErrorMessage: Int = R.string.required_field,
    textHint: Int,
    isRequiredField: Boolean = false,
    text: Int? = null,
    maximumCharacter: Int = 0,
    minimumCharacter: Int = 0

) : CustomEditText(
    context = context,
    textHint = textHint,
    text = text?.toString(),
    isRequiredField = isRequiredField,
    minimumCharacter = minimumCharacter,
    maximumCharacter = maximumCharacter,
    requiredErrorMessage = requiredErrorMessage,
    validationErrorMessage = validationErrorMessage
) {

    init {
        ediText.inputType = when (decimal) {
            false -> InputType.TYPE_CLASS_NUMBER
            else -> InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    override fun isValid(): Boolean {
        if (isRequiredField && isEmpty()) {
            setErrorMessage(requiredErrorMessage)
            return false
        } else if (getClearText().length < minimumCharacter) {
            setErrorMessage(validationErrorMessage)
            return false
        }
        return true
    }

    fun getValue(): Int? {
        try {
            getClearText().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}