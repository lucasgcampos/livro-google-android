package com.lgcampos.carros.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;

import org.parceler.Parcels;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class MapaFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap map;
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        carro = Parcels.unwrap(getArguments().getParcelable("carro"));

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        if (carro != null) {
            map.setMyLocationEnabled(true);

            LatLng location = new LatLng(Double.parseDouble(carro.latitude), Double.parseDouble(carro.longitude));

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
            map.moveCamera(update);
            map.addMarker(new MarkerOptions()
                    .title(carro.nome)
                    .snippet(carro.desc)
                    .position(location));

            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
