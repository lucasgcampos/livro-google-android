package com.lgcampos.carros.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;

import org.parceler.Parcels;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class EditarCarroDialog extends DialogFragment {

    public static final String EDITAR_CARRO = "editar_carro";
    public static final String CARRO = "carro";
    private Callback callback;
    private Carro carro;
    private TextView tNome;

    public interface  Callback {
        void onCarroUpdated(Carro carro);
    }

    public static void show(FragmentManager manager, Carro carro, Callback callback) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(EDITAR_CARRO);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);
        EditarCarroDialog fragment = new EditarCarroDialog();
        fragment.callback = callback;
        Bundle args = new Bundle();
        args.putParcelable(CARRO, Parcels.wrap(carro));
        fragment.setArguments(args);
        fragment.show(transaction, EDITAR_CARRO);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }

        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_editar_carro, container, false);
        view.findViewById(R.id.btAtualizar).setOnClickListener(onClickAtualizar());
        tNome = (TextView) view.findViewById(R.id.tNome);
        this.carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        if (carro != null) {
            tNome.setText(carro.nome);
        }
        return view;
    }

    private View.OnClickListener onClickAtualizar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novoNome = tNome.getText().toString();
                if (novoNome == null || novoNome.trim().isEmpty()) {
                    tNome.setError("Informe o nome");
                    return;
                }

                Context context = v.getContext();
                carro.nome = novoNome;

                if (callback != null) {
                    callback.onCarroUpdated(carro);
                }

                dismiss();
            }
        };
    }
}
