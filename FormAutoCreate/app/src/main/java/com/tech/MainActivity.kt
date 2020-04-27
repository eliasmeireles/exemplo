package com.tech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tech.model.User
import com.tech.view.*
import kotlinx.android.synthetic.main.activity_main.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class MainActivity : AppCompatActivity() {

    private lateinit var name: CustomEditext
    private lateinit var middleName: CustomEditext
    private lateinit var email: EmailEditext
    private lateinit var birthday: CustomEditext
    private lateinit var phoneNumber: CustomEditext
    private lateinit var parentView: ParentGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main)
        viewInitizlizer()

        form_button.setOnClickListener {

            if (parentView.isValid()) {
                val user = User(
                    name = name.getText(),
                    middleName = middleName.getText(),
                    email = email.getText(),
                    phoneNumber = phoneNumber.getText(),
                    birthday = birthday.getText()
                )

                println(user.toString())
            }
        }
    }

    private fun viewInitizlizer() {
        name = CustomEditext(context = this@MainActivity, hintText = "Name")
        middleName = CustomEditext(context = this@MainActivity, hintText = "Last Name")
        email = EmailEditext(context = this@MainActivity)
        phoneNumber = CustomEditext(context = this@MainActivity, hintText = "Phone Number")

        birthday = CustomEditext(
            context = this@MainActivity,
            hintText = "Birthday",
            isClickable = false,
            action = {
                val dateTime = DateTime.now()
                val forPattern = DateTimeFormat.forPattern("dd/MM/yyyy")
                birthday.setText(forPattern.print(dateTime))
            }
        )

        val childView: ArrayList<ViewValidation> = arrayListOf(
            name,
            middleName,
            email,
            HorizontalViewGroup(this@MainActivity, arrayOf(phoneNumber, birthday))
        )

        parentView = ParentGroup(viewContainer = form_container, childViews = childView)
    }
}
