package edu.uees.testing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import edu.uees.testing.availability.DisponibilidadClient;
import edu.uees.testing.domain.EstadoReserva;
import edu.uees.testing.domain.Reserva;
import edu.uees.testing.notification.Notificador;
import edu.uees.testing.repository.ReservaRepository;

class ReservaServiceTest {

    @Test
    void entornoJUnitFunciona() {
        assertTrue(true);
    }

    @Test
    void dosHorasPermitenCancelar() {
        ReservaService servicio = new ReservaService(null, null, null);
        assertTrue(servicio.puedeCancelar(2));
    }

    @Test
    void totalNegativoLanzaExcepcion() {
        ReservaService servicio = new ReservaService(null, null, null);
        assertThrows(
            IllegalArgumentException.class,
            () -> servicio.calcularTotal("NORMAL", -1)
        );
    }

    @Test
    void confirmarReservaConMocks() {
        DisponibilidadClient disponibilidad = mock(DisponibilidadClient.class);
        ReservaRepository repository = mock(ReservaRepository.class);
        Notificador notificador = mock(Notificador.class);
        ReservaService servicio = new ReservaService(disponibilidad, repository, notificador);

        Reserva reserva = new Reserva("R-001", "VIP");

        when(
            disponibilidad.estaDisponible(any())
        ).thenReturn(true);

        servicio.confirmar(reserva);

        assertEquals(
            EstadoReserva.CONFIRMADA,
            reserva.getEstado()
        );

        verify(repository)
            .guardar(reserva);

        verify(notificador)
            .enviarConfirmacion(reserva);
    }
}