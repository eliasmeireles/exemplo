package com.example.scopedstorage;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scopedstorage.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private Uri cameraImageUri;

    private ActivityResultLauncher<String[]> selectAnImage = registerForActivityResult(new ActivityResultContracts.OpenDocument(), this::loadImageFromUri);

    private ActivityResultLauncher<Uri> newImageFromCamera = registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
        if (success) {
            loadImageFromUri(cameraImageUri);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.selectAnImage.setOnClickListener(v -> selectAnImage.launch(new String[]{"*/jpg"}));
        activityMainBinding.newImageFromCamera.setOnClickListener(v -> newImageFromCamera.launch(photoUriBuilder()));
    }


    private void loadImageFromUri(Uri imageUri) {
        if (imageUri == null) return;

        new Handler(getMainLooper()).post(() -> {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                activityMainBinding.imageViewTest.setImageBitmap(bitmap);
                File directory = getFilesDir();
                File test_files = new File(directory.getPath().concat(File.separator).concat("test_files"));

                if (!test_files.exists()) {
                    boolean mkdir = test_files.mkdirs();
                    System.out.println(mkdir);
                }
                FileOutputStream out = new FileOutputStream(test_files.getPath().
                        concat(File.separator)
                        .concat(String.valueOf(System.currentTimeMillis()).concat(".jpg")));
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Uri photoUriBuilder() {
        Uri imageCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, String.valueOf(System.currentTimeMillis()).concat(".jpg"));
        cameraImageUri = getContentResolver().insert(imageCollection, contentValues);
        return cameraImageUri;
    }
}