package com.example.fingerprint.encrypt;

import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public enum KeyStoreReferenceKey {

    PASSWORD_REFERENCE("PASSWORD_KEY_STORE_ALIAS", "SHARED_PASSWORD_KEY", "SHARED_CIPHER_IV_PASSWORD_KEY"),
    EMAIL_REFERENCE("EMAIL_KEY_STORE_ALIAS", "SHARED_EMAILS_KEY", "SHARED_CIPHER_IV_EMAILS_KEY");

    private String alias;
    private String sharedDecodedDataKey;
    private String sharedCipherIVKey;

    KeyStoreReferenceKey(String alias, String sharedDecodedDataKey, String sharedCipherIVKey) {
        this.alias = alias;
        this.sharedDecodedDataKey = sharedDecodedDataKey;
        this.sharedCipherIVKey = sharedCipherIVKey;
    }

    public String getAlias() {
        return alias;
    }

    public String getSharedDecodedDataKey() {
        return sharedDecodedDataKey;
    }

    public String getSharedCipherIVKey() {
        return sharedCipherIVKey;
    }
}
