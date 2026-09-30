package edu.uees.testing.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReservaServiceTest {

    @Test
    void entornoJUnitFunciona() {
        assertTrue(true);
    }

    @Test
    void dosHorasPermitenCancelar() {
        ReservaService servicio = new ReservaService(null, null, null);
        assertTrue(
            servicio.puedeCancelar(2)
        );
    }

    @Test
    void totalNegativoLanzaExcepcion() {
        ReservaService servicio = new ReservaService(null, null, null);
        assertThrows(
            IllegalArgumentException.class,
            () -> servicio.calcularTotal(
                "NORMAL", -1
            )
        );
    }
}