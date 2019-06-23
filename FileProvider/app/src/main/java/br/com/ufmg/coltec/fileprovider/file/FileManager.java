package br.com.ufmg.coltec.fileprovider.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe responsável para criação de uma imagem,
 * */
public class FileManager {

    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    /*
     * @Todo arquivo criado será do tipo imagem com extensão jpg.
     * Método que cria uma arquivo do tipo imagem
     * onde esse imagem será temporariamente salva no diretório de cash
     * retornando essa imagem como um arquivo.
     * */
    public File createCacheFileFromUri(String fileName, boolean manterNomeDeArquivo, Uri imageUri) throws IOException {
        fileName = fileName.replaceAll("[@\\s]", "_");
        String nomeDoArquivo = !manterNomeDeArquivo ? fileName.concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".pdf")
                : fileName;
        File cashImage = new File(context.getCacheDir().getPath(), nomeDoArquivo);
        FileOutputStream outputStream = new FileOutputStream(cashImage);
        ParcelFileDescriptor descriptor = context.getContentResolver().openFileDescriptor(imageUri, "r");

        if (descriptor != null) {
            FileDescriptor fileDescriptor = descriptor.getFileDescriptor();
            FileWriter fileWriter = new FileWriter(fileDescriptor);
            fileWriter.flush();
        }
        outputStream.flush();
        outputStream.close();
        return cashImage;
    }

    /**
     * @Todo os arquivos retornados serão do tipo imagem com extensão jpg.
     * @Método reponsável a criar e retornar um {@link List<File>},
     * é preciso ter cuidado ao usar esse método, pois ele pode
     * lançar exeções do tipo {@link IOException} e {@link NullPointerException}
     * o parâmentro do tipo {@link String #fileName} é ultilizado para
     * o nome incial do arquivo.
     */
    public List<File> createFilesFromUrisList(List<Uri> uriList) throws IOException, NullPointerException {
        if (uriList == null) {
            throw new NullPointerException("The uri list can't be null!");
        }
        List<File> files = new ArrayList<>();
        for (Uri uri : uriList) {
            String nomeDaImagem = String.valueOf(System.currentTimeMillis()).concat(".jpg");
            File imageOnCashDir = createCacheFileFromUri(nomeDaImagem, true, uri);
            files.add(imageOnCashDir);
        }
        return files;
    }
}
