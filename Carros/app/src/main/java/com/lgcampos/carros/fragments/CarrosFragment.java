package com.lgcampos.carros.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;

import java.util.List;

public class CarrosFragment extends BaseFragment {

    private int tipo;
    private RecyclerView recyclerView;
    private List<Carro> carros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.tipo = getArguments().getInt("tipo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText("Carros: " + getString(tipo));

        return view;
    }

    public static CarrosFragment newInstance(int tipo) {
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment fragment = new CarrosFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
