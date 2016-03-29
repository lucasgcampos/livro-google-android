package com.lgcampos.carros.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO qual é o objetivo desta classe?
 *
 * @author Lucas Campos
 * @sinse 1.0.0
 */
public class CarroService {
    public static List<Carro> getCarros(Context context, int tipo) {
        String tipoString = context.getString(tipo);

        List<Carro> carros = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Carro carro = new Carro();
            carro.nome = "Carro " + tipoString + ": " + i; //Nome dinâmico conforme o tipo
            carro.desc = "Desc " + i;
            carro.urlFoto = "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png";
            carros.add(carro);
        }

        return carros;
    }
}
