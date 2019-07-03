package com.example.fingerprint.encrypt;

import android.os.Build;
import android.security.keystore.KeyProperties;

import androidx.annotation.RequiresApi;

public interface Constants {

    @RequiresApi(api = Build.VERSION_CODES.M)
    String ENCRYPTION_TRANSFORMATION_TYPE = KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_GCM + "/" + KeyProperties.ENCRYPTION_PADDING_NONE;
    String ANDROID_KEY_STORE = "AndroidKeyStore";
}

