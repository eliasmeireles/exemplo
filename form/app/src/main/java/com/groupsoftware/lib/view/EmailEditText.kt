package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import com.groupsoftware.lib.R
import org.jetbrains.annotations.NotNull
import java.util.regex.Pattern

@SuppressLint("ViewConstructor")
class EmailEditText(
    @NotNull context: Context,
    private val patter: String = "[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*",
    validationErrorMessage: Int = R.string.email_invalid,
    requiredErrorMessage: Int = R.string.required_field,
    textHint: Int = R.string.email,
    isRequiredField: Boolean = false,
    attrXml: Int = R.xml.editext_layout_attrs,
    editTextAttrXml: Int = R.xml.editext_attrs,
    clickable: Boolean = false,
    onClick: () -> Unit = {}

) : CustomEditText(
    context = context,
    requiredErrorMessage = requiredErrorMessage,
    editTextAttrXml = editTextAttrXml,
    textHint = textHint,
    attrXml = attrXml,
    clickable = clickable,
    isRequiredField = isRequiredField,
    validationErrorMessage = validationErrorMessage,
    onClick = onClick
) {

    init {
        ediText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    override fun isValid(): Boolean {
        val email = getClearText()
        val matches = Pattern.matches(
            patter,
            email
        )

        if (isRequiredField && isEmpty()) {
            setErrorMessage(requiredErrorMessage)
            return false
        }

        if (!isEmpty() && !matches) {
            setErrorMessage(validationErrorMessage)
            return false
        }

        return true
    }
}