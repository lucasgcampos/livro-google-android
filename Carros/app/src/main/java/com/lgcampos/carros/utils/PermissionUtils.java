package com.lgcampos.carros.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class PermissionUtils {

    public static boolean validate(Activity activity, int requestCode, String... permissions) {
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                list.add(permission);
            }
        }

        if (list.isEmpty()) {
            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        ActivityCompat.requestPermissions(activity, newPermissions, 1);

        return false;
    }

    private static boolean hasPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

}
