package com.example.fingerprint.service;

import android.security.keystore.KeyProperties;

import static org.junit.Assert.*;

public class KeyStoreServiceTest {
    public static void main(String[] args) {
        StringBuilder encryptionTransformationType = new StringBuilder()
                .append(KeyProperties.KEY_ALGORITHM_AES)
                .append("/")
                .append(KeyProperties.BLOCK_MODE_GCM)
                .append("/")
                .append(KeyProperties.ENCRYPTION_PADDING_NONE);

        System.out.println(encryptionTransformationType.toString());
    }
}