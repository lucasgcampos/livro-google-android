package com.lgcampos.carros.domain;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lgcampos.carros.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;

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

    public static List<Carro> getCarrosFromWebService(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String url = String.format(URL, tipoString);

        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);

        List<Carro> carros = parseJSON(context, json);
        salvarCarros(context, tipo, carros);

        return carros;
    }

    private static void salvarCarros(Context context, int tipo, List<Carro> carros) {
        CarroDB db = new CarroDB(context);
        try {
            String tipoString = String.valueOf(tipo);
            db.deleteCarrosByTipo(tipoString);

            for (Carro carro : carros) {
                carro.tipo = tipoString;
                Log.d(TAG, "Salvando o carro " + carro.nome);
                db.save(carro);
            }
        } finally {
            db.close();
        }
    }

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = getCarrosFromBranco(context, tipo);


//        List<Carro> carros = getCarrosDoArquivo(context, tipo);
        if (carros != null && !carros.isEmpty()) {
            return carros;
        }

        return getCarrosFromWebService(context, tipo);
    }

    private static List<Carro> getCarrosFromBranco(Context context, int tipo) {
        CarroDB db = new CarroDB(context);
        try {
            String tipoString = String.valueOf(tipo);
            List<Carro> carros = db.findAllbyTipo(tipoString);
            Log.d(TAG, "Retornando " + carros.size() + " carros [" + tipoString + "] do banco");
            return carros;
        } finally {
            db.close();
        }
    }

    public static List<Carro> getCarrosDoArquivo(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String fileName = String.format("carros_%s.json", tipoString);
        Log.d(TAG, "Abrindo arquivo: " + fileName);

        String json = FileUtils.readFile(context, fileName, "UTF-8");

        if (json == null) {
            Log.d(TAG, "Arquivo " + fileName + " não encontrado.");
            return null;
        }

        List<Carro> carros = parseJSON(context, json);
        Log.d(TAG, "Retornando carros do arquivo " + fileName + ".");

        return carros;
    }

    private static void salvarArquivoNaMemoriaExterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = SDCardUtils.getPrivateFile(context, fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(file, json);
        Log.d(TAG, "1) Arquivo privado salvo na pasta downloads: " + file);

        file = SDCardUtils.getPublicFile(fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(file, json);
        Log.d(TAG, "2) Arquivo público salvo na pasta downloads: " + file);
    }

    private static void salvarArquivoNaMemoriaInterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = FileUtils.getFile(context, fileName);
        IOUtils.writeString(file, json);
        Log.d(TAG, "Arquivo salvo: " + file);
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
