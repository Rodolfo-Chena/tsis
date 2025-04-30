package mx.modelo;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Entrada;
import mx.uam.tsis.ejemplobackend.negocio.modelo.TipoEntrada;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntradaTest {

    private Entrada entrada;

    @BeforeEach
    public void setUp() {
        entrada = new Entrada(1L, TipoEntrada.GENERAL, LocalDateTime.now(), "A1", 100.0);
    }

    @Test
    public void testGettersAndSetters() {
        entrada.setId(2L);
        entrada.setTipo(TipoEntrada.PREFERENTE);
        entrada.setFecha(LocalDateTime.now().plusDays(1));
        entrada.setAsiento("B1");
        entrada.setPrecio(120.0);
        entrada.setReservada(true);
        entrada.setReservaExpiraEn(LocalDateTime.now().plusDays(2));

        assertEquals(2L, entrada.getId());
        assertEquals(TipoEntrada.PREFERENTE, entrada.getTipo());
        assertNotNull(entrada.getFecha());
        assertEquals("B1", entrada.getAsiento());
        assertEquals(120.0, entrada.getPrecio());
        assertTrue(entrada.isReservada());
        assertNotNull(entrada.getReservaExpiraEn());
    }

    @Test
    public void testEqualsAndHashCode() {
        Entrada entrada2 = new Entrada(1L, TipoEntrada.GENERAL, LocalDateTime.now(), "A1", 100.0);
        Entrada entrada3 = new Entrada(2L, TipoEntrada.PREFERENTE, LocalDateTime.now(), "B1", 120.0);

        assertEquals(entrada, entrada2); // Debería ser igual
        assertNotEquals(entrada, entrada3); // Debería ser diferente

        assertEquals(entrada.hashCode(), entrada2.hashCode()); // Los hashCodes deberían ser iguales
        assertNotEquals(entrada.hashCode(), entrada3.hashCode()); // Los hashCodes deberían ser diferentes
    }

    @Test
    public void testConstructor() {
        Entrada entrada = new Entrada(3L, TipoEntrada.VIP, LocalDateTime.now().plusHours(1), "C1", 150.0);

        assertEquals(3L, entrada.getId());
        assertEquals(TipoEntrada.VIP, entrada.getTipo());
        assertNotNull(entrada.getFecha());
        assertEquals("C1", entrada.getAsiento());
        assertEquals(150.0, entrada.getPrecio());
        assertFalse(entrada.isReservada());
        assertNull(entrada.getReservaExpiraEn());
    }
}
