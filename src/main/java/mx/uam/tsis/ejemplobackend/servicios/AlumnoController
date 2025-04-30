package mx.uam.tsis.ejemplobackend.servicios;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@Tag(name = "Alumno", description = "API para gestionar alumnos")
@RestController
@RequestMapping("/v1") // Versionamiento
public class AlumnoController {
	
	private static final Logger log = LoggerFactory.getLogger(AlumnoController.class);

	@Autowired
	private AlumnoService alumnoService;
	
	@Operation(
			summary = "Crear alumno",
			description = "Permite crear un nuevo alumno, la matrícula debe ser única"
			) // Documentacion del api
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) { // Validaciones
				
		log.info("Recibí llamada a create con "+nuevoAlumno); // Logging
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	

	}
	
	@Operation(summary = "Obtener todos los alumnos")
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}

	@Operation(summary = "Obtener un alumno por matrícula")
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); //.body(alumno);
	
		
	}
	
	@Operation(summary = "Actualizar un alumno")
	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("matricula") Integer matricula, @RequestBody @Valid Alumno nuevoAlumno) {
		
		Boolean result = alumnoService.update(nuevoAlumno);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).body(nuevoAlumno);			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}

		
	}
	
	/*
	public delete() {
		
	}*/
 
}
