package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mx.uam.tsis.ejemplobackend.datos.EntradaRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Entrada;
import mx.uam.tsis.ejemplobackend.negocio.modelo.TipoEntrada;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntradaServiceTest {

    @Mock
    private EntradaRepository entradaRepository;

    @InjectMocks
    private EntradaService entradaService;

    @Test
    public void testSeleccionarEntradasExito() {
        LocalDateTime fecha = LocalDateTime.now();
        Entrada entrada = new Entrada();
        entrada.setTipo(TipoEntrada.GENERAL);
        entrada.setPrecio(100.0);
        entrada.setReservada(false);

        List<Entrada> disponibles = Arrays.asList(new Entrada(), new Entrada(), new Entrada(), new Entrada(), new Entrada(), new Entrada());

        disponibles.forEach(e -> {
            e.setPrecio(100.0);
            e.setTipo(TipoEntrada.GENERAL);
        });

        when(entradaRepository.findDisponibles(TipoEntrada.GENERAL, fecha)).thenReturn(disponibles);

        List<Entrada> reservadas = entradaService.seleccionarEntradas(TipoEntrada.GENERAL, 6, fecha);

        assertNotNull(reservadas);
        assertEquals(6, reservadas.size());
        assertTrue(reservadas.get(0).isReservada());
    }

    @Test
    public void testSeleccionarEntradasFallaPorFaltaDeDisponibles() {
        LocalDateTime fecha = LocalDateTime.now();
        when(entradaRepository.findDisponibles(TipoEntrada.GENERAL, fecha)).thenReturn(Collections.singletonList(new Entrada()));

        List<Entrada> resultado = entradaService.seleccionarEntradas(TipoEntrada.GENERAL, 3, fecha);

        assertNull(resultado);
    }

    @Test
    public void testCalcularPrecioTotalSinDescuento() {
        Entrada e1 = new Entrada();
        e1.setPrecio(100.0);
        Entrada e2 = new Entrada();
        e2.setPrecio(150.0);
        List<Entrada> entradas = Arrays.asList(e1, e2);

        double total = entradaService.calcularPrecioTotal(entradas);

        assertEquals(250.0, total);
    }

    @Test
    public void testCalcularPrecioTotalConDescuento() {
        Entrada e = new Entrada();
        e.setPrecio(100.0);
        List<Entrada> entradas = Arrays.asList(e, e, e, e, e, e); // 6 entradas

        double total = entradaService.calcularPrecioTotal(entradas);

        assertEquals(540.0, total); // 600 * 0.9
    }

    @Test
    public void testRegistrarEntrada() {
        Entrada entrada = new Entrada();
        entrada.setReservada(true);
        entrada.setReservaExpiraEn(LocalDateTime.now());

        when(entradaRepository.save(any(Entrada.class))).thenAnswer(i -> i.getArgument(0));

        Entrada resultado = entradaService.registrarEntrada(entrada);

        assertFalse(resultado.isReservada());
        assertNull(resultado.getReservaExpiraEn());
    }

    @Test
    public void testLiberarReservasExpiradas() {
        Entrada entrada1 = new Entrada();
        entrada1.setReservada(true);
        entrada1.setReservaExpiraEn(LocalDateTime.now().minusMinutes(10));

        when(entradaRepository.findExpiradas(any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(entrada1));

        entradaService.liberarReservasExpiradas();

        assertFalse(entrada1.isReservada());
        assertNull(entrada1.getReservaExpiraEn());
    }
}
