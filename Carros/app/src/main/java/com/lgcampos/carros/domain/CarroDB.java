package com.lgcampos.carros.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class CarroDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "livro_android.sqlite";
    private static final String TABELA_CARRO = "carro";

    public CarroDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela carro...");
        db.execSQL("CREATE TABLE IF NOT EXISTS carro (" +
                "_id integer primary key autoincrement, " +
                "nome text, " +
                "desc text, " +
                "url_foto text, " +
                "url_info text, " +
                "url_video text, " +
                "latitude text, " +
                "longitude text, " +
                "tipo text);");
        Log.d(TAG, "Tabela criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long save(Carro carro) {
        long id = carro.id;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nome", carro.nome);
            values.put("desc", carro.desc);
            values.put("url_foto", carro.urlFoto);
            values.put("url_info", carro.urlInfo);
            values.put("url_video", carro.urlVideo);
            values.put("latitude", carro.longitude);
            values.put("longitude", carro.latitude);
            values.put("tipo", carro.tipo);

            if (id != 0) {
                String _id = String.valueOf(carro.id);
                String[] whereArgs = new String[]{_id};

                return db.update(TABELA_CARRO, values, "_id = ?", whereArgs);
            } else {
                id = db.insert(TABELA_CARRO, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    public int delete(Carro carro) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete(TABELA_CARRO, "_id = ?", new String[]{String.valueOf(carro.id)});
            Log.d(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }

    public int deleteCarrosByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete(TABELA_CARRO, "tipo = ?", new String[]{tipo});
            Log.d(TAG, "Deletou [" + count + "] registros.");
            return count;
        } finally {
            db.close();
        }
    }

    public List<Carro> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.query(TABELA_CARRO, null, null, null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }

    public List<Carro> findAllbyTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.query(TABELA_CARRO, null, "tipo = '" +tipo+ "'", null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }

    private List<Carro> toList(Cursor cursor) {
        List<Carro> carros = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carros.add(carro);
                carro.id = cursor.getLong(cursor.getColumnIndex("_id"));
                carro.nome = cursor.getString(cursor.getColumnIndex("nome"));
                carro.desc = cursor.getString(cursor.getColumnIndex("desc"));
                carro.urlFoto = cursor.getString(cursor.getColumnIndex("url_foto"));
                carro.urlInfo = cursor.getString(cursor.getColumnIndex("url_info"));
                carro.urlVideo = cursor.getString(cursor.getColumnIndex("url_video"));
                carro.latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                carro.longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                carro.tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            } while (cursor.moveToNext());
        }

        return carros;
    }

    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }

    public void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }
}
