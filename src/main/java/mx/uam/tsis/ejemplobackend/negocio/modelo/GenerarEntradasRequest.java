package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para generar múltiples entradas")
public class GenerarEntradasRequest {

    @Schema(description = "Tipo de entrada", required = true, example = "GENERAL")
    public TipoEntrada tipo;

    @Schema(description = "Cantidad de entradas a generar", required = true, example = "50")
    public int cantidad;

    @Schema(description = "Fecha del evento", required = true, example = "2025-05-01T18:00:00")
    public LocalDateTime fecha;

    @Schema(description = "Precio por entrada", required = true, example = "100.0")
    public double precio;
}
