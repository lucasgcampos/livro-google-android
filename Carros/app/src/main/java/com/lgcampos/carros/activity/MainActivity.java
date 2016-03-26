package com.lgcampos.carros.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lgcampos.carros.R;
import com.lgcampos.carros.fragments.AboutDialog;
import com.lgcampos.carros.fragments.CarrosFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpNavDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutDialog.showAbout(getSupportFragmentManager());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
