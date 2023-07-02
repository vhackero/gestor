package mx.gob.sedesol.basegestor.administracion;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext.xml"})
public class BitacoraServiceTest {
/*	
	@Autowired
	private BitacoraService bitacoraService;

	//@Test
	public void obtenerBitacoraConRegistros() {
		
		BitacoraDTO bitacoraDTO = new BitacoraDTO();
		
		List<BitacoraDTO> listaBitacora = bitacoraService.obtenerBitacora(bitacoraDTO);
		assertThat(listaBitacora, is(not(empty())));
	}
	
//	@Test
	public void obtenerBitacoraSinRegistros() {
		
		BitacoraDTO bitacoraDTO = new BitacoraDTO();
		bitacoraDTO.setIdUsuario(999L);
		
		List<BitacoraDTO> listaBitacora = bitacoraService.obtenerBitacora(bitacoraDTO);
		assertThat(listaBitacora, is(empty()));
	}
	
//	@Test
	public void obtenerBitacoraPorUsuario() {
		
		BitacoraDTO bitacoraDTO = new BitacoraDTO();
		bitacoraDTO.setIdUsuario(1L);
		
		List<BitacoraDTO> listaBitacora = bitacoraService.obtenerBitacora(bitacoraDTO);
		assertThat(listaBitacora, is(not(empty())));
	}

	@Test
		public void prueba() {
			assertThat(1==2, is(false));
		}
*/
}
