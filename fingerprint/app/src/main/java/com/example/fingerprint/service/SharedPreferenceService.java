package com.example.fingerprint.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceService {

    private static final String SHARED_PREFERENCE_KEY = "SHARED_PREFERENCE_KEY";

    private SharedPreferences sharedPreferences;

    public SharedPreferenceService(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0);
    }
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
