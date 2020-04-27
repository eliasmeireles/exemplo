package com.tech.view

import android.content.Context
import org.jetbrains.annotations.NotNull
import java.util.regex.Pattern


class EmailEditext(
    @NotNull context: Context,
    errorMessage: String? = "This is not a valid e-mail!",
    maximumCharacter: Int = 0,
    minimumCharacter: Int = 0,
    hintText: String? = "E-mail",
    emptyErrorMessage: String? = "This is a required field!",
    isClickable: Boolean = true,
    action: () -> Unit? = {}

) : CustomEditext(
    context = context,
    maximumCharacter = maximumCharacter,
    minimumCharacter = minimumCharacter,
    hintText = hintText,
    isClickable = isClickable,
    emptyErrorMessage = emptyErrorMessage,
    action = action
) {
    override fun isValid(): Boolean {
        val email = getText()
        val matches = Pattern.matches(
            "[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*",
            email
        )

        if (email.isEmpty()) {
            setError()
            return false
        } else if (!matches) {
            setError(errorMessage)
            return false
        }

        return true
    }
}