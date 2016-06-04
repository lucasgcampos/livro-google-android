package com.lgcampos.carros.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.fragments.CarroFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();
        Carro carro = (Carro) getIntent().getSerializableExtra("carro");
        getSupportActionBar().setTitle(carro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            CarroFragment fragment = new CarroFragment();
            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().replace(R.id.carro_fragment, fragment).commit();
        }


    }

}
