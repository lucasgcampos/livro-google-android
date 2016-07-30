package com.lgcampos.carros.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class DeletarCarroDialog extends DialogFragment {

    private static final String EXCLUIR_CARRO = "deletar_carro";
    private Callback callback;

    public interface  Callback {
        void onClickYes();
    }

    public static void show(FragmentManager manager, Callback callback) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(EXCLUIR_CARRO);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        DeletarCarroDialog fragment = new DeletarCarroDialog();
        fragment.callback = callback;
        fragment.show(transaction, EXCLUIR_CARRO);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (callback != null) {
                            callback.onClickYes();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deletar esse carro?")
                .setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener);

        return builder.create();
    }

}
