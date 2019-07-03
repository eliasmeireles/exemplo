package com.example.fingerprint.service.fingerprint;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.fingerprint.R;
import com.example.fingerprint.encrypt.KeyStoreReferenceKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.example.fingerprint.encrypt.Constants.ANDROID_KEY_STORE;
import static com.example.fingerprint.encrypt.Constants.ENCRYPTION_TRANSFORMATION_TYPE;


@SuppressWarnings("deprecation")
@RequiresApi(api = Build.VERSION_CODES.M)
public class AppFingerprintManager {

    private FingerprintManager fingerprintManager;
    private FingerPrintDelegate delegate;
    private Context context;
    private AlertDialog fingerprinteDialog;
    private CancellationSignal cancel;


    public AppFingerprintManager(Context context, FingerPrintDelegate delegate) {
        this.context = context;
        fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        this.delegate = delegate;
    }


    public void authenticates() {
        if (isHardwareDetected() && hasEnrolledFingerprint() && isPermissionGranted() && isFingerprintAvailable()) {
            try {
                cancel = new CancellationSignal();
                fingerprintManager.authenticate(new FingerprintManager.CryptoObject(getCipher()), cancel, 0, getAuthenticatesCallBack(), null);
                alertDialogBuild();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void alertDialogBuild() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View fingerprintLayout = LayoutInflater.from(context).inflate(R.layout.fingerprint_dialog, null);
        builder.setView(fingerprintLayout);
        builder.setCancelable(false);
        fingerprinteDialog = builder.create();
        fingerprinteDialog.show();

        Button buttonCancel = fingerprintLayout.findViewById(R.id.authenticates_fingerprint_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerprinteDialog.dismiss();
                cancel.cancel();
            }
        });
    }

    private FingerprintManager.AuthenticationCallback getAuthenticatesCallBack() {
        return new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                fingerprinteDialog.dismiss();
                delegate.success();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
    }

    private Cipher getCipher() {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @NonNull
    private SecretKey getSecretKey() throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidAlgorithmParameterException {

        final KeyGenerator keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

        keyGenerator.init(new KeyGenParameterSpec.Builder("fingerprint",
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build());

        return keyGenerator.generateKey();
    }

    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private  boolean isFingerprintAvailable() {
        return fingerprintManager.hasEnrolledFingerprints();
    }

    private boolean isHardwareDetected() {
        return fingerprintManager.isHardwareDetected();
    }

    private boolean hasEnrolledFingerprint() {
        return fingerprintManager.hasEnrolledFingerprints();
    }

    public interface FingerPrintDelegate {
        void success();
    }
}
