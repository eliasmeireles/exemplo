package com.example.fingerprint

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.security.keystore.KeyGenParameterSpec
import android.view.View
import javax.crypto.KeyGenerator


class MainActivity : AppCompatActivity() {

    private val KEY_STORE_ALIAS = "USER_FINGERPRINT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            finger_print_user.apply {
                setOnClickListener { requestFingerPrint() }
            }
        } else finger_print_user.visibility = View.GONE

    }

    private fun requestFingerPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                val keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    KEY_STORE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()

            } catch (exception: Exception) {
                exception.printStackTrace()
            }


            val build = BiometricPrompt
                .Builder(this)
                .setTitle("Fingerprint")
                .setSubtitle("Place you fingerprint into the sensor!")
                .setNegativeButton("Cancel", mainExecutor,
                    DialogInterface.OnClickListener { _, _ ->
                        Toast.makeText(this, "Operation canceled!", Toast.LENGTH_LONG).show()
                    })
                .setDescription("Uses your fingerprint to authenticates!")
                .build()

            build.authenticate(getCancellation(), mainExecutor, getAuthenticationCallBack())
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getAuthenticationCallBack(): BiometricPrompt.AuthenticationCallback {
        return object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "Logged in!", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@MainActivity, "Try again!", Toast.LENGTH_LONG).show()
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun getCancellation(): CancellationSignal {
        return CancellationSignal()
    }
}
