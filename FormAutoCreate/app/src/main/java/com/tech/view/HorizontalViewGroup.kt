package com.tech.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Xml
import android.view.View
import android.widget.LinearLayout
import com.tech.R
import org.jetbrains.annotations.NotNull
import org.xmlpull.v1.XmlPullParser


@SuppressLint("ViewConstructor")
class HorizontalViewGroup(
    @NotNull context: Context,
    val views: Array<CustomEditext>

) : LinearLayout(context), ViewValidation {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL

        val parser: XmlPullParser = resources.getXml(R.xml.horizontal_child_view)

        try {
            parser.next()
            parser.nextTag()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val attributeSet = Xml.asAttributeSet(parser)

        views.forEach {
            it.layoutParams = LayoutParams(context, attributeSet)
            this@HorizontalViewGroup.addView(it)
        }
    }

    override fun isValid(): Boolean {
        var hasError = false
        views.forEach {
            hasError = it.isValid()
        }

        return hasError
    }

    override fun getView(): View {
        return this
    }
}