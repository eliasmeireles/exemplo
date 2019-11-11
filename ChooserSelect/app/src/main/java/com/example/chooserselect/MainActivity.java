package com.example.chooserselect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission(Manifest.permission.CAMERA) && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startIntentImageChooser();
                }
            }
        });
    }

    private boolean checkPermission(String permission) {
        boolean permissionGrated = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!permissionGrated) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }

        return permissionGrated;
    }

    private void startIntentImageChooser() {
        String imageName = UUID.randomUUID().toString();
        File root = getExternalCacheDir();
        File imageFile = new File(root, imageName.concat(".jpg"));
        imageUri = Uri.fromFile(imageFile);

        ArrayList<Intent> intents = new ArrayList<>();
        Intent intentPicker = new Intent(Intent.ACTION_PICK);
        intentPicker.setType("image/*");

        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(imageCaptureIntent, 0);
        for (ResolveInfo info :
                resolveInfos) {

            Intent intent = new Intent(imageCaptureIntent);
            intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
            intent.setPackage(getPackageName());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intents.add(intent);
        }

        Intent chooserIntent = Intent.createChooser(intentPicker, "Choose how to continue");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, 255);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 255) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri.getLastPathSegment() != null) {
                    Log.d("ImageURI", selectedImageUri.getLastPathSegment());

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inSampleSize = 8;
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            } else if (imageUri != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
