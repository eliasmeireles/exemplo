package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import com.groupsoftware.lib.R
import org.jetbrains.annotations.NotNull

@SuppressLint("ViewConstructor")
class TextArea(
    @NotNull context: Context,
    requiredErrorMessage: Int = R.string.required_field,
    validationErrorMessage: Int = R.string.invalid_field,
    minimumCharacter: Int = 0,
    maximumCharacter: Int = 0,
    textHint: Int? = null,
    isRequiredField: Boolean = false,
    attrXml: Int = R.xml.editext_layout_attrs,
    editTextAttrXml: Int = R.xml.editext_attrs,
    private val maxLines: Int? = null,
    clickable: Boolean = false,
    onClick: () -> Unit = {}

) : CustomEditText(
    context = context,
    requiredErrorMessage = requiredErrorMessage,
    validationErrorMessage = validationErrorMessage,
    editTextAttrXml = editTextAttrXml,
    textHint = textHint,
    attrXml = attrXml,
    clickable = clickable,
    maximumCharacter = maximumCharacter,
    minimumCharacter = minimumCharacter,
    isRequiredField = isRequiredField,
    onClick = onClick
) {

    init {
        ediText.inputType = InputType.TYPE_CLASS_TEXT or
                InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        maxLines?.let {
            ediText.maxLines = maxLines
        }
    }

    override fun isValid(): Boolean {
        return true
    }
}