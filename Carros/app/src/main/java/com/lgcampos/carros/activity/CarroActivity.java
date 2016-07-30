package com.lgcampos.carros.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.fragments.CarroFragment;
import com.squareup.picasso.Picasso;

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

        ImageView appBarImg = (ImageView) findViewById(R.id.app_bar_img);
        Picasso.with(getContext()).load(carro.urlFoto).into(appBarImg);

        if (savedInstanceState == null) {
            CarroFragment fragment = new CarroFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.carro_fragment, fragment).commit();
        }

    }

    public void setTitle(String title) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setTitle(title);
    }

}
