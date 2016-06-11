package com.lgcampos.carros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lgcampos.carros.R;

import livroandroid.lib.utils.NavDrawerUtil;

/**
 * @author Lucas Gonçalves de Campos
 * @since 1.0.0
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setUpNavDrawer() {
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null && drawerLayout != null) {
            NavDrawerUtil.setHeaderValues(navigationView,
                    R.id.containerNavDrawerListViewHeader,
                    R.drawable.nav_drawer_header,
                    R.drawable.ic_logo_user,
                    R.string.nav_drawer_username,
                    R.string.nav_drawer_email);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    onNavDrawerItemSelected(item);

                    return true;
                }
            });


        }
    }

    private void onNavDrawerItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_carros_todos:
                break;
            case R.id.nav_item_carros_classicos:
                startCarrosActivity(R.string.classicos);
                break;
            case R.id.nav_item_carros_esportivos:
                startCarrosActivity(R.string.esportivos);
                break;
            case R.id.nav_item_carros_luxo:
                startCarrosActivity(R.string.luxo);
                break;
            case R.id.nav_item_site_livro:
                Intent intent = new Intent(getContext(), SiteLivroActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_item_settings:
                toast("Cliclou em configurações");
                break;
        }
    }

    private void startCarrosActivity(int tipo) {
        Intent intent = new Intent(getContext(), CarrosActivity.class);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout != null) {
                    openDrawer();

                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
