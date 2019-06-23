package br.com.ufmg.coltec.fileprovider.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileSystemHelper {

    private FileSystemHelper() {
    }

    /**
     * Create a file from a Uri.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     */
    public static File convertToFile(@NonNull Context context, @NonNull Uri uri) throws IOException {

        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(uri);
        String selectedFileName = getSelectedFileName(contentResolver, uri);

        if (selectedFileName != null) {
            String selectedFileExtensio = selectedFileName.substring(selectedFileName.lastIndexOf("."));
            File file = createFile(context, selectedFileExtensio);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            if (inputStream != null) {
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                inputStream.close();
                outputStream.close();
            }

            Log.e("FILE", file.getName());
            return file;
        }
        throw new IOException("Could not complete the action with the selected file!");
    }

    private static String getSelectedFileName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        int nameIndex;
        if (returnCursor != null) {
            nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = returnCursor.getString(nameIndex);
            returnCursor.close();
            return name;
        }
        return null;
    }

    public static File createFile(Context context, String fileExtension) throws IOException {
        String nomeDoArquivo = String.valueOf(System.currentTimeMillis()).concat(fileExtension);
        File file = new File(context.getCacheDir().getPath(), nomeDoArquivo);

        if (!file.exists()) //noinspection ResultOfMethodCallIgnored
            file.createNewFile();

        return file;
    }

    public static File getTempFile(Context context, Uri uri) {
        File file = null;
        try {
            String fileName = uri.getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

