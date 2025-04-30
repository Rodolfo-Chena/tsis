package mx.uam.tsis.ejemplobackend.negocio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.EntradaRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Entrada;
import mx.uam.tsis.ejemplobackend.negocio.modelo.TipoEntrada;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository entradaRepository;

    /**
     * Selecciona entradas y las reserva temporalmente
     * @param tipo Tipo de entrada (VIP, Preferente, General)
     * @param cantidad Cantidad de boletos
     * @param fecha Fecha del evento
     * @return Lista de entradas reservadas o null si no hay disponibilidad
     */
    public List<Entrada> seleccionarEntradas(TipoEntrada tipo, int cantidad, LocalDateTime fecha) {
        
        // 1. Verificar disponibilidad
        List<Entrada> disponibles = entradaRepository.findDisponibles(tipo, fecha);
        
        if(disponibles.size() < cantidad) {
            return null; // No hay suficientes entradas
        }

        // 2. Aplicar descuento si se compran más de 5
        double precioUnitario = disponibles.get(0).getPrecio();
        double total = precioUnitario * cantidad;

        if(cantidad > 5) {
            total *= 0.9; // Descuento del 10%
        }

        // 3. Reservar entradas (bloquearlas temporalmente por 5 minutos)
        List<Entrada> reservadas = disponibles.subList(0, cantidad);
        for(Entrada entrada : reservadas) {
            entrada.setReservada(true);
            entrada.setReservaExpiraEn(LocalDateTime.now().plusMinutes(5));
        }

        entradaRepository.saveAll(reservadas);
        return reservadas;
    }

    /**
     * Método para liberar entradas cuya reserva ha expirado
     */
    public void liberarReservasExpiradas() {
        List<Entrada> expiradas = entradaRepository.findExpiradas(LocalDateTime.now());
        for(Entrada entrada : expiradas) {
            entrada.setReservada(false);
            entrada.setReservaExpiraEn(null);
        }
        entradaRepository.saveAll(expiradas);
    }
    public Entrada registrarEntrada(Entrada entrada) {
    entrada.setReservada(false);
    entrada.setReservaExpiraEn(null);
    return entradaRepository.save(entrada);
}
/**
     * Método para calcular el precio total con descuento
     * @param entradas Lista de entradas seleccionadas
     * @return Precio total después de aplicar descuento
     */
    public double calcularPrecioTotal(List<Entrada> entradas) {
        double total = 0;
        
        // Sumar el precio de todas las entradas
        for (Entrada entrada : entradas) {
            total += entrada.getPrecio();
        }
        
        // Si hay más de 5 entradas, aplicar el 10% de descuento
        if (entradas.size() > 5) {
            total = total * 0.90;  // Descuento del 10%
        }

        return total;
    }

}
