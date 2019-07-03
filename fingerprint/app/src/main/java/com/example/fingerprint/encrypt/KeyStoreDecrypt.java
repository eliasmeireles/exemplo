package com.example.fingerprint.encrypt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.example.fingerprint.service.SharedPreferenceService;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import static com.example.fingerprint.encrypt.Constants.ANDROID_KEY_STORE;
import static com.example.fingerprint.encrypt.Constants.ENCRYPTION_TRANSFORMATION_TYPE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class KeyStoreDecrypt {

    private final SharedPreferences sharedPreferences;

    public KeyStoreDecrypt(Context context) {
        this.sharedPreferences = new SharedPreferenceService(context).getSharedPreferences();
    }

    public String decrypt(KeyStoreReferenceKey referenceKey) {

        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);

            final Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION_TYPE);
            String decodedDataFromShared = sharedPreferences.getString(referenceKey.getSharedDecodedDataKey(), null);
            String cipherIVShared = sharedPreferences.getString(referenceKey.getSharedCipherIVKey(), null);

            if (decodedDataFromShared != null && cipherIVShared != null) {
                byte[] decodedDataBytes = Base64.decode(decodedDataFromShared, Base64.DEFAULT);
                byte[] cipherIV = Base64.decode(cipherIVShared, Base64.DEFAULT);

                final GCMParameterSpec spec = new GCMParameterSpec(128, cipherIV);

                cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyStore, referenceKey), spec);

                final byte[] decodedData = cipher.doFinal(decodedDataBytes);
                return new String(decodedData, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private SecretKey getSecretKey(KeyStore keyStore, KeyStoreReferenceKey referenceKey) throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException {
        return ((KeyStore.SecretKeyEntry) keyStore.getEntry(referenceKey.getAlias(), null)).getSecretKey();
    }
}
