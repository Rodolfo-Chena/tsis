package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * 
 * Clase que contiene la lógica de negocio del manejo de alumnos
 * 
 * @author humbertocervantes
 *
 */
@Service
public class AlumnoService {

	private static final Logger log = LoggerFactory.getLogger(AlumnoService.class);

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * Método que permite crear nuevos alumnos
	 * 
	 * @param nuevoAlumno el alumno que se desea crear en el sistema
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		
		if(!alumnoOpt.isPresent()) {

			return  alumnoRepository.save(nuevoAlumno);
			
		} else {
			
			return null;
			
		}
		
	}
	

	public Iterable <Alumno> retrieveAll () {
		
		
		// Lógica de negocio
		
		return alumnoRepository.findAll();
	}
	
	public Alumno findByMatricula(Integer matricula) {

		// Lógica de negocio
		
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		
		return alumnoOpt.get();
	}
	
	/**
	 * 
	 * Actualiza al alumno
	 * 
	 * @param actualizado
	 * @return true si la actualización fue exitosa
	 */
	public boolean update(Alumno actualizado) {
		// Primero veo que si esté en la BD
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(actualizado.getMatricula());
		
		
		if(alumnoOpt.isPresent()) {
			Alumno alumno = alumnoOpt.get(); // Este es el que está en la bd
			
			alumno.setCarrera(actualizado.getCarrera());
			alumno.setNombre(actualizado.getNombre());
			
			log.info("Persistiendo los cambios "+alumno.getCarrera());
			
			alumnoRepository.save(alumno); // Persisto los cambios
			
			return true;
		} else {
			return false;
		}


	}
}
