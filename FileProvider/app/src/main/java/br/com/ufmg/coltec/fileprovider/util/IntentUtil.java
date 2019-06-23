package br.com.ufmg.coltec.fileprovider.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import br.com.ufmg.coltec.fileprovider.ui.permission.Permissions;

public class IntentUtil {

    public static void intentFileSelect(Activity activity, int intentCode) {
        if (Permissions.checkPermissions(activity)) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            activity.startActivityForResult(intent, intentCode);
        }
    }
}
