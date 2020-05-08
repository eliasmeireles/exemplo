package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import com.groupsoftware.lib.R
import com.groupsoftware.lib.util.DateUtil
import org.jetbrains.annotations.NotNull
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

@SuppressLint("ViewConstructor")
class DateSelectEditText(
    @NotNull context: Context,
    validationErrorMessage: Int = R.string.invalid_field,
    private val dateFormatPatter: Int = R.string.date_format,
    requiredErrorMessage: Int = R.string.required_field,
    textHint: Int = R.string.date,
    isRequiredField: Boolean = false,
    attrXml: Int = R.xml.editext_layout_attrs,
    editTextAttrXml: Int = R.xml.editext_attrs,
    var date: DateTime? = null,
    var dateTimeZone: DateTimeZone? = DateTimeZone.getDefault(),
    private val dateValidation: (dateTime: DateTime) -> Boolean = { true }
) : CustomEditText(
    context = context,
    requiredErrorMessage = requiredErrorMessage,
    editTextAttrXml = editTextAttrXml,
    textHint = textHint,
    attrXml = attrXml,
    clickable = true,
    isRequiredField = isRequiredField
), DateUtil.DateTimeDelegate {


    override fun onTextInputClick() {
        DateUtil.dateSelect(
            context = context,
            dateTimeDelegate = this,
            date = date,
            dateTimeZone = dateTimeZone
        )
    }

    override fun isValid(): Boolean {
        if (isRequiredField && date == null) {
            setErrorMessage(requiredErrorMessage)
            return false
        }
        date?.let {
            if (!dateValidation.invoke(it)) {
                setErrorMessage(validationErrorMessage)
                return false
            }
        }
        return true
    }

    override fun onSelected(selectedDateTime: DateTime) {
        this.date = null
        setText(null)
        isClicked = false

        if (dateValidation.invoke(selectedDateTime)) {
            ediText.setText(
                selectedDateTime.toString(
                    DateTimeFormat.forPattern(
                        context.getString(
                            dateFormatPatter
                        )
                    )
                )
            )
            this.date = selectedDateTime
        }
    }

    override fun onCancel() {
        isClicked = false
    }
}