package com.tech.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.Xml
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.tech.R
import org.jetbrains.annotations.NotNull
import org.xmlpull.v1.XmlPullParser


@SuppressLint("ViewConstructor")
open class CustomEditext(
    @NotNull context: Context,
    val maximumCharacter: Int = 0,
    val minimumCharacter: Int = 0,
    val hintText: String? = null,
    val emptyErrorMessage: String? = "This is a required field!",
    val errorMessage: String? = "This value is not valid!",
    val maxLines: Int = 1,
    isClickable: Boolean = true,
    action: () -> Unit? = {}

) : TextInputLayout(context, null, 0), ViewValidation {
    val textInputEditText = EditText(context)

    init {
        layoutSetup()
        textInputEditText.apply {
            if (!isClickable) {
                setOnClickListener {
                    action.invoke()
                }
                clearFocus()
                isFocusable = false
            }

            hint = hintText

            if (maximumCharacter > 0) {
                filters = arrayOf<InputFilter>(LengthFilter(maximumCharacter))
            }
        }
        addView(textInputEditText)

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                textInputEditText.error = null
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun layoutSetup() {
        val parser: XmlPullParser = resources.getXml(R.xml.default_child_view)

        try {
            parser.next()
            parser.nextTag()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val attributeSet = Xml.asAttributeSet(parser)
        layoutParams = LayoutParams(context, attributeSet)
        textInputEditText.maxLines = maxLines
    }

    fun setError() {
        textInputEditText.error = emptyErrorMessage
    }

    fun setError(errorMessage: String?) {
        textInputEditText.error = errorMessage
    }

    fun setText(text: Any) {
        textInputEditText.setText("$text")
    }

    fun getText(): String {
        return textInputEditText.text.toString().trim()
    }

    override fun isValid(): Boolean {
        if (minimumCharacter > 0 && getText().isEmpty()) {
            setError()
        } else if (getText().length < minimumCharacter) {
            setError(errorMessage)
        }

        return true
    }

    override fun getView(): View {
        return this
    }
}