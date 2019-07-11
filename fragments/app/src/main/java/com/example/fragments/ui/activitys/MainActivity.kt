package com.example.fragments.ui.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragments.R
import com.example.fragments.ui.fragments.FragmentListProducts

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentListProducts = FragmentListProducts.instance("Testing fragments!")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout_container, fragmentListProducts)
                .commit()
    }
}
