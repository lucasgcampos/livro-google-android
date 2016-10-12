package com.lgcampos.carros.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.lgcampos.carros.CarrosApplication;
import com.lgcampos.carros.R;
import com.lgcampos.carros.activity.CarroActivity;
import com.lgcampos.carros.activity.MapaActivity;
import com.lgcampos.carros.activity.VideoActivity;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.domain.CarroDB;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import livroandroid.lib.utils.IntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    public static final String REFRESH = "refresh";
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);

        view.findViewById(R.id.imgPlayVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo(carro.urlVideo, v);
            }
        });

        carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextString(R.id.tDesc, carro.desc);
        ImageView imageView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imageView);

        setTextString(R.id.tLat_lng, String.format("Lat/Lng: %s/%s", carro.latitude, carro.longitude));
        MapaFragment mapaFragment = new MapaFragment();
        mapaFragment.setArguments(getArguments());
        getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, mapaFragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            EditarCarroDialog.show(getFragmentManager(), carro, new EditarCarroDialog.Callback() {
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Editado: [" + carro.nome + "].");
                    CarroDB db = new CarroDB(getActivity());
                    db.save(carro);

                    CarroActivity activity = (CarroActivity) getActivity();
                    activity.setTitle(carro.nome);
                    CarrosApplication.getInstance().getBus().post(REFRESH);
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            DeletarCarroDialog.show(getFragmentManager(), new DeletarCarroDialog.Callback() {
                @Override
                public void onClickYes() {
                    toast("Carro [" + carro.nome + "] deletado.");
                    CarroDB db = new CarroDB(getActivity());
                    db.delete(carro);
                    getActivity().finish();
                    CarrosApplication.getInstance().getBus().post(REFRESH);
                }
            });
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            toast("Compartilhar: " + carro.nome);
            return true;
        } else if (item.getItemId() == R.id.action_video) {
            String url = carro.urlVideo;

            View menuItemView = getActivity().findViewById(item.getItemId());
            if (menuItemView != null && url != null) {
                showVideo(url, menuItemView);
            }
            return true;
        } else if (item.getItemId() == R.id.action_maps) {
            Intent intent = new Intent(getContext(), MapaActivity.class);
            intent.putExtra("carro", Parcels.wrap(carro));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showVideo(final String url, View ancoraView) {
        if (url != null && ancoraView != null) {
            PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(), ancoraView);
            popupMenu.inflate(R.menu.menu_popup_video);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_video_browser) {
                        IntentUtils.openBrowser(getContext(), url);
                    } else if (item.getItemId() == R.id.action_video_player) {
                        IntentUtils.showVideo(getContext(), url);
                    } else if (item.getItemId() == R.id.action_video_videoview) {
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("carro", Parcels.wrap(carro));
                        startActivity(intent);
                        IntentUtils.showVideo(getContext(), url);
                    }
                    return true;
                }
            });

            popupMenu.show();
        }
    }
}
