package br.com.tech.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.tech.R
import br.com.tech.ui.viewmodel.UserViewModel
import javax.inject.Inject

class CreateUserActivity : AppCompatActivity() {

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)


    }
}
