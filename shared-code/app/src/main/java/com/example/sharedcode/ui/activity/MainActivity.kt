package com.example.sharedcode.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedcode.R
import com.example.sharedcode.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
