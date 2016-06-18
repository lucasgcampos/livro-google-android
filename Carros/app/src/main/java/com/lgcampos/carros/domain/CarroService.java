package com.lgcampos.carros.domain;

import android.content.Context;
import android.util.Log;

import com.lgcampos.carros.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.XMLUtils;

/**
 * Carregar {@link Carro}
 *
 * @author Lucas Campos
 * @sinse 1.0.0
 */
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = CarroService.class.getSimpleName();

    private static String readFile(Context context, int tipo) throws IOException {
        if (tipo == R.string.classicos) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        } else if (tipo == R.string.esportivos) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }

        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

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

    public static List<Carro> getCarros(Context context, int tipo)  {
        try {
            String json = readFile(context, tipo);
            return parseJSON(context, json);
        } catch (IOException e) {
            Log.e(TAG, "Erro ao ler os carros: " + e.getMessage(), e);
            return null;
        }
    }
}
