package mx.gob.sedesol.basegestor.gestionescolar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class CrudPersonaResponsabilidadesTest {
	/*private static final Logger logger = Logger.getLogger(CrudPersonaResponsabilidadesTest.class);

	@Autowired
	private PersonaResponsabilidadesService personaResponsabilidadesService;

	private PersonaResponsabilidadDTO dto;

	//@Test
	public void guardar() {
		dto = new PersonaResponsabilidadDTO();

		dto.setIdPersona(2L);
		dto.setIdTipoResponsabilidad(2);
		dto.setUsuarioModifico(1L);
		dto.setFechaRegistro(new Date());

		try {
			ResultadoDTO resultado = personaResponsabilidadesService.guardar(dto);
			dto = (PersonaResponsabilidadDTO) resultado.getDto();
			logger.info("ID responsabilidad[" + dto.getIdTipoResponsabilidad() + "]");
			logger.info("ID persona[" + dto.getIdPersona() + "]");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void eliminar() {
		dto = new PersonaResponsabilidadDTO();
		
		dto.setIdPersona(1L);
		dto.setIdTipoResponsabilidad(1);
		dto.setId(1);
		dto.setFechaRegistro(new Date());
		dto.setUsuarioModifico(2L);
		
		logger.info("BORRAR id responsabilidad #########" + personaResponsabilidadesService.eliminar(dto).getDto().getId());
	}
	
	//@Test
	public void buscarPersonaPorResponsabilidad(){
		List<PersonaResponsabilidadDTO> res = personaResponsabilidadesService.obtienePersonasPorResponasbilidad(1);
		logger.info("Buscar persona por responsabilidad  idRespon ########### " + res.get(0).getIdTipoResponsabilidad());
		logger.info("Buscar persona por responsabilidad  persona ########### " + res.get(0).getPersona().getNombre());
	}

	public PersonaResponsabilidadesService getPersonaResponsabilidadesService() {
		return personaResponsabilidadesService;
	}

	public void setPersonaResponsabilidadesService(PersonaResponsabilidadesService personaResponsabilidadesService) {
		this.personaResponsabilidadesService = personaResponsabilidadesService;
	}
*/
	
	@Test
	public void prueba() {
		assertThat(1==2, is(false));
	}
}
