package com.lgcampos.carros.activity;

import android.os.Bundle;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.fragments.CarroFragment;

import org.parceler.Parcels;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();
        Carro carro = Parcels.unwrap(getIntent().getParcelableExtra("carro"));
        getSupportActionBar().setTitle(carro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            CarroFragment fragment = new CarroFragment();
            fragment.setArguments(getIntent().getExtras());
        }

    }

}
