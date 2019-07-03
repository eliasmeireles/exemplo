package com.example.fingerprint.encrypt;

public class KeyStoreReferenceKeyTest {

    public static void main(String[] args) {
        System.out.println(KeyStoreReferenceKey.EMAIL_REFERENCE.getAlias());
        System.out.println(KeyStoreReferenceKey.EMAIL_REFERENCE.getSharedDecodedDataKey());
        System.out.println(KeyStoreReferenceKey.EMAIL_REFERENCE.getSharedCipherIVKey());
    }
}