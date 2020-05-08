package com.groupsoftware.lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import com.groupsoftware.lib.R
import com.groupsoftware.lib.util.CnpjValidation.Companion.isACNPJValid
import com.groupsoftware.lib.util.CpfValidation.Companion.isAValidCPF
import org.jetbrains.annotations.NotNull

@SuppressLint("ViewConstructor")
class CpfCnpjEditText(
    @NotNull context: Context,
    validationErrorMessage: Int = R.string.invalid_field,
    requiredErrorMessage: Int = R.string.required_field,
    textHint: Int,
    isRequiredField: Boolean = false,
    text: String? = null
) : CustomEditText(
    context = context,
    textHint = textHint,
    text = text,
    isRequiredField = isRequiredField,
    requiredErrorMessage = requiredErrorMessage,
    validationErrorMessage = validationErrorMessage,
    maximumCharacter = 18
) {

    private companion object {
        const val CPF_MASK = "###.###.###-##"
        const val CNPJ_MASK = "##.###.###/####-##"
    }

    init {
        ediText.inputType = InputType.TYPE_CLASS_NUMBER
        textWatcherApply()
    }

    private fun textWatcherApply() {

        ediText.setTextWatcher(object : TextWatcher {
            var maskedValue = ""
            override fun afterTextChanged(sequence: Editable?) {
                sequence?.let {

                    val value = it.toString()
                        .replace(".", "", true)
                        .replace("/", "", true)
                        .replace("-", "", true)

                    var mask = CPF_MASK
                    if (value.length > CPF_MASK.length - 3) {
                        mask = CNPJ_MASK
                    }

                    if (value.length == CPF_MASK.length - 3 || value.length == CNPJ_MASK.length - 4) {
                        for (char in value) {
                            mask = mask.replaceFirst("#", char.toString(), true)
                        }
                        if (maskedValue != mask) {
                            maskedValue = mask
                            ediText.setText(mask)
                        }

                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    fun getClearValues(): String {
        return getClearText()
            .replace(".", "", true)
            .replace("/", "", true)
            .replace("-", "", true)
    }

    override fun isValid(): Boolean {
        if (isRequiredField && isEmpty()) {
            setErrorMessage(requiredErrorMessage)
            return false
        }

        val textSize = getClearText().length
        if (textSize <= CPF_MASK.length) {
            if (!isAValidCPF(getClearValues())) {
                setErrorMessage(R.string.cpf_not_valid)
                return false
            }
        } else {
            if (!isACNPJValid(getClearValues())) {
                setErrorMessage(R.string.cpng_not_valid)
                return false
            }
        }

        return true
    }
}