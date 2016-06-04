package com.lgcampos.carros.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends Fragment {

    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        carro = (Carro) getArguments().getSerializable("carro");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) getView().findViewById(R.id.tDesc);
        textView.setText(carro.desc);
        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imageView);
    }
}
