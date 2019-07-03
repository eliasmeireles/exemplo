package com.example.fingerprint.service.biometric

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class BiometricCallBack(private val biometricCallback: BiometricCallBack?) : BiometricPrompt.AuthenticationCallback() {


    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        biometricCallback?.onAuthenticationSucceeded(result)
    }


    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        super.onAuthenticationHelp(helpCode, helpString)
        biometricCallback?.onAuthenticationHelp(helpCode, helpString)
    }


    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        biometricCallback?.onAuthenticationError(errorCode, errString)
    }


    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        biometricCallback?.onAuthenticationFailed()
    }
}