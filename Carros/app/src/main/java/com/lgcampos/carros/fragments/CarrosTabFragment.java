package com.lgcampos.carros.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgcampos.carros.R;
import com.lgcampos.carros.adapter.TabsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarrosTabFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carros_tab, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getContext(), getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        tabLayout.setTabTextColors(cor, cor);

        return view;
    }

}
