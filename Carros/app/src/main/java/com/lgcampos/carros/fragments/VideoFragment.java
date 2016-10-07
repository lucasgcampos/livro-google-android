package com.lgcampos.carros.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lgcampos.carros.R;
import com.lgcampos.carros.domain.Carro;

import org.parceler.Parcels;

/**
 * Video Fragment
 *
 * @author Lucas Campos
 * @since 1.0.0
 */

public class VideoFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        VideoView videoView = (VideoView) view.findViewById(R.id.videoView);

        Carro carro = Parcels.unwrap(getArguments().getParcelable("carro"));

        if (carro != null) {
            videoView.setVideoURI(Uri.parse(carro.urlVideo));
            videoView.setMediaController(new MediaController(getContext()));
            videoView.start();

            toast("start: " + carro.urlVideo);
        }

        return view;
    }
}
