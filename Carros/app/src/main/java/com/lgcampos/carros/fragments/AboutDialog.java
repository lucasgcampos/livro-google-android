package com.lgcampos.carros.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lgcampos.carros.R;

import livroandroid.lib.utils.AndroidUtils;

/**
 * @author Lucas Gonçalves de Campos
 * @since 1.0.0
 */
public class AboutDialog extends DialogFragment {

    public static final String TAG_DIALOG_ABOUT = "dialog_about";

    public static void showAbout(FragmentManager fm) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment prev= fm.findFragmentByTag(TAG_DIALOG_ABOUT);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        new AboutDialog().show(transaction, TAG_DIALOG_ABOUT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();

        String versionName = AndroidUtils.getVersionName(getActivity());

        aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName)));

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflater.inflate(R.layout.dialog_about, null);
        view.setText(aboutBody);
        view.setMovementMethod(new LinkMovementMethod());
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
