package com.example.readyedittext.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Xml
import android.view.View
import android.widget.LinearLayout
import com.example.readyedittext.R
import org.jetbrains.annotations.NotNull
import org.xmlpull.v1.XmlPullParser

@SuppressLint("ViewConstructor")
class HorizontalViewGroup(
    @NotNull context: Context,
    private val views: Array<CustomEditText>
) : LinearLayout(context), TextInput{

    init {
        try {
            val parser: XmlPullParser = resources.getXml(R.xml.horizontal_view)
            parser.next()
            parser.nextTag()
            val attributeSet = Xml.asAttributeSet(parser)
            layoutParams = LayoutParams(context, attributeSet)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        views.forEach {
            addView(it)
        }
    }
    override fun getClearText(): String {
        return ""
    }

    override fun isValid(): Boolean {
        var isValid = true

        views.forEach {
            isValid = it.isValid()
        }

        return isValid
    }

    override fun view(): View {
        return this
    }
}