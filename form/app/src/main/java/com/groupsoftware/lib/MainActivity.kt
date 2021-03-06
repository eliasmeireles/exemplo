package com.groupsoftware.lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.readyedittext.util.isBeforeNow
import com.example.readyedittext.view.*
import kotlinx.android.synthetic.main.activity_main.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime

class MainActivity : AppCompatActivity() {

    private lateinit var email: EmailEditText
    private lateinit var initialDateSelectEditText: DateSelectEditText
    private lateinit var endDateSelectEditText: DateSelectEditText
    private lateinit var cpfCnpjInputText: CpfCnpjEditText
    private lateinit var textArea: TextArea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JodaTimeAndroid.init(this)

        initialDateSelectEditText = DateSelectEditText(
            context = this,
            dateValidation = ::initialDateValidation,
            isRequiredField = true
        )
        endDateSelectEditText = DateSelectEditText(
            context = this,
            isRequiredField = true
        )

        email = EmailEditText(
            context = this,
            isRequiredField = true
        )

        cpfCnpjInputText = CpfCnpjEditText(
            context = this,
            textHint = R.string.cpf_cnpj,
            isRequiredField = true
        )

        textArea = TextArea(context = this)
        val viewGroup = ViewGroup(
            viewContainer = form_container,
            views = arrayListOf(
                email,
                cpfCnpjInputText,
                textArea
            )
        )

        form_button.apply {
            setOnClickListener {
                viewGroup.isValid()
                println(cpfCnpjInputText.getClearValues())
                println(cpfCnpjInputText.getClearText())
            }
        }
    }

    private fun initialDateValidation(dateTime: DateTime): Boolean {
        if (isBeforeNow(dateTime = dateTime)) {
            return true
        }
        return false
    }
}
