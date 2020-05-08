package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Xml
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.groupsoftware.lib.R
import org.jetbrains.annotations.NotNull
import org.xmlpull.v1.XmlPullParser

@SuppressLint("ViewConstructor")
open class CustomEditText(
    @NotNull context: Context,
    val requiredErrorMessage: Int = R.string.required_field,
    val validationErrorMessage: Int = R.string.invalid_field,
    val textHint: Int? = null,
    val text: String? = null,
    var isRequiredField: Boolean = false,
    val maximumCharacter: Int = 0,
    val minimumCharacter: Int = 0,
    attrXml: Int = R.xml.editext_layout_attrs,
    editTextAttrXml: Int = R.xml.editext_attrs,
    clickable: Boolean = false,
    inputType: Int = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES,
    private val onClick: () -> Unit = {}

) : TextInputLayout(context), TextInput {

    var isClicked = false

    protected val ediText: EdiText by lazy {
        EdiText(
            attrXml = editTextAttrXml,
            context = context,
            maximumCharacter = maximumCharacter,
            textHint = textHint,
            attachView = ::setContentView
        )
    }


    init {

        try {
            val parser: XmlPullParser = resources.getXml(attrXml)
            parser.next()
            parser.nextTag()
            val attributeSet = Xml.asAttributeSet(parser)
            layoutParams = LayoutParams(context, attributeSet)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (clickable) {
            ediText.disable()
            ediText.setOnClickListener {
                if (!isClicked) {
                    isClicked = true
                    onTextInputClick()
                }
            }
        }

        ediText.inputType = inputType
        ediText.setText(text)

    }

    private fun setContentView(inputText: EditText) {
        addView(inputText)
    }

    open fun onTextInputClick() {
        onClick.invoke()
    }

    fun isEmpty(): Boolean {
        return getClearText().isEmpty()
    }

    override fun getClearText(): String {
        return ediText.text.toString().trim()
    }

    fun setText(value: Any?) {
        if (value != null) {
            ediText.setText("$value")
        } else {
            ediText.text = null
        }
    }

    override fun isValid(): Boolean {
        minimumCharacter.let {
            if (it > 0 && getClearText().length < it) {
                requiredErrorMessage.let { errorMessage ->
                    ediText.error = context.getString(errorMessage)
                }
                return false
            }
        }

        if (isRequiredField && getClearText().isEmpty()) {
            return false
        }

        return true
    }

    fun setErrorMessage(errorMessage: String?) {
        ediText.error = errorMessage
    }

    fun setErrorMessage(errorMessageId: Int) {
        ediText.error = resources.getString(errorMessageId)
    }

    override fun view(): View {
        return this
    }

    @SuppressLint("ViewConstructor")
    class EdiText(
        context: Context,
        var textHint: Int? = null,
        var maximumCharacter: Int = 0,
        attrXml: Int,
        attachView: (EdiText) -> Unit
    ) : AppCompatEditText(context) {

        init {
            try {
                val parser: XmlPullParser = resources.getXml(attrXml)
                parser.next()
                parser.nextTag()
                val attributeSet = Xml.asAttributeSet(parser)
                layoutParams = LayoutParams(context, attributeSet)
            } catch (e: Exception) {
                e.printStackTrace()
            }


            textHint?.let {
                hint = context.getString(it)
            }

            maximumCharacter.let {
                if (it > 0) {
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(it))
                }
            }

            attachView.invoke(this)
        }

        fun setText(text: String?) {
            super.setText(text)
            setSelection(super.getText().toString().length)
        }

        fun disable() {
            isFocusable = false
            isClickable = true
        }

        fun setTextWatcher(textWatcher: TextWatcher) {
            addTextChangedListener(textWatcher)
        }
    }
}