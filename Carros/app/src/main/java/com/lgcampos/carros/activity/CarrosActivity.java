package com.lgcampos.carros.activity;

import android.os.Bundle;

import com.lgcampos.carros.R;
import com.lgcampos.carros.fragments.CarrosFragment;

public class CarrosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo", 0)));

        if (savedInstanceState == null) {
            CarrosFragment fragment = new CarrosFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
