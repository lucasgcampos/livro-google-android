package com.lgcampos.carros.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lgcampos.carros.R;
import com.lgcampos.carros.adapter.TabsAdapter;
import com.lgcampos.carros.fragments.AboutDialog;

import livroandroid.lib.utils.Prefs;

public class MainActivity extends BaseActivity {

    public static final String PREFS_TAB_INDEX = "tabIdx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpNavDrawer();
        setupViewPagerTabs();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack(v, "Exemplo de FAB Button" +
                        "");
            }
        });
    }

    private void setupViewPagerTabs() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getContext(), getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        tabLayout.setTabTextColors(cor, cor);

        int tabIndex = Prefs.getInteger(getContext(), PREFS_TAB_INDEX);
        viewPager.setCurrentItem(tabIndex);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Prefs.setInteger(getContext(), PREFS_TAB_INDEX, viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
