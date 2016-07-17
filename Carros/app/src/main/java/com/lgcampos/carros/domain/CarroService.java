package com.lgcampos.carros.domain;

import android.content.Context;
import android.util.Log;

import com.lgcampos.carros.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.HttpHelper;

/**
 * Carregar {@link Carro}
 *
 * @author Lucas Campos
 * @sinse 1.0.0
 */
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = CarroService.class.getSimpleName();
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_%s.json";

    private static List<Carro> parseJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject object = root.getJSONObject("carros");
            JSONArray jsonCarros = object.getJSONArray("carro");
            
            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");

                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.nome + " > "  + c.urlFoto);
                }

                carros.add(c);
            }

            if (LOG_ON) {
                Log.d(TAG, carros.size() + " encontrados");
            }
            
            
        } catch (JSONException e) {
            throw  new IOException(e.getMessage(), e);
        }

        return carros;
    }

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String url = String.format(URL, tipoString);

        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);

        return parseJSON(context, json);
    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        }
        return "luxo";
    }
}
