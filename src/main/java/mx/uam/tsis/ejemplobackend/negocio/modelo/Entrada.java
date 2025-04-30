package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clase que representa una entrada para un evento
 * 
 */
@Entity
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la entrada", required = true)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de entrada (VIP, Preferente, General)", required = true)
    private TipoEntrada tipo;

    @NotNull
    @Schema(description = "Fecha y hora del evento", required = true)
    private LocalDateTime fecha;

    
    @Schema(description = "Número de asiento", required = true)
    private String asiento;

    @NotNull
    @Schema(description = "Precio por entrada", required = true)
    private Double precio;

    @Schema(description = "Indica si la entrada está reservada")
    private boolean reservada;

    @Schema(description = "Fecha y hora en que expira la reserva (si está reservada)")
    private LocalDateTime reservaExpiraEn;

    public Entrada() {
    }

    public Entrada(Long id, TipoEntrada tipo, LocalDateTime fecha, String asiento, Double precio) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.asiento = asiento;
        this.precio = precio;
        this.reservada = false;
        this.reservaExpiraEn = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEntrada getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntrada tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }

    public LocalDateTime getReservaExpiraEn() {
        return reservaExpiraEn;
    }

    public void setReservaExpiraEn(LocalDateTime reservaExpiraEn) {
        this.reservaExpiraEn = reservaExpiraEn;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Entrada))
            return false;
        Entrada other = (Entrada) obj;
        return id != null && id.equals(other.getId());
    }
}
