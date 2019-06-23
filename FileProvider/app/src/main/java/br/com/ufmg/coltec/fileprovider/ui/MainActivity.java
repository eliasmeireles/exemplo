package br.com.ufmg.coltec.fileprovider.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import br.com.ufmg.coltec.fileprovider.R;
import br.com.ufmg.coltec.fileprovider.file.FileSystemHelper;

import static br.com.ufmg.coltec.fileprovider.util.IntentUtil.intentFileSelect;

public class MainActivity extends AppCompatActivity {

    private Button selectFile;
    private int READ_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectFile = findViewById(R.id.select_pdf_file);
        selectFile();
    }

    public void selectFile() {
        selectFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intentFileSelect(MainActivity.this, READ_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    File file = null;
                    if (uri != null) {
                        file = FileSystemHelper.convertToFile(this, uri);
                    }
                    if (file != null) {
                        Log.i("File", file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
