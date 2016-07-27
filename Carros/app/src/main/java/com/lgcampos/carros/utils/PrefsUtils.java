package com.lgcampos.carros.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class PrefsUtils {
    public static boolean isCheckPushOn(final Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("PREF_CHECK_PUSH", false);
    }
}
