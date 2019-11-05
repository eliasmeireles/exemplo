package com.example.imagefilechooser.permission

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File


const val CAMERA_ACCESS_CODE = 10
const val READ_WRITE_ACCESS_CODE = 11
const val REQUEST_IMAGE_CODE = 12

fun startImageSelectIntent(activity: Activity): Uri? {

    if (!accessCameraPermissionGranted(activity = activity)) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_ACCESS_CODE
        )
    } else if (!accessStoragePermissionGranted(activity = activity)) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            READ_WRITE_ACCESS_CODE
        )
    } else {
        return openImageIntent(activity = activity)
    }
    return null
}

private fun openImageIntent(activity: Activity): Uri? {

    // Determine Uri of camera image to save.
    val root =
        File("${activity.getExternalFilesDir("cache")}")
    root.mkdir()
    val fname = "img_" + System.currentTimeMillis() + ".jpg"
    val sdImageMainDirectory = File(root, fname)
    val outputFileUri = Uri.fromFile(sdImageMainDirectory)

    // Camera.
    val cameraIntents = ArrayList<Intent>()
    val imagePickerIntent = Intent(Intent.ACTION_PICK)
    imagePickerIntent.type = "image/*"
    cameraIntents.add(imagePickerIntent)
    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val packageManager = activity.packageManager
    val listCam = packageManager.queryIntentActivities(captureIntent, 0)
    for (res in listCam) {
        val packageName = res.activityInfo.packageName
        val intent = Intent(captureIntent)
        intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
        intent.setPackage(packageName)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        cameraIntents.add(intent)
    }

    //FileSystem
    val galleryIntent = Intent()
    galleryIntent.type = "image/"
    galleryIntent.action = Intent.ACTION_GET_CONTENT

    // Chooser of filesystem options.
    val chooserIntent = Intent.createChooser(galleryIntent, "Select Source")
    // Add the camera options.
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toTypedArray<Parcelable>())
    activity.startActivityForResult(chooserIntent, REQUEST_IMAGE_CODE)
    return outputFileUri
}

fun accessCameraPermissionGranted(activity: Activity): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

fun accessStoragePermissionGranted(activity: Activity): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}