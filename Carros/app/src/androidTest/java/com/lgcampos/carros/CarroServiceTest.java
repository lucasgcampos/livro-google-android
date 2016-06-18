package com.lgcampos.carros;

import android.test.AndroidTestCase;

import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.domain.CarroService;

import java.util.List;

/**
 * @author Lucas Campos
 * @since 1.0.0
 */
public class CarroServiceTest extends AndroidTestCase {

    public void testGetCarros() {
        List<Carro> carros = CarroService.getCarros(getContext(), R.string.esportivos);
        assertNotNull(carros);

        assertTrue(carros.size() == 10);

        Carro c0 = carros.get(0);
        assertEquals("Ferrari FF", c0.nome);
        assertEquals("44.532218", c0.latitude);
        assertEquals("10.864019", c0.longitude);

        Carro c9 = carros.get(9);
        assertEquals("MERCEDES-BENZ C63 AMG", c9.nome);
        assertEquals("-23.564224", c9.latitude);
        assertEquals("-46.653156", c9.longitude);
    }
}
