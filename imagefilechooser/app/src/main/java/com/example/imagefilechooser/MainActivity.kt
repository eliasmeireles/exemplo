package com.example.imagefilechooser

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.imagefilechooser.permission.REQUST_IMAGE_CODE
import com.example.imagefilechooser.permission.startImageSelectIntent
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {

    private var outputFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_view.setOnClickListener {
            outputFileUri  = startImageSelectIntent(this@MainActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUST_IMAGE_CODE) {
            if (data != null && data.data != null) {
                val selectedImageUri = data.data

                if (selectedImageUri != null) {
                    Log.d("ImageURI", selectedImageUri.lastPathSegment!!)

                    val options = BitmapFactory.Options()

                    options.inSampleSize = 8
                    try {
                        val input = contentResolver.openInputStream(selectedImageUri)
                        val bitmap = BitmapFactory.decodeStream(input)
                        image_view.setImageBitmap(bitmap)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }

            } else {
                val options = BitmapFactory.Options()
                options.inSampleSize = 8
                val bitmap = BitmapFactory.decodeFile(outputFileUri?.path, options)
                image_view.setImageBitmap(bitmap)
            }
        }
    }

}
