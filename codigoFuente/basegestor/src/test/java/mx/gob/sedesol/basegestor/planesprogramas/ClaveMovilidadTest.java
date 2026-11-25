package mx.gob.sedesol.basegestor.planesprogramas;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ClaveMovilidadDuplicadaException;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.FichaDescProgramaRepo;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FichaDescProgramaServiceImpl;

public class ClaveMovilidadTest {
	private FichaDescProgramaServiceImpl servicio;
	private FichaDescProgramaRepo repo;
	@Before public void preparar() {
		servicio = new FichaDescProgramaServiceImpl();
		repo = mock(FichaDescProgramaRepo.class);
		ReflectionTestUtils.setField(servicio, "fichaDescProgramaRepo", repo);
		when(repo.buscarConflictoMovilidadParaGuardar(anyString(), anyInt(), anyString())).thenReturn(Collections.<Object[]>emptyList());
		when(repo.bloquearClavesMovilidad()).thenReturn(1);
		when(repo.saveAndFlush(any(TblFichaDescriptivaPrograma.class))).thenAnswer(invocacion -> {
			TblFichaDescriptivaPrograma entidad = (TblFichaDescriptivaPrograma) invocacion.getArguments()[0];
			if (entidad.getIdPrograma() == null) { entidad.setIdPrograma(10); }
			return entidad;
		});
	}
	private FichaDescProgramaDTO dto(Integer id) {
		FichaDescProgramaDTO programa = new FichaDescProgramaDTO();
		programa.setIdPrograma(id); programa.setNombreTentativo("Programa de prueba");
		programa.setFechaRegistro(new Date()); programa.setFechaActualizacion(new Date()); programa.setUsuarioModifico(2L);
		return programa;
	}
	@Test public void vacioSeNormalizaANullYNoRequiereClave() {
		FichaDescProgramaDTO programa = dto(null);
		assertNull(programa.getClaveMovilidad()); assertFalse(programa.isClaveMovilidadInformada());
		programa.setClaveMovilidad("  ");
		assertNull(programa.getClaveMovilidad()); assertTrue(programa.isClaveMovilidadInformada());
		assertTrue(servicio.guardar(programa).esCorrecto());
		verify(repo, never()).buscarConflictoMovilidadParaGuardar(anyString(), anyInt(), anyString());
	}
	@Test public void altaGuardaClaveRecortadaYLaDevuelve() {
		FichaDescProgramaDTO programa = dto(null); programa.setClaveMovilidad("  MOV-01  ");
		when(repo.buscarConflictoMovilidadParaGuardar("MOV-01", null, "Programa de prueba")).thenReturn(Collections.<Object[]>emptyList());
		assertEquals("MOV-01", servicio.guardar(programa).getDto().getClaveMovilidad());
	}
	@Test public void conflictoIdentificaProgramaYNoEscribe() {
		FichaDescProgramaDTO programa = dto(null); programa.setClaveMovilidad("MOV-01");
		when(repo.buscarConflictoMovilidadParaGuardar("MOV-01", null, "Programa de prueba")).thenReturn(Collections.singletonList(new Object[] { 99, "Programa propietario", "MOV-01" }));
		try { servicio.guardar(programa); fail("Debe rechazar la clave repetida"); }
		catch (ClaveMovilidadDuplicadaException e) { assertTrue(e.getMessage().contains("Programa propietario")); assertTrue(e.getMessage().contains("99")); }
		verify(repo, never()).saveAndFlush(any(TblFichaDescriptivaPrograma.class));
	}
	@Test public void edicionExcluyeElMismoPrograma() {
		FichaDescProgramaDTO programa = dto(10); programa.setClaveMovilidad("MOV-01");
		assertEquals("MOV-01", servicio.actualizar(programa).getDto().getClaveMovilidad());
		verify(repo).buscarConflictoMovilidadParaGuardar("MOV-01", 10, "Programa de prueba");
	}
	@Test public void actualizacionParcialConservaLaClaveVigente() {
		TblFichaDescriptivaPrograma vigente = new TblFichaDescriptivaPrograma(); vigente.setIdPrograma(10); vigente.setClaveMovilidad("MOV-01");
		when(repo.consultarClaveNombreMovilidad(10)).thenReturn(Collections.singletonList(new Object[] { "MOV-01", "Programa de prueba" }));
		assertEquals("MOV-01", servicio.actualizar(dto(10)).getDto().getClaveMovilidad());
	}
	@Test public void limpiezaExplicitaGuardaNull() {
		FichaDescProgramaDTO programa = dto(10); programa.setClaveMovilidad(null);
		assertNull(servicio.actualizar(programa).getDto().getClaveMovilidad());
		verify(repo, never()).findOne(10);
	}
	@Test(expected = IllegalArgumentException.class) public void rechazaMasDeCienCaracteres() {
		FichaDescProgramaDTO programa = dto(null); programa.setClaveMovilidad(String.join("", Collections.nCopies(101, "X")));
		servicio.guardar(programa);
	}
	@Test public void validaBajoBloqueoAntesDeEscribir() {
		FichaDescProgramaDTO programa = dto(10); programa.setClaveMovilidad("MOV-01");
		servicio.actualizar(programa);
		org.mockito.InOrder orden = inOrder(repo);
		orden.verify(repo).bloquearClavesMovilidad();
		orden.verify(repo).buscarConflictoMovilidadParaGuardar("MOV-01", 10, "Programa de prueba");
		orden.verify(repo).saveAndFlush(any(TblFichaDescriptivaPrograma.class));
	}

	@Test public void programasDeIgualNombrePuedenCompartirClave() {
		FichaDescProgramaDTO programa = dto(null); programa.setClaveMovilidad("AAB"); programa.setNombreTentativo("Asignatura A");
		when(repo.buscarConflictoMovilidadParaGuardar("AAB", null, "Asignatura A")).thenReturn(Collections.<Object[]>emptyList());
		assertTrue(servicio.guardar(programa).esCorrecto());
		verify(repo).buscarConflictoMovilidadParaGuardar("AAB", null, "Asignatura A");
	}

	@Test public void cambiarNombreNoPermiteCompartirConOtroNombre() {
		FichaDescProgramaDTO programa = dto(10); programa.setClaveMovilidad("AAB"); programa.setNombreTentativo("Asignatura B");
		when(repo.buscarConflictoMovilidadParaGuardar("AAB", 10, "Asignatura B"))
				.thenReturn(Collections.singletonList(new Object[] { 99, "Asignatura A", "AAB" }));
		try { servicio.actualizar(programa); fail("Debe rechazar nombres diferentes"); }
		catch (ClaveMovilidadDuplicadaException e) { assertTrue(e.getMessage().contains("Asignatura A")); }
		verify(repo, never()).saveAndFlush(any(TblFichaDescriptivaPrograma.class));
	}
}
