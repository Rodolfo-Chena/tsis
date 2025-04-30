package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Service
public class GrupoService {
	
	private static final Logger log = LoggerFactory.getLogger(GrupoService.class);

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	public Grupo create(Grupo nuevo) {
		
		// Validar reglas de negocio previas a la creación
		
		return grupoRepository.save(nuevo);
	}
	
	public Iterable <Grupo> retrieveAll() {
		return grupoRepository.findAll();
	}
	
	/**
	 * 
	 * Método que permite agregar un alumno a un grupo
	 * 
	 * @param groupId el id del grupo
	 * @param matricula la matricula del alumno
	 * @return true si se agregó correctamente, false si no
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		
		log.info("Agregando alumno con matricula "+matricula+" al grupo "+groupId);
		
		// 1.- Recuperamos primero al alumno
		Alumno alumno = alumnoService.findByMatricula(matricula);
		
		// 2.- Recuperamos el grupo
		Optional <Grupo> grupoOpt = grupoRepository.findById(groupId);
		
		// 3.- Verificamos que el alumno y el grupo existan
		if(!grupoOpt.isPresent() || alumno == null) {
			
			log.info("No se encontró alumno o grupo");
			
			return false;
		}
		
		// 4.- Agrego el alumno al grupo
		Grupo grupo = grupoOpt.get();
		grupo.addAlumno(alumno);
		
		// 5.- Persistir el cambio
		grupoRepository.save(grupo);
		
		return true;
	}
	
	

}
