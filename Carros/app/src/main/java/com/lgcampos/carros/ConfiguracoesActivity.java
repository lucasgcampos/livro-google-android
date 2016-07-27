package com.lgcampos.carros;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfiguracoesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, new PrefsFragment());
        transaction.commit();
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
