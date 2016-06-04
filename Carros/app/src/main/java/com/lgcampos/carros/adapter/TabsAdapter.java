package com.lgcampos.carros.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lgcampos.carros.R;
import com.lgcampos.carros.fragments.CarrosFragment;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private final Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = CarrosFragment.newInstance(R.string.classicos);
        } else if (position == 1) {
            fragment = CarrosFragment.newInstance(R.string.esportivos);
        } else {
            fragment = CarrosFragment.newInstance(R.string.luxo);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.classicos);
        } else if (position == 1) {
            return context.getString(R.string.esportivos);
        }
        return context.getString(R.string.luxo);
    }
}
