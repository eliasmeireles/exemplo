package com.exemplo.filedownload.service;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class FileDownload extends AsyncTask<String, Boolean, Boolean> {

    private FileDownloadDelegate delegate;
    private Map<String, String> files;

    public FileDownload(FileDownloadDelegate delegate, Map<String, String> files) {
        this.delegate = delegate;
        this.files = files;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.onDownloadStart();
        delegate.setDownloadProgress("0%");
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean successfulDownload = false;

        for (String key: files.keySet()) {
            successfulDownload = downloadFile(key, files.get(key));
        }

        return successfulDownload;
    }

    private Boolean downloadFile(String filePath, String fileUrl) {
        File file = new File(filePath);
        //noinspection ResultOfMethodCallIgnored
        file.delete();
        try {
            //noinspection ConstantConditions,ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            URL fileToDownloadUrl = new URL(fileUrl);
            URLConnection connection = fileToDownloadUrl.openConnection();
            connection.connect();
            int fileSize = connection.getContentLength();

            InputStream inputStream = new BufferedInputStream(fileToDownloadUrl.openStream(), 1024);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] bytes = new byte[1024];
            long totalDownloaded = 0;

            int progress;

            while ((progress = inputStream.read(bytes)) != -1) {
                totalDownloaded += progress;
                delegate.setDownloadProgress((int) ((totalDownloaded * 100) / fileSize) + "%");
                fileOutputStream.write(bytes, 0, progress);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.onDownloadFinish(result);
    }

    public interface FileDownloadDelegate {

        void onDownloadStart();
        void onDownloadFinish(boolean result);
        void setDownloadProgress(String progress);
    }
}
