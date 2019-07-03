package com.example.fingerprint.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.fingerprint.service.SharedPreferenceService;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.example.fingerprint.encrypt.Constants.ANDROID_KEY_STORE;
import static com.example.fingerprint.encrypt.Constants.ENCRYPTION_TRANSFORMATION_TYPE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class KeyStoreEncrypt {

    private final SharedPreferences sharedPreferences;

    public KeyStoreEncrypt(Context context) {
        this.sharedPreferences = new SharedPreferenceService(context).getSharedPreferences();
    }

    public boolean encrypt(String value, KeyStoreReferenceKey referenceKey) {

        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(referenceKey));

            byte[] decodedData = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

            return storeDecodedData(referenceKey, decodedData, cipher.getIV());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @NonNull
    private SecretKey getSecretKey(KeyStoreReferenceKey referenceKey) throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException {

        final KeyGenerator keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

        keyGenerator.init(new KeyGenParameterSpec.Builder(referenceKey.getAlias(),
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build());

        return keyGenerator.generateKey();
    }

    private boolean storeDecodedData(KeyStoreReferenceKey referenceKey, byte[] decodedData, byte[] cipherIV) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(referenceKey.getSharedDecodedDataKey(), Base64.encodeToString(decodedData, Base64.DEFAULT));
            editor.putString(referenceKey.getSharedCipherIVKey(), Base64.encodeToString(cipherIV, Base64.DEFAULT));
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
