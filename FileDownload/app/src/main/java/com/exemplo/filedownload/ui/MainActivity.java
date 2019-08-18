package com.exemplo.filedownload.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.filedownload.R;
import com.exemplo.filedownload.service.FileDownload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FileDownload.FileDownloadDelegate {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDialog();
        Map<String, String> filesToDownload = new HashMap<>();
        filesToDownload.put(getDatabasePath("file.txt").getPath(), "http://187.108.112.23/testebw/file2.test");
        filesToDownload.put(getDatabasePath("file2.txt").getPath(), "http://187.108.112.23/testebw/file1.test");

        new FileDownload(this, filesToDownload).execute();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.download_progress_bar_layout, null, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
    }

    @Override
    public void onDownloadStart() {
        dialog.show();
    }

    @Override
    public void onDownloadFinish(boolean value) {
        dialog.dismiss();
        if (value) {
            Toast.makeText(this, "Download was successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Could not download the file!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setDownloadProgress(final String progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView dialogProgressTextView = dialog.findViewById(R.id.textProgress);
                dialogProgressTextView.setText(getString(R.string.download_progress, progress));
            }
        });
    }
}
