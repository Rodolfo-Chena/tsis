package mx.uam.tsis.ejemplobackend.negocio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Uso de Mockito
public class GrupoServiceTest {

	@Mock
	private GrupoRepository grupoRepositoryMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	@Test
	public void testSuccesfulAddStudentToGroup (){
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");

		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.findByMatricula(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(true,result);
		
		assertEquals(grupo.getAlumnos().get(0),alumno);
		
	}
	
	@Test
	public void testUnsuccesfulAddStudentToGroup (){
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.findByMatricula(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(false,result);
		
		
	}
}
