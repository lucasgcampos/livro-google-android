package com.lgcampos.carros.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lgcampos.carros.R;
import com.lgcampos.carros.utils.PermissionUtils;

import livroandroid.lib.utils.AlertUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String permisions[] = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        boolean hasPermission = PermissionUtils.validate(this, 0, permisions);

        if (hasPermission) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                AlertUtils.alert(getContext(), R.string.app_name, R.string.msg_alerta_permissao, R.string.ok, new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                return;
            }
        }

        startActivity(new Intent(this, MainActivity.class));
    }
}
