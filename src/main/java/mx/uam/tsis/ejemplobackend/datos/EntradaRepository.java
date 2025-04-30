package mx.uam.tsis.ejemplobackend.datos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Entrada;
import mx.uam.tsis.ejemplobackend.negocio.modelo.TipoEntrada;

/**
 * Repositorio que permite acceder y manipular las entradas
 * del sistema (para eventos).
 * 
 */
public interface EntradaRepository extends CrudRepository<Entrada, Long> {

    // Encuentra entradas disponibles por tipo y fecha
    List<Entrada> findByTipoAndFechaAndReservadaFalse(TipoEntrada tipo, LocalDateTime fecha);

    // Encuentra las entradas cuya reserva ya expiró
    List<Entrada> findByReservadaTrueAndReservaExpiraEnBefore(LocalDateTime ahora);

    @Query("SELECT e FROM Entrada e WHERE e.tipo = :tipo AND e.fecha = :fecha AND e.reservada = false")
    List<Entrada> findDisponibles(TipoEntrada tipo, LocalDateTime fecha);

    @Query("SELECT e FROM Entrada e WHERE e.reservada = true AND e.reservaExpiraEn < :ahora")
    List<Entrada> findExpiradas(LocalDateTime ahora);
}
