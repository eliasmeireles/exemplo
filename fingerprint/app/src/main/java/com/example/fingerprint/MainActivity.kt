package com.example.fingerprint

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.example.fingerprint.encrypt.KeyStoreDecrypt
import com.example.fingerprint.encrypt.KeyStoreEncrypt
import com.example.fingerprint.encrypt.KeyStoreReferenceKey
import com.example.fingerprint.service.fingerprint.AppFingerprintManager
import kotlinx.android.synthetic.main.activity_main.*
import javax.crypto.KeyGenerator


class MainActivity : AppCompatActivity() {

    private val KEY_STORE_ALIAS = "USER_FINGERPRINT"
    private lateinit var textViewUserEmail: String
    private lateinit var userPassword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        finger_print_user.apply {
            setOnClickListener {
                textViewUserEmail = user_email.text.toString().trim()
                userPassword = user_password.text.toString().trim()
                storeUseDataOnKeyStore()
            }
        }
        //ifApi28More()
        api23To27()
    }

    private fun api23To27() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppFingerprintManager(this,
                AppFingerprintManager.FingerPrintDelegate {
                    getUserDataFromKeyStore()
                }).authenticates()
        }
    }

    private fun storeUseDataOnKeyStore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyStoreEncrypt = KeyStoreEncrypt(this)
            Runnable {
                keyStoreEncrypt.encrypt(textViewUserEmail, KeyStoreReferenceKey.EMAIL_REFERENCE)
                keyStoreEncrypt.encrypt(userPassword, KeyStoreReferenceKey.PASSWORD_REFERENCE)
                val keyStoreDecrypt = KeyStoreDecrypt(this@MainActivity)
                val userEmailFromKeyStore =
                    keyStoreDecrypt.decrypt(KeyStoreReferenceKey.EMAIL_REFERENCE)
                val userPasswordFromKeyStore =
                    keyStoreDecrypt.decrypt(KeyStoreReferenceKey.PASSWORD_REFERENCE)
                println(userEmailFromKeyStore)
                println(userPasswordFromKeyStore)
            }.run()
        }
    }

    private fun ifApi28More() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestFingerPrint()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getUserDataFromKeyStore() {
        val keyStoreDecrypt = KeyStoreDecrypt(this@MainActivity)
        val userEmail = keyStoreDecrypt.decrypt(KeyStoreReferenceKey.EMAIL_REFERENCE)
        val userPassword = keyStoreDecrypt.decrypt(KeyStoreReferenceKey.PASSWORD_REFERENCE)
        user_email.setText(userEmail)
        user_password.setText(userPassword)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun requestFingerPrint() {
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

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getAuthenticationCallBack(): BiometricPrompt.AuthenticationCallback {
        return object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                getUserDataFromKeyStore()
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
