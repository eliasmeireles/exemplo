package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import com.groupsoftware.lib.R
import org.jetbrains.annotations.NotNull

@SuppressLint("ViewConstructor")
class PersonNameEditText(
    @NotNull context: Context,
    requiredErrorMessage: Int = R.string.required_field,
    validationErrorMessage: Int = R.string.invalid_field,
    textHint: Int,
    isRequiredField: Boolean = false,
    attrXml: Int = R.xml.editext_layout_attrs,
    editTextAttrXml: Int = R.xml.editext_attrs,
    clickable: Boolean = false,
    text: String?,
    onClick: () -> Unit = {}

) : CustomEditText(
    context = context,
    requiredErrorMessage = requiredErrorMessage,
    validationErrorMessage = validationErrorMessage,
    editTextAttrXml = editTextAttrXml,
    textHint = textHint,
    attrXml = attrXml,
    text = text,
    clickable = clickable,
    isRequiredField = isRequiredField,
    onClick = onClick
) {

    init {
        ediText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
    }
}