package com.lgcampos.carros.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgcampos.carros.R;
import com.lgcampos.carros.activity.CarroActivity;
import com.lgcampos.carros.adapter.CarroAdapter;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.domain.CarroService;

import org.parceler.Parcels;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros() {
        this.carros = CarroService.getCarros(getContext(), tipo);
        recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
    }

    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            public void onClickCarro(View view, int index) {
                Carro carro = carros.get(index);
                Intent intent = new Intent(getActivity(), CarroActivity.class);
                intent.putExtra("carro", Parcels.wrap(carro));
                startActivity(intent);
            }
        };
    }

    public static CarrosFragment newInstance(int tipo) {
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment fragment = new CarrosFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
