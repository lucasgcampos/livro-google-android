package com.lgcampos.carros;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;

/**
 * @author Lucas Gonçalves de Campos
 * @since 1.0.0
 */
public class CarrosApplication extends Application {

    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;
    private Bus bus = new Bus();

    public static CarrosApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CarrosApplication.onCreate()");
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(TAG, "CarrosApplication.onTerminate()");
    }

    public Bus getBus() {
        return bus;
    }
}
