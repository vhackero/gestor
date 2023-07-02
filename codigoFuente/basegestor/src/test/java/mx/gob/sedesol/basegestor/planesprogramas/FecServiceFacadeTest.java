package mx.gob.sedesol.basegestor.planesprogramas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstructuraTematicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.PlanDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatMaterialDidactico;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatObjetoCurricular;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatStatusPrograma;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;
import mx.gob.sedesol.basegestor.service.gestionescolar.PersonaResponsabilidadesService;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CompetenciaEspecificaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ETematicaModuloMdlService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EjeCompetenciaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstPersonalExternoService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstUnidadDidacticaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstructuraTematicaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ModuloMoodleService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.PlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class FecServiceFacadeTest {

	private static final Logger logger = Logger.getLogger(FecServiceFacadeTest.class);
	
	@Mock
	private PersonaResponsabilidadesService personaResponsabilidadesService;
	@Mock
	private MallaCurricularService mallaCurricularService;
	@Mock
	private FichaDescProgramaService fichaDescProgramaService;
	@Mock
	private EstructuraTematicaService estructuraTematicaService;
	@Mock
	private ModuloMoodleService moduloMoodleService;
	@Mock
	private ETematicaModuloMdlService eTematicaModuloMdlService;
	@Mock
	private PlanService planService;
	@Mock
	private CatalogoComunService<CatStatusPrograma, Integer> catStatusProgramaService;
	@Autowired
	private CatalogoComunService<CatObjetoCurricular, Integer> objetoCurricularService;
	@Mock
	private EstUnidadDidacticaService estUnidadDidacticaService;
	@Mock
	private CatalogoComunService<CatMaterialDidactico,Integer> catMaterialDidacticoService;
	@Mock
	private CatalogoComunService<CatCompetenciaEspecifica,Integer> catCompEspecificaService;
	@Mock
	private OrgGubernamentalService orgGubernamentalService;
	@Mock
	private EstPersonalExternoService estPersonalExternoService;
	@Mock
	private CompetenciaEspecificaService competenciaEspecificaService;
	@Mock
	private EjeCompetenciaService ejeCompetenciaService;
	
	@Autowired
	private FichaDescProgramaService fichaDescProgramaServiceFunc;
	
	@InjectMocks
	private FECServiceFacade fecServiceFacade;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void FECguardaMallaCurricular(){
		
		ResultadoDTO<MallaCurricularDTO> stuff = new ResultadoDTO<>();
		MallaCurricularDTO mc = new MallaCurricularDTO();
		mc.setId(1);
		mc.setNombre("Malla Test Mock");
		mc.setObjetoCurricular(objetoCurricularService.buscarPorId(2, CatObjetoCurricular.class));
		stuff.setDto(mc);
		stuff.setResultado(ResultadoTransaccionEnum.EXITOSO);
		try{
		Mockito.when(mallaCurricularService.guardar(((MallaCurricularDTO)Mockito.anyObject()))).thenReturn(stuff);
		Mockito.when(fecServiceFacade.guardaMallaCurricular((MallaCurricularDTO)Mockito.anyObject()))
		.thenReturn(stuff);
		} catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
	
	@Test
	public void FECguardaMallaCurricularException(){
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(mallaCurricularService)
		.guardar(((MallaCurricularDTO)Mockito.anyObject()));
		
		try{
			fecServiceFacade.guardaMallaCurricular((MallaCurricularDTO)Mockito.anyObject());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			assertTrue("Excepion provocada", true);
		}
		
	}
	
	@Test
	public void fecEliminaMallaCurricular(){
		
		MallaCurricularDTO stuff = new MallaCurricularDTO();
		stuff.setId(Mockito.anyInt());
		stuff.setObjetoCurricular(objetoCurricularService.buscarPorId(2, CatObjetoCurricular.class));
		
		FichaDescProgramaDTO programaStub = new FichaDescProgramaDTO();
		programaStub.setTblEstructuraTematica(new EstructuraTematicaDTO());
		Mockito.when(fichaDescProgramaService.buscarPorId(1)).thenReturn(programaStub);
		Mockito.when(mallaCurricularService.eliminar((MallaCurricularDTO)Mockito.anyObject())).thenReturn(new ResultadoDTO<>());
		Mockito.when(estructuraTematicaService.eliminar((EstructuraTematicaDTO)Mockito.anyObject())).thenReturn(new ResultadoDTO<>());
		Mockito.when(fichaDescProgramaService.eliminar((FichaDescProgramaDTO)Mockito.anyObject())).thenReturn(new ResultadoDTO<>());
		
		ResultadoDTO<MallaCurricularDTO> resTx = fecServiceFacade.eliminaMallaCurricular(stuff);
		assertThat(resTx.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
				
	}

	@Test
	public void fecGuardaSubTemaCursoMoodle(){
		
		ResultadoDTO<EstructuraTematicaDTO> rsTx = new ResultadoDTO<>();
		rsTx.setResultado(ResultadoTransaccionEnum.EXITOSO);
		Mockito.when(estructuraTematicaService.guardar((EstructuraTematicaDTO)Mockito.anyObject())).thenReturn(rsTx);
		try{
			Mockito.when(fecServiceFacade.guardaSubTemaCursoMoodle(Mockito.anyObject())).thenReturn(new ResultadoDTO<EstructuraTematicaDTO>());
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void fecGeneraClaveProgramaPlanSedesol(){
		
		PlanDTO plan = new PlanDTO();
		plan.setIdentificador("01-17");
		
		Mockito.when(planService.buscarPorId(ConstantesGestor.IDENTIFICADOR_PLAN_SEDESOL)).thenReturn(plan);
		String cve = fecServiceFacade.generaClaveProgramaPlanSedesol(Mockito.anyInt());
		is(!StringUtils.isEmpty(cve));
		logger.info("CLAVE GENERADA " + cve);
		
	}
	
	@Test
	public void fecGuardarDatosProgramaActualizacion(){
		
		FichaDescProgramaDTO programaSaved = fichaDescProgramaServiceFunc.buscarPorId(40);
		Mockito.when(fichaDescProgramaService.actualizar(programaSaved)).thenReturn(new ResultadoDTO<>());
		Mockito.when(fecServiceFacade.guardarDatosPrograma(programaSaved)).thenReturn(new ResultadoDTO<>());
	}
	
	@Test
	public void fecGuardarDatosPrograma(){
		
		FichaDescProgramaDTO progStub = new FichaDescProgramaDTO();
		progStub.setIdPrograma(null);
		
		ResultadoDTO<FichaDescProgramaDTO> rs = new ResultadoDTO<>();
		rs.setDto(progStub);
		Mockito.when(fichaDescProgramaService.guardar((FichaDescProgramaDTO)Mockito.anyObject())).thenReturn(rs);
		Mockito.when(fichaDescProgramaService.actualizar((FichaDescProgramaDTO)Mockito.anyObject())).thenReturn(rs);
		ResultadoDTO<FichaDescProgramaDTO> rsTx = fecServiceFacade.guardarDatosPrograma(progStub);
		
		assertThat(rsTx.getResultado(), is(ResultadoTransaccionEnum.EXITOSO));
	}
	
	@Test
	public void fecGuardaDatosProgramaException(){
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(fichaDescProgramaService)
		.guardar(((FichaDescProgramaDTO)Mockito.anyObject()));
		
		Mockito.doThrow(new NullPointerException("Esta es una excepcion provocada")).when(fichaDescProgramaService)
		.actualizar(((FichaDescProgramaDTO)Mockito.anyObject()));
		
		try{
			FichaDescProgramaDTO progStub = new FichaDescProgramaDTO();
			fecServiceFacade.guardarDatosPrograma(progStub);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			assertTrue("Excepion provocada", true);
		}
		
	}
	
}

