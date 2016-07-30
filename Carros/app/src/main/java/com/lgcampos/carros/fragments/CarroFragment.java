package com.lgcampos.carros.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.activity.CarroActivity;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.domain.CarroDB;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) getView().findViewById(R.id.tDesc);
        textView.setText(carro.desc);
        ImageView imageView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imageView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            EditarCarroDialog.show(getFragmentManager(), carro, new EditarCarroDialog.Callback() {
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Editado: [" + carro.nome + "].");
                    CarroDB db = new CarroDB(getActivity());
                    db.save(carro);

                    CarroActivity activity = (CarroActivity) getActivity();
                    activity.setTitle(carro.nome);
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            DeletarCarroDialog.show(getFragmentManager(), new DeletarCarroDialog.Callback() {
                @Override
                public void onClickYes() {
                    toast("Carro [" + carro.nome + "] deletado.");
                    CarroDB db = new CarroDB(getActivity());
                    db.delete(carro);
                    getActivity().finish();
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            toast("Compartilhar: " + carro.nome);
            return true;
        } else if (item.getItemId() == R.id.action_video) {
            toast("Vídeo");
            return true;
        } else if (item.getItemId() == R.id.action_maps) {
            toast("Mapa");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
