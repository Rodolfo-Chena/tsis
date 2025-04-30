package mx.uam.tsis.ejemplobackend.servicios;

import mx.uam.tsis.ejemplobackend.datos.EntradaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import mx.uam.tsis.ejemplobackend.negocio.EntradaService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Entrada;
import mx.uam.tsis.ejemplobackend.negocio.modelo.GenerarEntradasRequest;
import mx.uam.tsis.ejemplobackend.negocio.modelo.TipoEntrada;

@Tag(name = "Entrada", description = "API para seleccionar y liberar entradas")
@RestController
@RequestMapping("/v1/entradas")
public class EntradaController {

    private static final Logger log = LoggerFactory.getLogger(EntradaController.class);

    @Autowired
    private EntradaService entradaService;
    @Autowired
    private EntradaRepository entradaRepository;

    @Operation(summary = "Seleccionar entradas disponibles", description = "Selecciona entradas de cierto tipo, cantidad y fecha. Reserva temporal por 5 minutos.")
    @PostMapping(path = "/seleccionar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> seleccionarEntradas(
        @RequestParam TipoEntrada tipo,
        @RequestParam @Min(1) int cantidad,
        @RequestParam String fecha // formato ISO 8601: yyyy-MM-ddTHH:mm
    ) {
        log.info("Solicitud de selección: tipo={}, cantidad={}, fecha={}", tipo, cantidad, fecha);

        LocalDateTime fechaEvento;
        try {
            fechaEvento = LocalDateTime.parse(fecha);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha inválido (usar yyyy-MM-ddTHH:mm)");
        }

        List<Entrada> reservadas = entradaService.seleccionarEntradas(tipo, cantidad, fechaEvento);

        if (reservadas == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No hay suficientes entradas disponibles");
        }

        // Calcular el precio total con descuento si es necesario
        double precioTotal = entradaService.calcularPrecioTotal(reservadas);

        // Incluir el precio total en la respuesta junto con las entradas reservadas
        return ResponseEntity.status(HttpStatus.OK).body(new SeleccionarEntradasResponse(reservadas, precioTotal));
    }

    @Operation(summary = "Liberar entradas expiradas", description = "Libera entradas que ya no fueron compradas en 5 minutos")
    @PostMapping("/liberar-expiradas")
    public ResponseEntity<?> liberarExpiradas() {
        entradaService.liberarReservasExpiradas();
        return ResponseEntity.ok("Entradas expiradas liberadas correctamente");
    }

    @Operation(summary = "Registrar una nueva entrada")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEntrada(@RequestBody Entrada nuevaEntrada) {
        Entrada guardada = entradaService.registrarEntrada(nuevaEntrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PostMapping("/generar")
    public ResponseEntity<?> generarEntradas(@RequestBody GenerarEntradasRequest request) {

        List<Entrada> entradasGeneradas = new ArrayList<>();

        for (int i = 0; i < request.cantidad; i++) {
            Entrada entrada = new Entrada();

            entrada.setTipo(request.tipo);
            entrada.setFecha(request.fecha);
            entrada.setPrecio(request.precio);
            entrada.setReservada(false);
            entrada.setReservaExpiraEn(null);
            entrada.setAsiento(null); // o puedes generar asientos tipo "A1", "A2", etc.

            entradaRepository.save(entrada);
            entradasGeneradas.add(entrada);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(entradasGeneradas);
    }

    public void setEntradaService(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    public void setEntradaRepository(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    // Clase para estructurar la respuesta de selección de entradas
    public static class SeleccionarEntradasResponse {
        private List<Entrada> entradas;
        private double precioTotal;

        public SeleccionarEntradasResponse(List<Entrada> entradas, double precioTotal) {
            this.entradas = entradas;
            this.precioTotal = precioTotal;
        }

        // Getters y setters
        public List<Entrada> getEntradas() {
            return entradas;
        }

        public void setEntradas(List<Entrada> entradas) {
            this.entradas = entradas;
        }

        public double getPrecioTotal() {
            return precioTotal;
        }

        public void setPrecioTotal(double precioTotal) {
            this.precioTotal = precioTotal;
        }
    }
    
}
